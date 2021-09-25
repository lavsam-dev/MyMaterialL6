package com.learn.lavsam.mymaterial.ui.api

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.learn.lavsam.mymaterial.R
import com.learn.lavsam.mymaterial.ui.picture.PictureOfTheDayData
import com.learn.lavsam.mymaterial.ui.picture.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    private val nasaDate: Calendar = Calendar.getInstance()
    private val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var apiDate: String = ""

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val rnds = (30..2000).random()
        nasaDate.add(Calendar.DATE, -rnds)
        apiDate = sdf.format(nasaDate.time)
//        toast(apiDate)
        viewModel.getData(apiDate)
            .observe(this@EarthFragment, Observer<PictureOfTheDayData> { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                bottom_sheet_description.text = serverResponseData.explanation
                bottom_sheet_description_header.text = serverResponseData.title
                bottom_sheet_description_date.text = apiDate

                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    image_view_earth.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}
