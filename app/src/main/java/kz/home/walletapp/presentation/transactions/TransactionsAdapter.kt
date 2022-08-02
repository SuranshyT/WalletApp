package kz.home.walletapp.presentation.transactions

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Transaction

class TransactionsAdapter(val context: Context, private val deleteItem: (Transaction) -> Unit) :
    ListAdapter<Transaction, TransactionsAdapter.TransactionsViewHolder>(TransactionsDiffUtilCallback()),
    TransactionAdapterItemTouchHelper {

    inner class TransactionsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(kz.home.walletapp.R.layout.item_transactions, parent, false)) {

        private val dateTextView = itemView.findViewById<TextView>(kz.home.walletapp.R.id.date)
        private val nameTextView = itemView.findViewById<TextView>(kz.home.walletapp.R.id.name_tv)
        private val bankTextView = itemView.findViewById<TextView>(kz.home.walletapp.R.id.bank_tv)
        private val valueTextView = itemView.findViewById<TextView>(kz.home.walletapp.R.id.value_tv)
        private val image = itemView.findViewById<ImageView>(kz.home.walletapp.R.id.image)
        private val signTextView = itemView.findViewById<TextView>(kz.home.walletapp.R.id.sign_tv)

        fun bind(item: Transaction) {
            dateTextView.text = item.date
            nameTextView.text = item.name
            bankTextView.text = item.bank
            valueTextView.text = item.value.toString()
            image.setImageResource(item.img)
            signTextView.text = item.type
            if (item.type == "+") {
                valueTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
                signTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
            } else {
                valueTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                signTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TransactionsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun onDismiss(position: Int) {
        val transaction = getItem(position)
        deleteItem(transaction)
        notifyItemRemoved(position)
    }
}

class TransactionsDiffUtilCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}