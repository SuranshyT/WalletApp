package kz.home.walletapp.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import kz.home.walletapp.R
import kz.home.walletapp.utils.link
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var retypePassword: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val terms = view.findViewById<TextView>(R.id.termsAndPrivacy)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        emailInput = view.findViewById(R.id.login_input)
        passwordInput = view.findViewById(R.id.password_input)
        retypePassword = view.findViewById(R.id.password_repeat_input)

        registerButton.setOnClickListener {
            if(emailInput.text.toString().isNotBlank() && passwordInput.text.toString().isNotBlank() && retypePassword.text.toString().isNotBlank()) {
                emailInput.error = null
                if(passwordInput.text.toString() == retypePassword.text.toString()) {
                    val email = emailInput.text.toString()
                    val password = passwordInput.text.toString()
                    viewModel.registerUser(email, password)
                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_loginFragment)
                }else{
                    emailInput.error = "Passwords are not the same"
                }
            }else{
                emailInput.error = "No login or password"
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