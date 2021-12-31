package com.release.viewblock.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * 自定义Layout
 * @author yancheng
 * @since 2021/12/10
 */
class TagLayout(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineMaxHegith = 0
        val widtSpechMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightUsed
            )
            /*val layoutParams = child.layoutParams
            var childWidthSpecMode = 0
            var childWidthSpecSize = 0
            when (layoutParams.width) {
                LayoutParams.MATCH_PARENT ->
                    when (widtSpechMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecMode = MeasureSpec.EXACTLY
                            childWidthSpecSize = widthSpecSize - widthUsed
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                            childWidthSpecSize = 0
                        }
                    }
                LayoutParams.WRAP_CONTENT ->
                    when (widtSpechMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecMode = MeasureSpec.AT_MOST
                            childWidthSpecSize = widthSpecSize - widthUsed
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                            childWidthSpecSize = 0
                        }
                    }
                else -> {
                    childWidthSpecMode = MeasureSpec.EXACTLY
                    childWidthSpecSize = layoutParams.width
                }
            }*/
            if (widtSpechMode != MeasureSpec.UNSPECIFIED && widthUsed + child.measuredWidth > widthMeasureSpec) {
                lineWidthUsed = 0
                heightUsed = 0
                heightUsed += lineMaxHegith
                lineMaxHegith = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(
                widthUsed,
                heightUsed,
                widthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )
            widthUsed += child.measuredWidth
            lineMaxHegith = max(lineMaxHegith, child.measuredHeight)
        }
        val selfWidth = widthUsed
        val selfHeight = lineMaxHegith
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}