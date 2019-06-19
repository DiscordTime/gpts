package com.discordtime.gpts.lobby

import android.Manifest
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.discordtime.gpts.R
import com.discordtime.gpts.listplaces.view.ListPlacesFragment
import com.discordtime.gpts.maps.view.MapViewFragment

class LobbyActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private val fragmentListPlaces = ListPlacesFragment()
    private val fragmentMaps = MapViewFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentListPlaces

    private val PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var textMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        //sets up location permission
        verifyPermissions(PERMISSION)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        toolbar = supportActionBar!!
        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //initiates maps fragment firstly
        fm.beginTransaction().replace(R.id.container, fragmentMaps).commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_map -> {
                toolbar.title = "Maps"
                fm.beginTransaction().replace(R.id.container, fragmentMaps).commit()
                active = fragmentMaps
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_list_places -> {
                toolbar.title = "List Places"
                fm.beginTransaction().replace(R.id.container, fragmentListPlaces).commit()
                active = fragmentListPlaces
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                toolbar.title = "Profile"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun verifyPermissions(per: Array<String>): Boolean {
        for(i in per.indices) {
            val permission = ActivityCompat.checkSelfPermission(this, per[i])
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(this, per,1)
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PERMISSION_DENIED)
                verifyPermissions(PERMISSION)
        }
    }
}