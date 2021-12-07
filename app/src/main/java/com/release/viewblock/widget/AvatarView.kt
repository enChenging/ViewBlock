package com.release.viewblock.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.R
import com.release.viewblock.ktx.dp2px
import com.release.viewblock.ktx.dp2pxF

/**
 * 头像
 * Xfermode使用解析
 * PorterDuff.Mode官方介绍地址
 * https://developer.android.com/reference/android/graphics/PorterDuff.Mode
 * @author yancheng
 * @since 2021/12/6
 */

private val IMAGE_WIDTH = 200f.dp2px
private val IMAGE_PADDING = 20f.dp2pxF
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
class AvatarView(context: Context) : View(context){
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(
        IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH)

    override fun onDraw(canvas: Canvas) {
        //离屏缓冲
        val count = canvas.saveLayer(bounds, null)
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH,
            paint
        )
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
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