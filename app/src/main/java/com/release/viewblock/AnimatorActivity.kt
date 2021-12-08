package com.release.viewblock

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.release.viewblock.ktx.dp2pxF
import com.release.viewblock.widget.ProvinceEvaluator
import kotlinx.android.synthetic.main.activity_animator.*

/**
 * 属性动画
 * @author yancheng
 * @since 2021/12/8
 */
class AnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)

        //ViewPropertyAnimator 使用
//        vCircleView.animate()
//            .translationX(100.dp2pxF)
//            .translationY(100.dp2pxF)
//            .alpha(0.5f)
//            .scaleX(1.5f)
//            .scaleY(1.5f)
//            .rotation(90f)
//            .withLayer()
//            .startDelay = 1000


        //ObjectAnimator 使用
//        val animator = ObjectAnimator.ofFloat(vCircleView,"radius",150.dp2pxF)
//        animator.startDelay = 1000
//        animator.start()

//        val topFlipAnimator = ObjectAnimator.ofFloat(vCameraView,"topFlip",60f)
//        topFlipAnimator.startDelay = 1000
//        topFlipAnimator.duration = 1500
//        topFlipAnimator.start()

//        val bottomFlipAnimator = ObjectAnimator.ofFloat(vCameraView,"bottomFlip",60f)
//        bottomFlipAnimator.startDelay = 1000
//        bottomFlipAnimator.duration = 1500
//        bottomFlipAnimator.start()

//        val flipRotationAnimator = ObjectAnimator.ofFloat(vCameraView,"flipRotation",60f)
//        flipRotationAnimator.startDelay = 1000
//        flipRotationAnimator.duration = 1500
//        flipRotationAnimator.start()

        //PropertyValuesHolder 使用
        //fraction 比率  value 值
//        val lenght = 150.dp2pxF
//        val keyframe1 = Keyframe.ofFloat(0f, 0f)
//        val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * lenght)
//        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * lenght)
//        val keyframe4 = Keyframe.ofFloat(1f, 1f * lenght)
//        val keyframeHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4
//        )
//        val animator = ObjectAnimator.ofPropertyValuesHolder(vCircleView,keyframeHolder)
//        animator.startDelay = 1000
//        animator.duration = 2000
//        animator.start()

        //TypeEvaluator 使用
//        val animator = ObjectAnimator.ofObject(
//            vPointFView,
//            "point",
//            PointFEvaluator(),
//            PointF(100.dp2pxF, 200.dp2pxF)
//        )
//        animator.startDelay = 1000
//        animator.duration = 2000
//        animator.start()

        val animator = ObjectAnimator.ofObject(
            vProvinceView,
            "province",
            ProvinceEvaluator(),
            "阿勒泰市"
        )
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()
    }

    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val startX = startValue.x
            val endX = endValue.x
            val currentX = startX + (endX - startX) * fraction
            val startY = startValue.y
            val endY = endValue.y
            val currentY = startY + (endY - startY) * fraction
            return PointF(currentX, currentY)
        }
    }
}