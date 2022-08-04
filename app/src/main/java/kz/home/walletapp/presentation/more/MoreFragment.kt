package kz.home.walletapp.presentation.more

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import kz.home.walletapp.R
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.EMAIL_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreFragment : Fragment(R.layout.fragment_more) {
    private val accountsViewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        view.findViewById<TextView>(R.id.tv_name_log_out).text = preferences?.getString(EMAIL_KEY, "")

        view.findViewById<Button>(R.id.btn_log_out).setOnClickListener {
            preferences.edit().clear().apply()
            accountsViewModel.logOut()
        }
    }

}