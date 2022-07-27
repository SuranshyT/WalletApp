package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                val builder: AlertDialog.Builder? = activity?.let {
                    AlertDialog.Builder(it)
                }

                builder?.setTitle("Enter amount:")

                // Set an EditText view to get user input
                val input = EditText(context)
                input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                builder?.setView(input)

                // 2. Chain together various setter methods to set the dialog characteristics
                builder?.setPositiveButton("OK"){
                    dialog, id ->
                    bankAccount.value = input.text.toString().toDouble() ?: 0.0
                    //println(input.text)
                    viewModel.addAccount(bankAccount)
                    dialog.cancel()
                    dismiss()
                    findNavController().navigate(R.id.action_chooseBankBottomSheetFragment2_to_accountsFragment)
                }

                // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                val dialog: AlertDialog? = builder?.create()

                dialog?.show()

                //viewModel.addAccount(bank)
                //dismiss()
                //findNavController().navigate(R.id.action_chooseBankBottomSheetFragment2_to_accountsFragment)
            }
        )
        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }
    }
}