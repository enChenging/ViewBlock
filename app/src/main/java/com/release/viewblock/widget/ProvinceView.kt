package com.release.viewblock.widget

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.release.viewblock.ktx.dp2pxF

/**
 * 省
 * @author yancheng
 * @since 2021/12/8
 */
private val provinces = listOf(
    "北京市",
    "A",
    "阿拉善盟",
    "鞍山",
    "安庆",
    "安阳",
    "阿坝",
    "安顺",
    "阿里",
    "安康",
    "阿克苏",
    "阿勒泰",
    "澳门",
    "安吉",
    "安丘",
    "安岳",
    "安平",
    "安溪",
    "安宁",
    "安化",
    "阿拉尔",
    "安福",
    "阿勒泰市"
)

class ProvinceView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 80.dp2pxF
        textAlign = Paint.Align.CENTER
    }
    var province = "北京市"
        set(value) {
            field = value
            invalidate()
        }

    init {
        //开启离屏缓冲,使用硬件绘制
        setLayerType(LAYER_TYPE_HARDWARE,null)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(province, width / 2f, height / 2f, paint)
    }
}

class ProvinceEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startIndex = provinces.indexOf(startValue)
        val endIndex = provinces.indexOf(endValue)
        val currentIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
        return provinces[currentIndex]
    }
}