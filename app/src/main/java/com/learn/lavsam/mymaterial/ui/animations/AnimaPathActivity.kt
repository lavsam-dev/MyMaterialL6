package com.learn.lavsam.mymaterial.ui.animations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_anima_path_transitions.*

private const val CHANGEBOUNDS_DURATION: Long = 500

class AnimaPathActivity : AppCompatActivity() {

    private var toRightAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anima_path_transitions)
        button.setOnClickListener {
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = CHANGEBOUNDS_DURATION
            TransitionManager.beginDelayedTransition(
                transitions_container,
                changeBounds
            )

            toRightAnimation = !toRightAnimation
            val params = button.layoutParams as FrameLayout.LayoutParams
            params.gravity =
                if (toRightAnimation) Gravity.END or Gravity.BOTTOM else Gravity.START or Gravity.TOP
            button.layoutParams = params
        }
    }
}