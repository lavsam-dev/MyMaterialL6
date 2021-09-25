package com.learn.lavsam.mymaterial.ui.animations

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_anima_explode.*

private const val EXPLODE_DURATION: Long = 1000
private const val ITEM_COUNT = 32

class AnimaExplodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anima_explode)
        recycler_view.adapter = Adapter()
    }

    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.duration = EXPLODE_DURATION
        TransitionManager.beginDelayedTransition(recycler_view, explode)
        recycler_view.adapter = null
    }

    private fun explodeOne(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                    onBackPressed()
                }
            })
        explode.duration = EXPLODE_DURATION
        TransitionManager.beginDelayedTransition(recycler_view, set)
        recycler_view.adapter = null
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_anima_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explodeOne(it)
            }
        }

        override fun getItemCount(): Int {
            return ITEM_COUNT
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}