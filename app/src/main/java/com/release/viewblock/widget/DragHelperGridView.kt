package com.release.viewblock.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

/**
 * 触摸反馈-拖拽
 * @author yancheng
 * @since 2022/1/13
 */
private const val COLUMNS = 2
private const val ROWS = 3

class DragHelperGridView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    constructor(context: Context) : this(context, null)

    private var dragHelper = ViewDragHelper.create(this, DragCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var childLeft = 0
        var childTop = 0
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in children.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {
        var capturedLeft = 0f
        var captureTop = 0f

        /**
         * 返回true可以拖拽
         * @param child View
         * @param pointerId Int
         * @return Boolean
         */
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun onViewDragStateChanged(state: Int) {
            //停止拖拽
            if (state == ViewDragHelper.STATE_IDLE) {
                val capturedView = dragHelper.capturedView
                if (capturedView != null) {
                    capturedView.elevation--
                }
            }
        }

        /**
         * 横向轨道
         * @param child View
         * @param left Int
         * @param dx Int
         * @return Int
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            //显示到其它View的上方
            capturedChild.elevation = elevation + 1
            //被托起View的位置
            capturedLeft = capturedChild.left.toFloat()
            captureTop = capturedChild.top.toFloat()
        }

        /**
         * View位置改变时
         * @param changedView View
         * @param left Int
         * @param top Int
         * @param dx Int
         * @param dy Int
         */
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }

        /**
         * View被放下
         * @param releasedChild View
         * @param xvel Float
         * @param yvel Float
         */
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            dragHelper.settleCapturedViewAt(capturedLeft.toInt(), captureTop.toInt())
            postInvalidateOnAnimation()
        }
    }


}