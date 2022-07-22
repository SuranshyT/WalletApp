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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import kz.home.walletapp.R
import kz.home.walletapp.utils.link


class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = view.findViewById<TextInputEditText>(R.id.login_input)
        val password = view.findViewById<TextInputEditText>(R.id.password_input)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerLink = view.findViewById<TextView>(R.id.registerLink)
        val terms = view.findViewById<TextView>(R.id.termsAndPrivacy)
        val googleSignIn = view.findViewById<CardView>(R.id.google_cv)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if(account!=null){
            findNavController().navigate(R.id.action_loginFragment_to_accountsFragment)
        }
        //updateUI(account)



        loginButton.setOnClickListener {
            /*if(login.text.toString().isNotBlank() && password.text.toString().isNotBlank()) {
                login.error = null*/

                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_accountsFragment)
            /*}else{
                login.error = "No login or password"
            }*/
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

                //dosomeop()
            }
        }

        googleSignIn.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            //registerForActivityResult(signInIntent, RC_SIGN_IN)
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

            // Signed in successfully, show authenticated UI.
            //updateUI(account)
            findNavController().navigate(R.id.action_loginFragment_to_accountsFragment)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("WWW", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }
}