package com.Tejas.firstapp.forecast

//import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.Tejas.firstapp.*
import com.Tejas.firstapp.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.

 * create an instance of this fragment.
 */
class CurrentForecastFragment : Fragment() {
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()

//    private lateinit var appNavigator: AppNavigator

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        appNavigator = context as AppNavigator
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)


        val locationName : TextView? = view?.findViewById(R.id.locationName)
        val tempText : TextView? = view?.findViewById(R.id.tempText1)
        val currentIcon  = view.findViewById<ImageView>(R.id.currentForecastImage)
        val emptyText = view.findViewById<TextView>(R.id.Emptytext)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
//        val zipcode = arguments?.getString(ZIP_CODE)?:""


        val currentWeatherObserver = Observer<CurrentWeather> {weather1->
            if(weather1.name != null) {

                emptyText.visibility = View.GONE
                progressBar.visibility = View.GONE
                locationName?.visibility = View.VISIBLE
                tempText?.visibility = View.VISIBLE
                currentIcon.visibility = View.VISIBLE

                locationName?.text = weather1.name
                tempText?.text = formatTempForDisplay(weather1.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
                val iconId1 = weather1.weatherIcon[0].icon
                currentIcon.load("http://openweathermap.org/img/wn/${iconId1}@2x.png")
            }
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner,currentWeatherObserver)

        val locationEntryButton : FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener{
            showLocationEntry()
        }

        locationRepository = LocationRepository(requireContext())

        val savedLocationObserver = Observer<Location> {savedLocation->
            when (savedLocation){
                is Location.Zipcode ->{
                    progressBar.visibility = View.VISIBLE
                    forecastRepository.loadCurrentForecast(savedLocation.zipcode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }




    companion object{
        const val ZIP_CODE = "zipcode"

        fun newInstance(zipcode : String) : CurrentForecastFragment{
            val fragment = CurrentForecastFragment()

            val args = Bundle()

            args.putString(ZIP_CODE,zipcode)
            fragment.arguments = args
            return fragment
        }


    }


}