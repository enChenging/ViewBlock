package com.release.viewblock

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.release.viewblock.ktx.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vDashBoard.setOnClickListener(this)
        vPieChart.setOnClickListener(this)
        vAvatar.setOnClickListener(this)
        vXfermode.setOnClickListener(this)
        vSport.setOnClickListener(this)
        vMultiline.setOnClickListener(this)
        vCamera.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.vDashBoard ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.DASH_BOARD))
            R.id.vPieChart ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.PIE_CHART))
            R.id.vAvatar ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.AVATAR))
            R.id.vXfermode ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.XFERMODE))
            R.id.vSport ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.SPORT))
            R.id.vMultiline ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.MULTILINE))
            R.id.vCamera ->
                startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, Constants.CAMERA))
        }
    }

}