package kz.home.walletapp.presentation.accounts

import androidx.recyclerview.widget.DiffUtil
import kz.home.walletapp.domain.model.AccountsSum

class SumDiffCallBack : DiffUtil.Callback() {
    private var oldList = emptyList<AccountsSum>()
    private var newList = emptyList<AccountsSum>()

    fun setItems(oldList: List<AccountsSum>, newList: List<AccountsSum>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].sum == newList[newItemPosition].sum

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        val fields = mutableSetOf<SumPayload>()
        val oldItem = oldList[oldPosition]
        val newItem = newList[newPosition]

        if (oldItem.sum != newItem.sum) fields.add(SumPayload.SUM)
        if (oldItem.type != newItem.type) fields.add(SumPayload.TYPE)

        return when {
            fields.isNotEmpty() -> fields
            else -> super.getChangePayload(oldPosition, newPosition) // null
        }
    }
}

enum class SumPayload {
    TYPE,
    SUM
}