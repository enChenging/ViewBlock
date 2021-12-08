package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.ktx.dp2pxF
import java.util.jar.Attributes

/**
 * 圆
 * @author yancheng
 * @since 2021/12/8
 */
class CircleView(context: Context,attrs: AttributeSet?) : View(context,attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var radius = 50.dp2pxF
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.color = Color.parseColor("#00796B")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }
}