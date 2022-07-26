package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
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
            findNavController().navigate(R.id.action_walletTypeFragment_to_chooseBankBottomSheetFragment2, bundle)
        }

        cvCryptoAccount.setOnClickListener {
            bundle.putString("type", "crypto")
            findNavController().navigate(R.id.action_walletTypeFragment_to_chooseBankBottomSheetFragment2, bundle)
        }
    }
}