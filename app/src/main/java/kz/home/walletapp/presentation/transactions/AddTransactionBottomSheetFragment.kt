package kz.home.walletapp.presentation.transactions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.home.walletapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddTransactionBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: TransactionsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_transaction_bottomsheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_addTransactionBottomSheetFragment_to_transactionsFragment)
        }
    }
}