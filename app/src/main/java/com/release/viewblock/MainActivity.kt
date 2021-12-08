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
        vAnimator.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.vDashBoard ->
                jumpAct(Constants.DASH_BOARD)
            R.id.vPieChart ->
                jumpAct(Constants.PIE_CHART)
            R.id.vAvatar ->
                jumpAct(Constants.AVATAR)
            R.id.vXfermode ->
                jumpAct(Constants.XFERMODE)
            R.id.vSport ->
                jumpAct(Constants.SPORT)
            R.id.vMultiline ->
                jumpAct(Constants.MULTILINE)
            R.id.vCamera ->
                jumpAct(Constants.CAMERA)
            R.id.vAnimator ->
                startActivity<AnimatorActivity>()
        }
    }

    private fun jumpAct(value: String) {
        startActivity<ShowWidgetViewActivity>(Pair(Constants.KEY, value))
    }
}