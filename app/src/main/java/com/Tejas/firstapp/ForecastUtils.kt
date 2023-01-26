package com.Tejas.firstapp

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.widget.Toast


fun formatTempForDisplay(temp : Float,tempDisplaySetting : TempDisplaySetting) : String {
    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f\u00B0F",temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) *(5f/9f)
            return String.format("%.2f\u00B0C",temp)
        }
    }
}



fun showTempDisplaySettingDialog(context :Context, tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogAlert = AlertDialog.Builder(context)
        .setTitle("Display Units")
        .setMessage("Choose the Unit for temp Display.")
    // we can use it this way or the one used below with DialogAlert.
    //The buttons can also be impelmented in below ways
    // 1] this is using lambda
    dialogAlert
        .setPositiveButton("\u00B0C"){_,_ ->
        tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
    }
        .setNeutralButton("\u00B0F"){_,_ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        // 2]   this is the original way
//            .setNeutralButton("\u0B00F", object : DialogInterface.OnClickListener{
//                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    Toast.makeText(this,"\u0B00C",Toast.LENGTH_SHORT).show()
//                }
//
//            })
        .setOnDismissListener(){
            Toast.makeText(context,"Settings will be applied on app restart", Toast.LENGTH_LONG).show()
        }
        .show()
}
