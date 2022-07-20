package kz.home.walletapp.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import kz.home.walletapp.R
import kz.home.walletapp.utils.link

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val terms = view.findViewById<TextView>(R.id.termsAndPrivacy)
        val registerButton = view.findViewById<Button>(R.id.registerButton)
        val login = view.findViewById<TextInputEditText>(R.id.login_input)
        val password = view.findViewById<TextInputEditText>(R.id.password_input)
        val retypePassword = view.findViewById<TextInputEditText>(R.id.password_repeat_input)

        registerButton.setOnClickListener {
            if(login.text.toString().isNotBlank() && password.text.toString().isNotBlank() && retypePassword.text.toString().isNotBlank()) {
                login.error = null
                if(password.text.toString() == retypePassword.text.toString()) {

                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_loginFragment)
                }else{
                    login.error = "Passwords are not the same"
                }
            }else{
                login.error = "No login or password"
            }
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