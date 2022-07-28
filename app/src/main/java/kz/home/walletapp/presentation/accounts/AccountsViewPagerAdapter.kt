package kz.home.walletapp.presentation.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.domain.model.Sum

class AccountsViewPagerAdapter : RecyclerView.Adapter<AccountsViewPagerViewHolder>() {
    private val sumList = mutableListOf<Sum>()
    private val diffCallback = SumDiffCallBack()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewPagerViewHolder =
        AccountsViewPagerViewHolder(parent)

    override fun onBindViewHolder(holder: AccountsViewPagerViewHolder, position: Int) {
        holder.bind(sumList[position])
    }

    override fun onBindViewHolder(
        holder: AccountsViewPagerViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bind(sumList[position], payloads.first() as? Set<*>)
        }
    }

    override fun getItemCount(): Int = sumList.size

    /*fun setSum(list: List<Sum>){
        sumList.clear()
        sumList.addAll(list)
        notifyDataSetChanged()
    }*/

    fun setSum(list: List<Sum>) {
        diffCallback.setItems(sumList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback, false)
        sumList.clear()
        sumList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}

class AccountsViewPagerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val sumTextView = itemView.findViewById<TextView>(R.id.sum_tv)

    constructor(parent: ViewGroup)
            : this(LayoutInflater.from(parent.context).inflate(R.layout.fragment_accounts_slide, parent, false))

    fun bind(item: Sum) {
        sumTextView.text = item.sum.toString()
    }

    fun bind(item: Sum, fields: Set<*>?) {
        fields?.forEach {
            when (it) {
                SumPayload.SUM -> sumTextView.text = item.sum.toString()
            }
        }
        sumTextView.text = item.sum.toString()
    }
}