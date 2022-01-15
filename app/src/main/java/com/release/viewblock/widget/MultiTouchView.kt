package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.release.viewblock.ktx.dp2px
import com.release.viewblock.ktx.getAvatar

/**
 * 多点触摸
 * @author yancheng
 * @since 2022/1/13
 */
class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp2px)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}