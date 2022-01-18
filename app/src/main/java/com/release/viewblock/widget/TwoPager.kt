package com.release.viewblock.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

/**
 * 触摸反馈-TwoPager
 * @author yancheng
 * @since 2022/1/13
 */
class TwoPager(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    constructor(context: Context) : this(context, null)

    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    private var scrolling = false

    //计算器 (OverScroller适合做快速滑动,它有初始速度,而Scroller没有初始速度)
    private val overScroller: OverScroller = OverScroller(context)
    private val velocityTracker = VelocityTracker.obtain()
    private val viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private var minVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = downX - event.x
                if (abs(dx) > pagingSlop) {
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    result = true
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(width)
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targerPage = if (abs(vx) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targerPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}