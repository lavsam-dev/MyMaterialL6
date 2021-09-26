package com.learn.lavsam.mymaterial.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.learn.lavsam.mymaterial.R
import com.learn.lavsam.mymaterial.ui.animations.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> activity?.let {
                    startActivity(Intent(it, AnimationsActivity::class.java))
                }
                R.id.navigation_two -> activity?.let {
                    startActivity(Intent(it, AnimaBonusActivity::class.java))
                }
                R.id.navigation_three -> activity?.let {
                    startActivity(Intent(it, AnimaEnlargeActivity::class.java))
                }
                R.id.navigation_four -> activity?.let {
                    startActivity(Intent(it, AnimaExplodeActivity::class.java))
                }
                R.id.navigation_five -> activity?.let {
                    startActivity(Intent(it, AnimaFabActivity::class.java))
                }
                R.id.navigation_six -> activity?.let {
                    startActivity(Intent(it, AnimaPathActivity::class.java))
                }
                R.id.navigation_seven -> activity?.let {
                    startActivity(Intent(it, AnimaShuffleActivity::class.java))
                }
                R.id.navigation_eight -> activity?.let {
                    startActivity(Intent(it, AnimaStateListActivity::class.java))
                }
                R.id.navigation_nine -> activity?.let {
                    startActivity(Intent(it, AnimaVisibilityActivity::class.java))
                }
            }
            dismiss()
            true
        }
    }
}