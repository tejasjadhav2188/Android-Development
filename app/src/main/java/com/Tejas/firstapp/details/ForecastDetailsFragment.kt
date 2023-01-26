package com.Tejas.firstapp.details


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.Tejas.firstapp.*
import com.Tejas.firstapp.databinding.FragmentForecastDetailsBinding
import com.Tejas.firstapp.databinding.FragmentLocationEntryBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.Menu
import android.view.MenuItem


private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class ForecastDetailsFragment : Fragment() {

    private val args:ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel : ForecastDetailsViewModel by viewModels(
        factoryProducer = {  viewModelFactory  }
    )


    private var _binding : FragmentForecastDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tempDisplaySettingManager : TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val layout =  inflater.inflate(R.layout.fragment_forecast_details,container,false)
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container,false)



        viewModelFactory = ForecastDetailsViewModelFactory(args)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

//        val tempText :TextView = layout.findViewById(R.id.tempText)
//        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)

//        binding.tempText.text = formatTempForDisplay(args.temp,tempDisplaySettingManager.getTempDisplaySetting())
//        binding.descriptionText.text = args.description
//        binding.dateDetailsFrag.text = DATE_FORMAT.format(Date((args.date)*1000))
//        binding.IconDetailsFrag.load("http://openweathermap.org/img/wn/${args.iconId}@2x.png")


        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater : MenuInflater = menuInflater
//        inflater.inflate(R.menu.settings_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.display_unit ->{
//                showTempDisplaySettingDialog(this, tempDisplaySettingManager )
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewStateObserver = Observer<ForecastDetailsViewState>{viewState->
            binding.tempText.text = formatTempForDisplay(viewState.temp,tempDisplaySettingManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
            binding.dateDetailsFrag.text = viewState.date
            binding.IconDetailsFrag.load(viewState.icon)
        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)
//        viewModel.processArgs(args)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_forecast_details)
//        setTitle(R.string.forecast_detail_activity)
//
//
//        val TempText :TextView = layout.findViewById(R.id.tempText)
//        val DescriptionText = layout.findViewById<TextView>(R.id.descriptionText)
//
//        TempText.text = formatTempForDisplay(intent.getFloatExtra("key_temp",0f),tempDisplaySettingManager.getTempDisplaySetting())
//        DescriptionText.text = intent.getStringExtra("key_description")
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater : MenuInflater = menuInflater
//        inflater.inflate(R.menu.settings_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.display_unit ->{
//                showTempDisplaySettingDialog(this, tempDisplaySettingManager )
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


}