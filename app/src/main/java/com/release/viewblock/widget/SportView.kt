package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.release.viewblock.ktx.dp2pxF

/**
 * 运动圆环
 * @author yancheng
 * @since 2021/12/7
 */
private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF8041")
private val RING_WIDTH = 20.dp2pxF
private val RADIUS = 150.dp2pxF

class SportView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp2pxF
        textAlign = Paint.Align.CENTER
    }

    private val bounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        //绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        //绘制文字
        paint.textSize = 100.dp2pxF
        paint.style = Paint.Style.FILL
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            "cccc",
            width / 2f,
            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f,
            paint
        )

        //绘制文字2
        paint.textSize = 150.dp2pxF
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds("abcd", 0, "abcd".length, bounds)
        canvas.drawText("abcd", -bounds.left.toFloat(), -bounds.top.toFloat(), paint)

        //绘制文字3
        paint.textSize = 15.dp2pxF
        paint.getTextBounds("bbbccc",0,"bbbccc".length,bounds)
        canvas.drawText("bbbccc",-bounds.left.toFloat(),-bounds.top.toFloat(),paint)

    }
}