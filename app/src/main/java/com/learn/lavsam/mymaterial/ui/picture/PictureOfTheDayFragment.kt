package com.learn.lavsam.mymaterial.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.learn.lavsam.mymaterial.R
import com.learn.lavsam.mymaterial.ui.MyMaterialLesson
import com.learn.lavsam.mymaterial.ui.api.NasaApiActivity
import com.learn.lavsam.mymaterial.ui.apibottom.NasaApiBottomActivity
import com.learn.lavsam.mymaterial.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val nasaDate: Calendar = Calendar.getInstance()
    private var nasaDateCalc: Calendar = nasaDate.clone() as Calendar
    private val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var apiDate: String = sdf.format(nasaDateCalc.time)

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiDate = sdf.format(nasaDateCalc.time)
        viewModel.getData(apiDate)
            .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomAppBar(view)
        chipGroupMain.setOnCheckedChangeListener { chipGroupMain, position ->
            chipGroupMain.findViewById<Chip>(position)?.let {
                nasaDateCalc = nasaDate.clone() as Calendar;
                when (position) {
                    1 -> {
                        nasaDateCalc.add(Calendar.DATE, -2)
                    }
                    2 -> {
                        nasaDateCalc.add(Calendar.DATE, -1)
                    }
                }
                apiDate = sdf.format(nasaDateCalc.time)
//                val apiDateNasa = sdf.format(nasaDate.time)
//                Toast.makeText(context, "Выбран ${it.text} Дата ${apiDate} Nasa ${apiDateNasa}", Toast.LENGTH_SHORT).show()
                viewModel.getData(apiDate)
                    .observe(
                        this@PictureOfTheDayFragment,
                        Observer<PictureOfTheDayData> { renderData(it) })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_theme -> {
                requireActivity().setTheme(R.style.MyAppTheme_Indigo)
                requireActivity().recreate()
            }
            R.id.app_bar_fav -> activity?.let {
                startActivity(Intent(it, NasaApiBottomActivity::class.java))
//                toast("Favourite")
            }
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, SettingsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_api -> activity?.let {
                startActivity(Intent(it, NasaApiActivity::class.java))
//                startActivity(Intent(it, ApiActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
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
                    image_view.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
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

    private fun setBottomAppBar(view: View) {
        val context = activity as MyMaterialLesson
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
