package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kz.home.walletapp.R

class WalletTypeFragment : Fragment(R.layout.fragment_wallet_type) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cvBankAccount = view.findViewById<CardView>(R.id.cv_bank_account)
        val cvCryptoAccount = view.findViewById<CardView>(R.id.cv_crypto_account)

        val bundle = Bundle()
        cvBankAccount.setOnClickListener {
            bundle.putString("type", "bank")
            findNavController().navigate(R.id.action_walletTypeFragment_to_chooseBankBottomSheetFragment, bundle)
        }

        cvCryptoAccount.setOnClickListener {
            bundle.putString("type", "crypto")
            findNavController().navigate(R.id.action_walletTypeFragment_to_chooseBankBottomSheetFragment, bundle)
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (keyCode == KeyEvent.KEYCODE_BACK) {
                    findNavController().navigate(R.id.action_walletTypeFragment_to_accountsFragment)
                    true
                } else false
            }
        })
    }
}