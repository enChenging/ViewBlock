package com.release.viewblock.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.ktx.dp2pxF
import kotlin.math.cos
import kotlin.math.sin

/**
 * 仪表盘
 * 图形的位置和尺寸测量
 * @author yancheng
 * @since 2021/12/6
 */
private val OPEN_ANGLE = 120f
private val MARK = 10
private val RADIUS = 150f.dp2pxF
private val LENGTH = 120f.dp2pxF
private val DASH_WIDTH = 2f.dp2pxF
private val DASH_LENGTH = 10f.dp2pxF
class DashBoardView(context: Context?) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //仪表刻度
    private val dash = Path()
    private val path = Path()
    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.dp2pxF
        paint.style = Paint.Style.STROKE

        //CCW逆时针  CW顺时针
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(path, false)
        //总长度分20份间隔长度  pathMeasure.length / 20f
        pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - DASH_WIDTH) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        //画弧
        canvas.drawPath(path, paint)

        //画刻度
        //path 虚线效果
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        //5 刻度
        //Math.toRadians 角度转弧度
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LENGTH * cos(markToRadians(MARK)).toFloat(),
            height / 2f + LENGTH * sin(markToRadians(MARK)).toFloat(),
            paint
        )
    }

    private fun markToRadians(mark: Int) =
        Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark).toDouble())

}