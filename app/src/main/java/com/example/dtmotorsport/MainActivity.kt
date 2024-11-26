package com.example.dtmotorsport
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {


    class HomeFragment : Fragment(R.layout.fragment_home)


    class DriversFragment : Fragment(R.layout.fragment_drivers)


    class StandingsFragment : Fragment(R.layout.fragment_standings)


    class SettingsFragment : Fragment(R.layout.fragment_settings)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    replaceFragment(HomeFragment())
                    true
                }

                R.id.nav_drivers -> {

                    replaceFragment(DriversFragment())
                    true
                }

                R.id.nav_standings -> {

                    replaceFragment(StandingsFragment())
                    true
                }

                R.id.nav_settings -> {

                    replaceFragment(SettingsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}





