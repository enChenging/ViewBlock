package com.release.viewblock

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.release.viewblock.ktx.startActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 自定义View
 * @author yancheng
 * @since 2021/12/6
 */
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
        vDrawable.setOnClickListener(this)
        vMaterialEditText.setOnClickListener(this)
        vMeasure.setOnClickListener(this)
        vLayout.setOnClickListener(this)
        vScalableIv.setOnClickListener(this)
        vMultiTouch1.setOnClickListener(this)
        vMultiTouch2.setOnClickListener(this)
        vMultiTouch3.setOnClickListener(this)
        vTwoPager.setOnClickListener(this)
        vDragHelp.setOnClickListener(this)
        vDragListener.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        view as TextView
        val title = view.text.toString()
        when (view.id) {
            R.id.vDashBoard ->
                jumpAct(Constants.DASH_BOARD, title, "")
            R.id.vPieChart ->
                jumpAct(Constants.PIE_CHART, title, "")
            R.id.vAvatar ->
                jumpAct(Constants.AVATAR, title, "")
            R.id.vXfermode ->
                jumpAct(Constants.XFERMODE, title, "")
            R.id.vSport ->
                jumpAct(Constants.SPORT, title, "")
            R.id.vMultiline ->
                jumpAct(Constants.MULTILINE, title, "")
            R.id.vCamera ->
                jumpAct(Constants.CAMERA, title, "")
            R.id.vAnimator ->
                startActivity<AnimatorActivity>(Pair(Constants.TITLE, title))
            R.id.vDrawable ->
                jumpAct(Constants.DRAWABLE, title, "")
            R.id.vMaterialEditText ->
                jumpAct(Constants.MATERIAL_EDIT_TEXT, title, "")
            R.id.vMeasure ->
                jumpAct(Constants.MEASURE, title, "")
            R.id.vLayout ->
                jumpAct(Constants.LAYOUT, title, "1")
            R.id.vScalableIv ->
                jumpAct(Constants.SCALABLE_IMAGE_VIEW, title, "")
            R.id.vMultiTouch1 ->
                jumpAct(Constants.MULTI_TOUCH_1, title, "")
            R.id.vMultiTouch2 ->
                jumpAct(Constants.MULTI_TOUCH_2, title, "")
            R.id.vMultiTouch3 ->
                jumpAct(Constants.MULTI_TOUCH_3, title, "")
            R.id.vTwoPager ->
                jumpAct(Constants.TWO_PAGER, title, "1")
            R.id.vDragHelp ->
                jumpAct(Constants.DRAG_HELP, title, "1")
            R.id.vDragListener ->
                jumpAct(Constants.DRAG_LISTENER, title, "1")
        }
    }

    private fun jumpAct(value: String, title: String, isViewGroup: String) {
        startActivity<ShowWidgetViewActivity>(
            Pair(Constants.KEY, value),
            Pair(Constants.TITLE, title),
            Pair(Constants.IS_VIEW_GROUP, isViewGroup)
        )
    }
}