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
import kz.home.walletapp.data.MyDatabase
import kz.home.walletapp.data.User
import kz.home.walletapp.presentation.App
import kz.home.walletapp.utils.Executors
import kz.home.walletapp.utils.link
import kz.home.walletapp.utils.randomID
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    //private val viewModel: RegistrationViewModel by viewModel()
    val executors = Executors()
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var retypePassword: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val terms = view.findViewById<TextView>(R.id.termsAndPrivacy)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        email = view.findViewById(R.id.login_input)
        password = view.findViewById(R.id.password_input)
        retypePassword = view.findViewById(R.id.password_repeat_input)

        registerButton.setOnClickListener {
            if(email.text.toString().isNotBlank() && password.text.toString().isNotBlank() && retypePassword.text.toString().isNotBlank()) {
                email.error = null
                if(password.text.toString() == retypePassword.text.toString()) {
                    val e = email.text.toString()
                    val p = password.text.toString()
                    //viewModel.insertData(e, p)
                    //insertData()
                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_loginFragment)
                }else{
                    email.error = "Passwords are not the same"
                }
            }else{
                email.error = "No login or password"
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

    override fun onDestroy() {
        super.onDestroy()

        executors.shutdown()
    }

    private fun insertData() {
        val e = email.text.toString()
        val p = password.text.toString()
        val insert = User(randomID(), e, p)
        executors.diskIO().execute {

            (requireActivity().applicationContext as App).db.userDao().insertUser(insert)
        }
    }

}