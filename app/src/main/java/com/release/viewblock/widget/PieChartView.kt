package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.ktx.dp2pxF
import kotlin.math.cos
import kotlin.math.sin

/**
 * 饼图
 * 图形的位置和尺寸测量
 * @author yancheng
 * @since 2021/12/6
 */
private val RADIUS = 150f.dp2pxF
private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(
    Color.parseColor("#C2185B"),
    Color.parseColor("#00ACC1"),
    Color.parseColor("#558B2F"),
    Color.parseColor("#5D4037")
)
private val OFFSET_LENGTH = 30f
class PieChartView(context: Context,attrs: AttributeSet?) : View(context,attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        //画弧
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            if (index == 1) {
                canvas.save()
                //偏移
                canvas.translate(
                    OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat(),
                    OFFSET_LENGTH * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat()
                )
            }
            canvas.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle, true, paint
            )
            startAngle += angle
            if (index == 1)
                canvas.restore()
        }

    }


}