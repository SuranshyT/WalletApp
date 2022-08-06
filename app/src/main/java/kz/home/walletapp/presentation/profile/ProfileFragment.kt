package kz.home.walletapp.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.home.walletapp.R
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.EMAIL_KEY
import kz.home.walletapp.presentation.login.PASSWORD_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val accountsViewModel: AccountsViewModel by sharedViewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val email = preferences?.getString(EMAIL_KEY, "")
        val password = preferences?.getString(PASSWORD_KEY, "")

        val firstNameTv = view.findViewById<TextView>(R.id.first_name_tv)
        val lastNameTv = view.findViewById<TextView>(R.id.last_name_tv)
        val emailTv = view.findViewById<TextView>(R.id.email_tv)
        val phoneTv = view.findViewById<TextView>(R.id.phone_tv)

        lifecycleScope.launch {
            if (email != null && password != null) {
                profileViewModel.loginUser(email, password).collect {
                    if (it != null) {
                        withContext(Dispatchers.Main) {
                            firstNameTv.text = it.firstName
                            lastNameTv.text = it.lastName
                            emailTv.text = it.email
                            phoneTv.text = it.phone
                        }
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.btn_log_out).setOnClickListener {
            preferences.edit().clear().apply()
            accountsViewModel.logOut()
        }
    }
}