package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.release.viewblock.ktx.dp2pxF

/**
 * 圆(为View测量使用)
 * @author yancheng
 * @since 2021/12/9
 */
private val RADIUS = 100.dp2pxF
private val PADDING = 100.dp2pxF

class MeasureView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.parseColor("#00796B")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = (PADDING + RADIUS * 2).toInt()
//        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val width = when (specWidthMode) {
//            MeasureSpec.EXACTLY -> specWidthSize
//            MeasureSpec.AT_MOST -> if (size > specWidthSize) specWidthSize else size
//            else -> size
//        }
        val width = resolveSize(size,widthMeasureSpec)
        val height = resolveSize(size,heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i("cyc", "onDraw: ")
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }
}