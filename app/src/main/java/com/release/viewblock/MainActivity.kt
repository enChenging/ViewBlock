package com.release.viewblock

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.release.viewblock.ktx.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDashBoardView(view: View) = startActivity<DashBoardActivity>()
    fun onPieChart(view: View) = startActivity<PieChartActivity>()

}