package com.gpstracker

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.ankit.gpslibrary.ADLocation
import org.ankit.gpslibrary.MyTracker


class MainActivity : AppCompatActivity(), MyTracker.ADLocationListener {
    var txt_location: TextView? = null;
    var button_submit: Button? = null;
    var loc: ADLocation? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_location =  findViewById(R.id.txt_location) as TextView
        button_submit =  findViewById(R.id.button_submit) as Button
        button_submit!!.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                //ask for permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    101
                )
            } else {
                findLoc()
            }
        }
    }

    private fun findLoc() {
        MyTracker(applicationContext,this).track()
        txt_location!!.text = loc.toString()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            findLoc()
        }
    }

    override fun whereIAM(loc: ADLocation?) {
        System.out.println("ADLocation "+loc)
        this.loc = loc
    }

}