package kz.home.walletapp.presentation.login

import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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

        terms.link(
            Pair("Terms", View.OnClickListener {
                Toast.makeText(requireActivity(), "Terms of Service Clicked", Toast.LENGTH_SHORT).show()
            }),
            Pair("Privacy Policy", View.OnClickListener {
                Toast.makeText(requireActivity(), "Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }))
    }
}