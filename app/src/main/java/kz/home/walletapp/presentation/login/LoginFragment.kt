package kz.home.walletapp.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import kz.home.walletapp.R
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.utils.link
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val EMAIL_KEY = "EMAIL"
const val PASSWORD_KEY = "PASSWORD"

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val authViewModel: AuthViewModel by sharedViewModel()
    private val accountsViewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailInput = view.findViewById<TextInputEditText>(R.id.login_input)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.password_input)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerLink = view.findViewById<TextView>(R.id.registerLink)
        val terms = view.findViewById<TextView>(R.id.termsAndPrivacy)
        val googleSignIn = view.findViewById<CardView>(R.id.google_cv)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if(account!=null){
            findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
        }

        loginButton.setOnClickListener {
            if(emailInput.text.toString().isNotBlank() && passwordInput.text.toString().isNotBlank()) {
                emailInput.error = null
                val email = emailInput.text.toString().trim()
                val password = passwordInput.text.toString().trim()
                accountsViewModel.clear()
                //accountsViewModel.getUser(email, password)

                lifecycleScope.launch {
                    authViewModel.loginUser(email, password).collect {
                        if (it != null) {
                            //accountsViewModel.getUser(email, password)
                            //Log.e("", "$email  -  $password")
                            //accountsViewModel.initialize()
                            //accountsViewModel.count()
                            //accountsViewModel.clear()
                            val bundle = bundleOf(EMAIL_KEY to email, PASSWORD_KEY to password)
                            Navigation.findNavController(view)
                                .navigate(R.id.action_loginFragment_to_tabsFragment, bundle)
                        } else {
                            Toast.makeText(requireActivity(), "No such user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }else{
                emailInput.error = "No login or password"
            }
        }

        registerLink.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

        googleSignIn.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }


        terms.link(
            Pair("Terms", View.OnClickListener {
                Toast.makeText(requireActivity(), "Terms of Service Clicked", Toast.LENGTH_SHORT).show()
            }),
            Pair("Privacy Policy", View.OnClickListener {
                Toast.makeText(requireActivity(), "Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }))
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
        } catch (e: ApiException) {
            Log.w("WWW", "signInResult:failed code=" + e.statusCode)
        }
    }
}