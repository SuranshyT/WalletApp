package kz.home.walletapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import kz.home.walletapp.R
import kz.home.walletapp.presentation.MainActivity
import kz.home.walletapp.presentation.login.EMAIL_KEY

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()
    private val animationDuration: Long = 1500

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderAnimations()

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        if(preferences?.getString(EMAIL_KEY, "") != ""){
            viewModel.setLoggedIn()
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner){launchMainScreen(it)}

    }

    private fun launchMainScreen(isSignedIn: Boolean){
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        intent.putExtra("isSigned", isSignedIn)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {startActivity(intent)}, animationDuration)
    }

    private fun renderAnimations() {
        val textView = requireView().findViewById<TextView>(R.id.tv_splash)
        textView.alpha = 0.0f
        textView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(animationDuration)
            .start()
    }
}