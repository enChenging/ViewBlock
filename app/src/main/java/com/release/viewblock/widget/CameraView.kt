package com.release.viewblock.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.release.viewblock.R
import com.release.viewblock.ktx.dp2pxF

/**
 * 范围裁切和几何变换
 * @author yancheng
 * @since 2021/12/7
 */
private val BITMAP_SIZE = 200.dp2pxF
private val BITMAP_PADDING = 100.dp2pxF

class CameraView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val camera = Camera()

    //上方折起角度
    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    //下方折起角度
    var bottomFlip = 30f
        set(value) {
            field = value
            invalidate()
        }

    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        //照相机的摄像距离默认-8
        camera.setLocation(0f, 0f, (-6).dp2pxF)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //上半部分 不变
        //withSave ==  save() + restore()
        canvas.withSave {
            canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0f)
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        }

        //下半部分 先旋转30度,再沿X轴旋转,再旋转30度回来
        canvas.withSave {
            canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE)
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
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