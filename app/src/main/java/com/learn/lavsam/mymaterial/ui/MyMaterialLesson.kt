package com.learn.lavsam.mymaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learn.lavsam.mymaterial.R
import com.learn.lavsam.mymaterial.ui.picture.PictureOfTheDayFragment

class MyMaterialLesson : AppCompatActivity() {
    var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bool = savedInstanceState?.getBoolean("bool") ?: true
        if (bool)
            setTheme(R.style.MyAppTheme)
        else
            setTheme(R.style.MyAppTheme_Indigo)

        bool = !bool

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("bool", bool)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}