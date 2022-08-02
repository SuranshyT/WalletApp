package kz.home.walletapp.presentation.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.home.walletapp.R
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.EMAIL_KEY
import kz.home.walletapp.presentation.login.PASSWORD_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TabsFragment : Fragment(R.layout.fragment_tabs) {
    private val accountsViewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        NavigationUI.setupWithNavController(view.findViewById<BottomNavigationView>(R.id.bottom_nav), navController)

        val e = arguments?.getString(EMAIL_KEY)
        val p = arguments?.getString(PASSWORD_KEY)

        if (e != null && p != null) {
            accountsViewModel.initialize(e, p)
        }
    }
}