package kz.home.walletapp.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import kz.home.walletapp.R
import kz.home.walletapp.data.User
import kz.home.walletapp.utils.link
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var retypePassword: TextInputEditText
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerButton = view.findViewById<Button>(R.id.registerButton)

        emailInput = view.findViewById(R.id.login_input)
        passwordInput = view.findViewById(R.id.password_input)
        retypePassword = view.findViewById(R.id.password_repeat_input)
        firstNameInput = view.findViewById(R.id.first_name_input)
        lastNameInput = view.findViewById(R.id.last_name_input)

        registerButton.setOnClickListener {
            onRegisterClicked(view)
        }
    }

    private fun onRegisterClicked(view: View) {
        if (emailInput.text.toString().isNotBlank()
            && passwordInput.text.toString().isNotBlank()
            && retypePassword.text.toString().isNotBlank()
            && firstNameInput.text.toString().isNotBlank()
            && lastNameInput.text.toString().isNotBlank()
        ) {
            emailInput.error = null
            if (passwordInput.text.toString() == retypePassword.text.toString()) {
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()
                val firstName = firstNameInput.text.toString()
                val lastName = lastNameInput.text.toString()

                val user = User(email, password, firstName, lastName)
                checkUser(view, user)
            } else {
                emailInput.error = "Passwords are not the same"
            }
        } else {
            emailInput.error = "Please fill out all fields"
        }
    }

    private fun checkUser(view: View, user: User) {
        var n = 0
        lifecycleScope.launch {
            viewModel.getUsers().collect {
                if (it != null) {
                    for (i in it.indices) {
                        if (it[i].email == user.email) {
                            n +=1
                            Toast.makeText(requireActivity(), "Such user is already registered", Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (n == 0) {
                        viewModel.registerUser(user)
                        Navigation.findNavController(view).popBackStack()
                    }
                } else {
                    viewModel.registerUser(user)
                    Navigation.findNavController(view).popBackStack()
                }
            }
        }
    }
}