package com.release.viewblock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.release.viewblock.widget.*
import kotlinx.android.synthetic.main.activity_show_widget_view.*

/**
 * 饼图
 * @author yancheng
 * @since 2021/12/6
 */
class ShowWidgetViewActivity : AppCompatActivity() {

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
        }
    }
}