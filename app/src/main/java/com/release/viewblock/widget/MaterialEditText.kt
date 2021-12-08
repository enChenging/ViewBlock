package com.release.viewblock.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.release.viewblock.R
import com.release.viewblock.ktx.dp2pxF

/**
 * MaterialEditText
 * @author yancheng
 * @since 2021/12/8
 */
private val TEXT_SIZE = 12.dp2pxF
private val TEXT_MARGIN = 8.dp2pxF
private val HORIZONTAL_OFFSET = 5.dp2pxF
private val VERTICAL_OFFSET = 23.dp2pxF
private val EXTRA_VERTICAL_OFFSET = 16.dp2pxF

class MaterialEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var floatLableFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatLableFraction", 0f, 1f)
    }

    //是否展示
    private var floatLabelShown = false

    var userFloatingLable = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        }

    init {
        paint.textSize = TEXT_SIZE
        paint.color = Color.RED
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.userFloatingLable))
//        userFloatingLable =
//            typedArray.getBoolean(R.styleable.MaterialEditText_userFloatingLable, true)
        userFloatingLable = typedArray.getBoolean(0, true)
        typedArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (floatLabelShown && text.isNullOrEmpty()) {
            floatLabelShown = false
            animator.reverse()
        } else if (!floatLabelShown && !text.isNullOrEmpty()) {
            floatLabelShown = true
            animator.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatLableFraction * 0xff).toInt()
        val currentVertivalValue =
            VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatLableFraction)
        canvas.drawText(
            if (hint.isNullOrEmpty()) "请输入" else hint.toString(),
            HORIZONTAL_OFFSET,
            currentVertivalValue,
            paint
        )
    }

}