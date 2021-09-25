package com.learn.lavsam.mymaterial.ui.apibottom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learn.lavsam.mymaterial.R
import com.learn.lavsam.mymaterial.ui.api.EarthFragment
import com.learn.lavsam.mymaterial.ui.api.MarsFragment
import com.learn.lavsam.mymaterial.ui.api.WeatherFragment
import kotlinx.android.synthetic.main.activity_nasa_api_bottom.*

class NasaApiBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nasa_api_bottom)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
            }
        }

        bottom_navigation_view.selectedItemId = R.id.bottom_view_earth
        bottom_navigation_view.getOrCreateBadge(R.id.bottom_view_earth)
        val badge = bottom_navigation_view.getBadge(R.id.bottom_view_earth)
        badge?.maxCharacterCount = 2
        badge?.number = 999

        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                    bottom_navigation_view.removeBadge(R.id.bottom_view_earth)
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                }
            }
        }
    }
}