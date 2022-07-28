package kz.home.walletapp.presentation.accounts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.domain.model.Bank
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseBankBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var recyclerViewAdapter: AddBankAdapter
    private val viewModel: AccountsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_choose_bank_bottomsheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recyclerView = view.findViewById<RecyclerView>(R.id.bs_recycler_view)
        setupRecyclerView(recyclerView)

        val type = arguments?.getString("type")
        if(type == "bank"){
            view.findViewById<TextView>(R.id.tv_bs_title).text = "Choose bank"
            recyclerViewAdapter.submitList(Data.bankList)
        }else if(type == "crypto"){
            view.findViewById<TextView>(R.id.tv_bs_title).text = "Choose wallet"
            recyclerViewAdapter.submitList(Data.cryptoList)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerViewAdapter = AddBankAdapter(
            onBankClickListener = { bank ->
                val bankAccount: Bank = bank
                val builder = MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)

                builder.setTitle("Enter amount:")

                val input = EditText(context)
                input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                builder.setView(input)

                builder.setPositiveButton("OK"){
                        dialog, _ ->
                    bankAccount.value = input.text.toString().toDouble() ?: 0.0
                    viewModel.addAccount(bankAccount)
                    dialog.cancel()
                    dismiss()
                    viewModel.count()
                    findNavController().navigate(R.id.action_chooseBankBottomSheetFragment2_to_accountsFragment)
                    findNavController().popBackStack()
                    findNavController().popBackStack()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        )

        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }
    }
}