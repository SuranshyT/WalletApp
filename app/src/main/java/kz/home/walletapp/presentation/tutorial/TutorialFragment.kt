package kz.home.walletapp.presentation.tutorial

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import kz.home.walletapp.R

class TutorialFragment : Fragment(R.layout.fragment_tutorial) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skipButton = view.findViewById<Button>(R.id.skipButton)
        skipButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_tutorialFragment_to_loginFragment)
        }
    }
}