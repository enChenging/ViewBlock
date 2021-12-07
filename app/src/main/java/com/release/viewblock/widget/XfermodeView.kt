package com.release.viewblock.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.ktx.dp2px
import com.release.viewblock.ktx.dp2pxF

/**
 * Xfermode使用解析
 * PorterDuff.Mode官方介绍地址
 * https://developer.android.com/reference/android/graphics/PorterDuff.Mode
 * @author yancheng
 * @since 2021/12/6
 */

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

class XfermodeView(context: Context?) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(150f.dp2pxF, 50f.dp2pxF, 300f.dp2pxF, 200f.dp2pxF)
    private val circleBitmap = Bitmap.createBitmap(150f.dp2px, 150f.dp2px, Bitmap.Config.ARGB_8888)
    private val squareBitmap = Bitmap.createBitmap(150f.dp2px, 150f.dp2px, Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(50f.dp2pxF, 0f.dp2pxF, 150f.dp2pxF, 100f.dp2pxF, paint)
        canvas.setBitmap(squareBitmap)
        paint.color = Color.parseColor("#2196F3")
        canvas.drawRect(0f.dp2pxF, 50f.dp2pxF, 100f.dp2pxF, 150f.dp2pxF, paint)
    }

    override fun onDraw(canvas: Canvas) {
        //离屏缓冲
        val count = canvas.saveLayer(bounds, null)
        canvas.drawBitmap(circleBitmap, 150f.dp2pxF, 50f.dp2pxF, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap, 150f.dp2pxF, 50f.dp2pxF, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }
}