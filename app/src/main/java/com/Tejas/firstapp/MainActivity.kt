package com.Tejas.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.Tejas.firstapp.forecast.CurrentForecastFragmentDirections
import androidx.navigation.ui.setupWithNavController
import com.Tejas.firstapp.forecast.WeeklyForecastFragment
import com.Tejas.firstapp.forecast.WeeklyForecastFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.Tejas.firstapp.location.LocationEntryFragmentDirections

class MainActivity : AppCompatActivity() {

    // region  Setup Methods

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration =  AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setTitle(R.string.app_name)

        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)

//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragmentContainer,LocationEntryFragment())
//            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.display_unit ->{
                showTempDisplaySettingDialog(this,tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun navigateToCurrentForecast(zipcode: String) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragmentContainer,CurrentForecastFragment.newInstance(zipcode))
//            .commit()

//        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment()
//        findNavController(R.id.nav_host_fragment).navigate(action)
    }
//
//    override fun navigateToLocationEntry() {
////        supportFragmentManager
////            .beginTransaction()
////            .replace(R.id.fragmentContainer,LocationEntryFragment())
////            .commit()
//        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
//        findNavController(R.id.nav_host_fragment).navigate(action)
//    }
//
//    override fun navigateToForecastDetails(forecast:DailyForecast){
//        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.description)
//        findNavController(R.id.nav_host_fragment).navigate(action)
//    }
//
//    override fun navigateToForecastDetails1(forecast: DailyForecast) {
//        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp,forecast.description)
//        findNavController(R.id.nav_host_fragment).navigate(action)
//    }



    // endregion TearDown Methods
//}