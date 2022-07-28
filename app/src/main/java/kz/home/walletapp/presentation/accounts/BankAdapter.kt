package kz.home.walletapp.presentation.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Bank
import java.text.DecimalFormat

class BankAdapter(private val deleteItem: (Bank) -> Unit) :
    ListAdapter<Bank, BankAdapter.BankViewHolder>(BankDiffUtilCallback()), BankAdapterItemTouchHelper {

    inner class BankViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_accounts, parent, false)) {

        private val nameTextView = itemView.findViewById<TextView>(R.id.name_tv)
        private val valueTextView = itemView.findViewById<TextView>(R.id.value_tv)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: Bank) {
            nameTextView.text = item.name
            valueTextView.text = item.value.toString()
            image.setImageResource(item.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return BankViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun onDismiss(position: Int) {
        val bank = getItem(position)
        deleteItem(bank)
        notifyItemRemoved(position)
    }
}

class BankDiffUtilCallback : DiffUtil.ItemCallback<Bank>() {
    override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem == newItem
    }
}