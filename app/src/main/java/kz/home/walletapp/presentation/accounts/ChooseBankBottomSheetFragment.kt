package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.domain.model.Bank

class ChooseBankBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_choose_bank_bottomsheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.bs_recycler_view)
        setupRecyclerView(recyclerView)

    }

    private fun setupRecyclerView(recyclerView: RecyclerView?) {
        val recyclerViewAdapter = AddBankAdapter(
            onBankClickListener = {
                (parentFragment as? BankBottomSheetListener)?.addAccountClicked(it)
                dismiss()
                findNavController().navigate(R.id.action_chooseBankBottomSheetFragment2_to_accountsFragment)
            }
        )
        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }

        val type = arguments?.getString("type")
        if(type == "bank"){
            recyclerViewAdapter.submitList(Data.bankList)
        }else if(type == "crypto"){
            recyclerViewAdapter.submitList(Data.cryptoList)
        }
    }
    interface BankBottomSheetListener {
        fun addAccountClicked(bank: Bank)
    }
}