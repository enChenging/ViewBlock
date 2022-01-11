package com.release.viewblock.ktx

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.release.viewblock.R

/**
 * 工具类
 * @author yancheng
 * @since 2022/1/11
 */
fun getAvatar(res: Resources, width: Int): Bitmap {
    val options = BitmapFactory.Options()
    //只读出尺寸 不读像素
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, R.drawable.avatar, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(res, R.drawable.avatar, options)
}