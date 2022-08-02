package kz.home.walletapp.presentation.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.TransactionsSum

class TransactionsViewPagerAdapter : RecyclerView.Adapter<TransactionsViewPagerViewHolder>() {
    private val accountsSumList = mutableListOf<TransactionsSum>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewPagerViewHolder =
        TransactionsViewPagerViewHolder(parent)

    override fun onBindViewHolder(holder: TransactionsViewPagerViewHolder, position: Int) {
        holder.bind(accountsSumList[position])
    }

    override fun getItemCount(): Int = accountsSumList.size

    fun setSum(list: List<TransactionsSum>){
        accountsSumList.clear()
        accountsSumList.addAll(list)
        notifyDataSetChanged()
    }
}

class TransactionsViewPagerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val earnedTextView = itemView.findViewById<TextView>(R.id.earned)
    private val spentTextView = itemView.findViewById<TextView>(R.id.spent)

    constructor(parent: ViewGroup)
            : this(LayoutInflater.from(parent.context).inflate(R.layout.fragment_transactions_slide, parent, false))

    fun bind(item: TransactionsSum) {
        earnedTextView.text = item.earned.toString()
        spentTextView.text = item.spent.toString()
    }
}