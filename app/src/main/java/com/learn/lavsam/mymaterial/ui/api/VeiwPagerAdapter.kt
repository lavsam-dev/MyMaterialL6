package com.learn.lavsam.mymaterial.ui.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val WEATHER_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())
    private val fragmentsName = arrayOf("Земля", "Марс", "Погода")

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return fragments[position].javaClass.simpleName
        return fragmentsName[position]
    }
}
