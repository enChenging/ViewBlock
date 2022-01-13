package com.release.viewblock.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.release.viewblock.ktx.dp2px
import com.release.viewblock.ktx.getAvatar
import kotlin.math.max
import kotlin.math.min

/**
 * 自定义缩放ImageView
 * @author yancheng
 * @since 2022/1/11
 */
private val IMAGE_SIZE = 300.dp2px

//额外放缩倍数
private const val EXTRA_SCALE_FRACTION = 1.5f

class ScalableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private val viewGestureListener = ViewGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, viewGestureListener)
    private val viewFlingRunner = ViewFlingRunner()
    private val viewScaleGestureListener = ViewScaleGestureListener()
    private val scaleGestureDetector = ScaleGestureDetector(context, viewScaleGestureListener)

    //图片内贴边
    private var smallScale = 0f

    //图片外贴边
    private var bigScale = 0f

    //双击变大/缩小
    private var big = false

    //当前放缩比
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    //缩放动画
    private val scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)

    //计算器 (OverScroller适合做快速滑动,它有初始速度,而Scroller没有初始速度)
    private val scroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //width - IMAGE_SIZE  view的尺寸-图片尺寸/2 = 图片位于中心的偏移值
        originalOffsetX = (width - IMAGE_SIZE) / 2f
        originalOffsetY = (height - IMAGE_SIZE) / 2f

        //图片比较宽
        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FRACTION
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FRACTION
        }
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    /**
     * 边缘修正
     */
    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress)
            gestureDetector.onTouchEvent(event)
        return true
    }

    inner class ViewGestureListener : GestureDetector.SimpleOnGestureListener() {

        /**
         * 按下
         * @param p0 MotionEvent
         * @return Boolean 返回true消费事件
         */
        override fun onDown(p0: MotionEvent?): Boolean {
            return true
        }

        /**
         * ActionMove
         * @param downEvent MotionEvent down事件
         * @param currentEvent MotionEvent 当前事件
         * @param distanceX Float 上个事件和这次事件点的距离 (旧位置-新位置)
         * @param distanceY Float
         * @return Boolean
         */
        override fun onScroll(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
        }

        /**
         * 手指快速滑动触发
         * @param downEvent MotionEvent
         * @param currentEvent MotionEvent
         * @param velocityX Float 速率(单位时间内的位移)
         * @param velocityY Float
         * @return Boolean
         */
        override fun onFling(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                //startX:起始坐标, startY:起始坐标,velocityX:速度, velocityY
                //minX:最小值,maxX:最大值, minY:最小值,maxY:最大值,overX:过度滑动,最多可以偏移多少,overY:过度滑动
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt()
//                    20.dp2px, 20.dp2px
                )
                //在下一帧调用 (与post区别是,post会立刻调用,而postOnAnimation会在下一帧中调用)
                ViewCompat.postOnAnimation(this@ScalableImageView, viewFlingRunner)
            }
            return false
        }

        /**
         * 双击 (时间间隔300ms:双击,短语40ms不会触发:防止手抖)
         * @param p0 MotionEvent
         * @return Boolean
         */
        override fun onDoubleTap(p0: MotionEvent): Boolean {
            big = !big
            if (big) {
                //手指双击的偏移值
                offsetX = (p0.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (p0.y - height / 2f) * (1 - bigScale / smallScale)
                fixOffsets()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class ViewScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {

        override fun onScaleBegin(p0: ScaleGestureDetector): Boolean {
            offsetX = (p0.focusX - width / 2f) * (1 - bigScale / smallScale)
            offsetY = (p0.focusY - height / 2f) * (1 - bigScale / smallScale)
            fixOffsets()
            return true
        }

        override fun onScaleEnd(p0: ScaleGestureDetector) {
        }

        /**
         *
         * @param p0 ScaleGestureDetector
         * @return Boolean true:此刻与上一状态比值  false:此刻与初始状态的比值
         */
        override fun onScale(p0: ScaleGestureDetector): Boolean {
            val tempCurrentScale = currentScale * p0.scaleFactor //0  ->  无穷
            return if (tempCurrentScale < smallScale || tempCurrentScale > bigScale) {
                //上一状态的值进行保存
                false
            } else {
                currentScale *= p0.scaleFactor
                true
            }
//            currentScale = currentScale.coerceAtLeast(smallScale).coerceAtMost(bigScale)
        }

    }

    inner class ViewFlingRunner : Runnable {
        override fun run() {
            //是否还在计算中
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }


}