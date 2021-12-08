package com.release.viewblock

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.release.viewblock.widget.*
import kotlinx.android.synthetic.main.activity_show_widget_view.*

/**
 * 饼图
 * @author yancheng
 * @since 2021/12/6
 */
class ShowWidgetViewActivity : AppCompatActivity() {

    private val materialEditText by lazy {
        MaterialEditText(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_widget_view)

        val key = intent.getStringExtra(Constants.KEY)
        initView(key)
    }

    private fun initView(key: String?) {
        container.removeAllViews()
        when (key) {
            Constants.DASH_BOARD ->
                container.addView(DashBoardView(this))
            Constants.PIE_CHART ->
                container.addView(PieChartView(this))
            Constants.AVATAR ->
                container.addView(AvatarView(this))
            Constants.XFERMODE ->
                container.addView(XfermodeView(this))
            Constants.SPORT ->
                container.addView(SportView(this))
            Constants.MULTILINE ->
                container.addView(MultilineTextView(this))
            Constants.CAMERA ->
                container.addView(CameraView(this))
            Constants.DRAWABLE ->
                container.addView(DrawableView(this))
            Constants.MATERIAL_EDIT_TEXT -> {
                container.addView(materialEditText)
            }
        }
    }
}