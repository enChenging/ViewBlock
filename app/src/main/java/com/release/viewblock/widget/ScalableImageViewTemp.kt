package com.release.viewblock.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
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

class ScalableImageViewTemp(context: Context, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    //图片内贴边
    private var smallScale = 0f

    //图片外贴边
    private var bigScale = 0f
    private val gestureDetector = GestureDetectorCompat(context, this)

    //    private val gestureDetector = GestureDetectorCompat(context, this).apply {
//        setOnDoubleTapListener(this@ScalableImageView)
//    }
    private var big = false

    //放缩比
    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    /**
     * 按下
     * @param p0 MotionEvent
     * @return Boolean 返回true消费事件
     */
    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    /**
     * 用户触摸到,而且触摸100毫秒
     * @param p0 MotionEvent
     */
    override fun onShowPress(p0: MotionEvent?) {

    }

    /**
     * 单击 (不支持双击时,单击用这个)
     * @param p0 MotionEvent
     * @return Boolean 你是否消费了点击事件 (返回值对我们无作用,是给系统做记录用的)
     */
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
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
     * 边缘修正
     */
    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    /**
     * 长按点击
     * @param e MotionEvent
     */
    override fun onLongPress(e: MotionEvent?) {

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
                ((bitmap.height * bigScale - height) / 2).toInt(),
                20.dp2px, 20.dp2px
            )
            //在下一帧调用 (与post区别是,post会立刻调用,而postOnAnimation会在下一帧中调用)
            ViewCompat.postOnAnimation(this, this)
        }
        return false
    }

    override fun run() {
        //是否还在计算中
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            ViewCompat.postOnAnimation(this, this)
        }
    }

    /**
     * 单击 (支持双击时,单击用这个)
     * @param p0 MotionEvent
     * @return Boolean
     */
    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
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

    /**
     * 双击按下后的各种事件都会收到
     * @param p0 MotionEvent
     * @return Boolean
     */
    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        return false
    }


}