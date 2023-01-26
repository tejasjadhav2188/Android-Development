package com.Tejas.firstapp.forecast

import android.content.Context
//import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Tejas.firstapp.*
import com.Tejas.firstapp.api.DailyForecast
import com.Tejas.firstapp.api.WeeklyForecast
//import com.Tejas.firstapp.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.

 * create an instance of this fragment.
 */
class WeeklyForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository

//    private lateinit var appNavigator: AppNavigator
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        appNavigator = context as AppNavigator
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        val zipcode = arguments?.getString(CurrentForecastFragment.ZIP_CODE)?:""


        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val emptyText = view.findViewById<TextView>(R.id.Emptytext)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)


        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val locationEntryButton : FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener{
            showLocationEntry()
        }



        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){
            showForecastDetails(it)
        }

        recyclerView.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<WeeklyForecast>{weeklyForecast->
            emptyText.visibility = View.GONE
            progressBar.visibility = View.GONE
            // Update the list
            dailyForecastAdapter.submitList(weeklyForecast.daily)
        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner,weeklyForecastObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocation->
            when (savedLocation){
                is Location.Zipcode ->{
                    progressBar.visibility = View.VISIBLE
                    forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)

        return view
    }

    private fun showLocationEntry(){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast){
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val date = forecast.date
        val iconId = forecast.weather[0].icon
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp,description,date,iconId)
        findNavController().navigate(action)
    }

    companion object{
        const val ZIP_CODE = "key_zipcode"

        fun newInstance(zipcode : String) : WeeklyForecastFragment{
            val fragment = WeeklyForecastFragment()

            val args = Bundle()

            args.putString(ZIP_CODE,zipcode)
            fragment.arguments = args
            return fragment
        }


    }


}