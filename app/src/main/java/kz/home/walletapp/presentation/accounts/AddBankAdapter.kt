package kz.home.walletapp.presentation.accounts

import android.media.Image
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Bank

class AddBankAdapter(
    private val onBankClickListener: (bank: Bank) -> Unit
) :
    ListAdapter<Bank, AddBankAdapter.BankViewHolder>(AddBankDiffUtilCallback()) {

    inner class BankViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_accounts, parent, false)) {

        private val nameTextView = itemView.findViewById<TextView>(R.id.name_tv)
        private val valueTextView = itemView.findViewById<TextView>(R.id.value_tv)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        private val tvCurrencyAbbr = itemView.findViewById<TextView>(R.id.tv_currency_abbr)
        private val refreshButton = itemView.findViewById<ImageView>(R.id.refresh_button)
        private val plusButton = itemView.findViewById<ImageView>(R.id.plus_button)

        fun bind(item: Bank) {
            nameTextView.text = item.name
            valueTextView.text = "Kazakhstan"//item.value.toString()
            image.setImageResource(item.img)

            tvCurrencyAbbr.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
            plusButton.visibility = View.INVISIBLE

            itemView.setOnClickListener {
                onBankClickListener(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return BankViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class AddBankDiffUtilCallback : DiffUtil.ItemCallback<Bank>() {
    override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem == newItem
    }
}