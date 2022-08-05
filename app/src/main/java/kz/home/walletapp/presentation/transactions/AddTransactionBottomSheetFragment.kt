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
import com.google.android.material.textfield.TextInputLayout
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.data.Transaction
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sign

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

        val pickDateButton = view.findViewById<Button>(R.id.btn_pick_date)
        pickDateButton.setOnClickListener {
            showDatePickerDialog(view)
        }

        var chosenBank: Bank? = null

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            chosenBank = parent.getItemAtPosition(position) as Bank
        }

        val transactionNameEditText = view.findViewById<EditText>(R.id.transaction_name)
        val transactionValueEditText = view.findViewById<EditText>(R.id.transaction_value)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)
        var sign = ""
        chosenDateTV = view.findViewById<TextView>(R.id.tv_chosen_date)

        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        chosenDateTV.text = currentDate

        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            if(chosenBank != null && transactionNameEditText.text.isNotBlank() && transactionValueEditText.text.isNotEmpty() && chosenDateTV.text.toString() != "date"){
                val transactionName = transactionNameEditText.text.toString()
                val transactionValue = transactionValueEditText.text.toString().toDouble()
                val radioId = radioGroup.checkedRadioButtonId

                if(radioId == R.id.radio_button_plus){
                    sign = "+"
                }else if (radioId == R.id.radio_button_minus){
                    sign = "-"
                }

                if(sign == "-" && transactionValue > chosenBank!!.value){
                    Toast.makeText(requireContext(), "Value is exceed amount", Toast.LENGTH_SHORT).show()
                }else if (transactionValue == 0.0) {
                    Toast.makeText(requireContext(), "0 is unallowed value", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addTransaction(Transaction(chosenDateTV.text.toString(), transactionName, chosenBank!!.name, chosenBank!!.img, transactionValue, sign))
                    dismiss()
                    findNavController().navigate(R.id.action_addTransactionBottomSheetFragment_to_transactionsFragment)
                }

            } else {
                Toast.makeText(requireContext(), "Enter all fields", Toast.LENGTH_SHORT).show()
            }
        }



    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val currentDateString = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(c.time)
        chosenDateTV.text = currentDateString
    }

}