package kz.home.walletapp.presentation.welcome

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kz.home.walletapp.R

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skipTutorialTextView = view.findViewById<TextView>(R.id.tv_skip_tutorial)
        skipTutorialTextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        val startTutorialButton = view.findViewById<TextView>(R.id.btn_start_tutorial)
        startTutorialButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_tutorialFragment)
        }
    }
}