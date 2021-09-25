package com.learn.lavsam.mymaterial.ui.animations

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_anima_shuffle.*
import java.util.*
import kotlin.collections.ArrayList

class AnimaShuffleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anima_shuffle)

        val titles: MutableList<String> = ArrayList()
        for (i in 0..4) {
            titles.add(String.format("Item %d", i + 1))
        }
        createViews(transitions_container, titles)
        button.setOnClickListener {
            TransitionManager.beginDelayedTransition(transitions_container, ChangeBounds())
            titles.shuffle()
            createViews(transitions_container, titles)
        }
    }

    private fun createViews(layout: ViewGroup, titties: List<String>) {
        layout.removeAllViews()
        for (titty in titties) {
            val textView = TextView(this)
            textView.text = titty
            textView.textSize = 24f
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            textView.setBackgroundColor(color)
            textView.gravity = Gravity.CENTER_HORIZONTAL
            ViewCompat.setTransitionName(textView, titty)
            layout.addView(textView)
        }
    }
}