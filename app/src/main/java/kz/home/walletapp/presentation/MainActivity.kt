package kz.home.walletapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.home.walletapp.R

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //this.deleteDatabase("MyDB")

        val isSignedIn = isSignedIn()
        val navController = getRootNavController()

        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(
            if (isSignedIn) {
                getTabsDestination()
            } else {
                getSignInDestination()
            }
        )
        navController.graph = graph

        activate(navController)

        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.welcomeFragment -> bottomNavigationView.visibility = View.GONE
                R.id.loginFragment -> bottomNavigationView.visibility = View.GONE
                R.id.registrationFragment -> bottomNavigationView.visibility = View.GONE
                R.id.tutorialFragment -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }*/

    }

    private fun activate(navController: NavController) {
        if (this.navController == navController) return
        this.navController = navController
    }

    private fun getRootNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        return navHost.navController
    }

    private fun isSignedIn(): Boolean {
        return intent.extras?.getBoolean("isSigned") ?: false
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph

    private fun getTabsDestination(): Int = R.id.tabsFragment

    private fun getSignInDestination(): Int = R.id.welcomeFragment

    override fun onDestroy() {
        navController = null
        super.onDestroy()
    }
}