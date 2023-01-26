package com.Tejas.firstapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")

class ForecastDetailsViewModelFactory(private val args : ForecastDetailsFragmentArgs) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)){
            return ForecastDetailsViewModel(args) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class ForecastDetailsViewModel(args : ForecastDetailsFragmentArgs) : ViewModel() {

    private val _viewState : MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState : LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description,
            date = DATE_FORMAT.format(Date(args.date*1000)),
            icon = "http://openweathermap.org/img/wn/${args.iconId}@2x.png"
        )
    }

//    fun processArgs(args : ForecastDetailsFragmentArgs){
//        _viewState.value = ForecastDetailsViewState(
//            temp = args.temp,
//            description = args.description,
//            date = DATE_FORMAT.format(Date(args.date*1000)),
//            icon = "http://openweathermap.org/img/wn/${args.iconId}@2x.png"
//
//    }

}