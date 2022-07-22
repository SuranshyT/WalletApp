package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kz.home.walletapp.R

class AccountsFragment : Fragment(R.layout.fragment_accounts) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())

        if(acct!=null){
            view.findViewById<TextView>(R.id.textView2).text = acct.displayName
        }
    }
}