package kz.home.walletapp.presentation.transactions

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import kz.home.walletapp.R
import kz.home.walletapp.data.Transaction
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionBottomSheetFragment : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: AccountsViewModel by sharedViewModel()
    lateinit var chosenDateTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_transaction_bottomsheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val accounts: List<Bank> = viewModel.getMyAccounts()

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, accounts)
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.actv_chosen_bank)
        autoCompleteTextView.setAdapter(adapter)

        val pickDateTv = view.findViewById<TextView>(R.id.pick_date_tv)
        pickDateTv.setOnClickListener {
            showDatePickerDialog(view)
        }

        var chosenBank: Bank? = null

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            chosenBank = parent.getItemAtPosition(position) as Bank
        }

        val transactionNameEditText = view.findViewById<EditText>(R.id.transaction_name)
        val transactionValueEditText = view.findViewById<EditText>(R.id.transaction_value)
        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_button)
        var sign = ""
        chosenDateTV = view.findViewById(R.id.tv_chosen_date)

        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        chosenDateTV.text = currentDate

        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            onAddClicked(
                chosenBank,
                transactionNameEditText,
                transactionValueEditText,
                toggleButton,
                sign
            )
        }
    }

    private fun onAddClicked(
        chosenBank: Bank?,
        transactionNameEditText: EditText,
        transactionValueEditText: EditText,
        toggleButton: MaterialButtonToggleGroup,
        sign: String
    ) {
        var sign1 = sign
        if (chosenBank != null && transactionNameEditText.text.isNotBlank() && transactionValueEditText.text.isNotEmpty() && chosenDateTV.text.toString() != "date") {
            val transactionName = transactionNameEditText.text.toString()
            val transactionValue = transactionValueEditText.text.toString().toDouble()
            val checkedId = toggleButton.checkedButtonId

            if (checkedId == R.id.earned_button1) {
                sign1 = "+"
            } else if (checkedId == R.id.spent_button2) {
                sign1 = "-"
            }

            if (sign1 == "-" && transactionValue > chosenBank.value) {
                Toast.makeText(requireContext(), "Value is exceed amount", Toast.LENGTH_SHORT)
                    .show()
            } else if (transactionValue == 0.0) {
                Toast.makeText(requireContext(), "0 is not allowed value", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.addTransaction(
                    Transaction(
                        chosenDateTV.text.toString(),
                        transactionName,
                        chosenBank.name,
                        chosenBank.img,
                        transactionValue,
                        sign1
                    )
                )
                dismiss()
                findNavController().navigate(R.id.action_addTransactionBottomSheetFragment_to_transactionsFragment)
            }
        } else {
            Toast.makeText(requireContext(), "Enter all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val currentDateString = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(c.time)
        chosenDateTV.text = currentDateString
    }
}