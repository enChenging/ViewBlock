package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *
 * @author yancheng
 * @since 2021/12/8
 */
class DrawableView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    val drawable = MeshDrawable()

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }
}