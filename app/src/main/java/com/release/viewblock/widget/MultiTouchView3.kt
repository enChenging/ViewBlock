package com.release.viewblock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.release.viewblock.ktx.dp2pxF

/**
 * 多点触摸-互不影响型
 * @author yancheng
 * @since 2022/1/13
 */
class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4.dp2pxF
        //圆头
        paint.strokeCap = Paint.Cap.ROUND
        //圆角
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until paths.size()) {
            val path = paths.valueAt(i)
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                paths.append(event.getPointerId(actionIndex), path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until paths.size()) {
                    val ponitId = event.getPointerId(i)
                    val path = paths.get(ponitId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointId = event.getPointerId(actionIndex)
                paths.remove(pointId)
                invalidate()
            }
        }
        return true
    }
}