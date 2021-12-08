package com.release.viewblock.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.R
import com.release.viewblock.ktx.dp2pxF

/**
 * 多行文字
 * 文字测量
 * @author yancheng
 * @since 2021/12/7
 */
private val IMAGE_PADDING = 50.dp2pxF
private val IMAGE_SIZE = 150f.dp2pxF

class MultilineTextView(context: Context,attrs: AttributeSet?) : View(context,attrs) {
    constructor(context: Context) : this(context, null)

    val text =
        "Donec vel turpis nisl. Phasellus leo leo, accumsan in urna id, scelerisque condimentum nunc. Nunc nunc est, finibus quis porta elementum, ornare quis est. Etiam ligula lectus, dictum ut nibh eget, lacinia facilisis ante. Vivamus rutrum, ex nec vulputate ultrices, leo dolor sollicitudin felis, non rutrum nunc ex quis arcu. Nam ut mi leo. Suspendisse interdum pharetra leo, id rhoncus nunc imperdiet vel. Suspendisse sed viverra magna. Etiam rutrum sollicitudin risus, a mollis est aliquam mattis. Donec a tortor condimentum, condimentum urna non, consectetur odio. Suspendisse luctus, mi vitae porta lobortis, lacus turpis auctor elit, ut sodales nisi libero id dolor. Integer suscipit metus vel felis porttitor varius. Curabitur ac sapien eget nulla venenatis cursus."

    //    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
//        textSize = 16.dp2pxF
//    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp2pxF
    }
    private val bitmap = getAvatar(IMAGE_SIZE.toInt())
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val staticLayout =
//            StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
//        staticLayout.draw(canvas)
        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)
        paint.getFontMetrics(fontMetrics)
        val measureWidth = floatArrayOf(0f)
        var start = 0
        var count = 0
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        while (start < text.length) {
            maxWidth =
                if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
                    width.toFloat()
                } else {
                    width.toFloat() - IMAGE_SIZE
                }
            count =
                paint.breakText(text, start, text.length, true, maxWidth, measureWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        //只读出尺寸 不读像素
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }
}