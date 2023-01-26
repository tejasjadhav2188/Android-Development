package com.Tejas.firstapp.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.Tejas.firstapp.AppNavigator
import com.Tejas.firstapp.Location
import com.Tejas.firstapp.LocationRepository
import com.Tejas.firstapp.R


/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

//    private lateinit var appNavigator: AppNavigator
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        appNavigator = context as AppNavigator
//    }
    private lateinit var locationRepository: LocationRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        locationRepository = LocationRepository(requireContext())
        val view =  inflater.inflate(R.layout.fragment_location_entry, container, false)

        val zipCodeEntry : EditText = view.findViewById(R.id.EditText1)

        val entryButton : Button = view.findViewById(R.id.Button1)

        //This makes Sound and .show() displays ZipCode when button pressed
        entryButton.setOnClickListener{
            val zipCode:String = zipCodeEntry.text.toString()
//            if(zipCode.length != 5) {
//                Toast.makeText(requireContext(),R.string.Zip_Code_entry_error, Toast.LENGTH_SHORT).show()
//            }else {
                locationRepository.saveLocation(Location.Zipcode(zipCode))
                findNavController().navigateUp()
//            }

        }
        return view
    }


}
