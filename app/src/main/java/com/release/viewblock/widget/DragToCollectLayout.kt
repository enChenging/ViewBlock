package com.release.viewblock.widget

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.layout_drag_to_collect_view.view.*

/**
 * 触摸反馈-拖拽3
 * @author yancheng
 * @since 2022/1/19
 */
class DragToCollectLayout(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    constructor(context: Context) : this(context, null)

    private var dragStarter = OnLongClickListener { view ->
        //对数据进行包装
        val imageData = ClipData.newPlainText("name", view.contentDescription)
        //data:可以跨进程数据, shadowBuilder:拖起来半透明View的样式, localState:本地数据, flags
        ViewCompat.startDragAndDrop(view, imageData, DragShadowBuilder(view), null, 0)
    }

    private var dragListener: OnDragListener = CollectListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        vAvatarIv.setOnLongClickListener(dragStarter)
        vFrameIv.setOnLongClickListener(dragStarter)
        vCollectorLayout.setOnDragListener(dragListener)
    }

    private inner class CollectListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> if (v is LinearLayout) {
                    val textView = TextView(context)
                    textView.textSize = 16f
                    textView.text = event.clipData.getItemAt(0).text
                    v.addView(textView)
                }
            }
            return true
        }

    }
}