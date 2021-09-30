package com.learn.lavsam.mymaterial.ui.animations

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_anima_visibility.*

class AnimaVisibilityActivity : AppCompatActivity() {

    private var textIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anima_visibility)

        btn.setOnClickListener {
            TransitionManager.beginDelayedTransition(container, AutoTransition())
            textIsVisible = !textIsVisible
            text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            text2.visibility = if (textIsVisible) View.GONE else View.VISIBLE
            image.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }
    }
}