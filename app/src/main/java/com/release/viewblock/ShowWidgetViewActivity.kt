package com.release.viewblock

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.release.viewblock.ktx.dp2px
import com.release.viewblock.widget.*
import kotlinx.android.synthetic.main.activity_show_widget_view.*

/**
 * 展示各种自定义View视图
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
    private val vMeasure by lazy {
        MeasureView(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                300.dp2px,
                200.dp2px
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(
            intent.getStringExtra(Constants.KEY),
            intent.getStringExtra(Constants.TITLE),
            intent.getStringExtra(Constants.IS_VIEW_GROUP)
        )
    }

    private fun initView(key: String?, title: String?, stringExtra: String?) {
        if (stringExtra.isNullOrEmpty()) {
            setContentView(R.layout.activity_show_widget_view)
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
                Constants.MATERIAL_EDIT_TEXT ->
                    container.addView(materialEditText)
                Constants.MEASURE ->
                    container.addView(vMeasure)
                Constants.SCALABLE_IMAGE_VIEW ->
                    container.addView(ScalableImageView(this))
                Constants.MULTI_TOUCH_1 ->
                    container.addView(MultiTouchView1(this))
                Constants.MULTI_TOUCH_2 ->
                    container.addView(MultiTouchView2(this))
                Constants.MULTI_TOUCH_3 ->
                    container.addView(MultiTouchView3(this))
            }
        } else {
            when (key) {
                Constants.LAYOUT ->
                    setContentView(R.layout.layout_tag)
                Constants.TWO_PAGER ->
                    setContentView(R.layout.layout_two_pager)
                Constants.DRAG_HELP ->
                    setContentView(R.layout.layout_drag_help_grid_view)
                Constants.DRAG_LISTENER ->
                    setContentView(R.layout.layout_drag_listener_grid_view)
                Constants.DRAG_TO_COLLECT ->
                    setContentView(R.layout.layout_drag_to_collect_view)
                Constants.DRAG_UP_DOWN ->
                    setContentView(R.layout.layout_drag_up_down_view)
            }
        }
        supportActionBar?.title = title

    }
}