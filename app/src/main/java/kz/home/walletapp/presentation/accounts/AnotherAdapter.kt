package kz.home.walletapp.presentation.accounts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.home.walletapp.domain.model.Sum

class AnotherAdapter(activity: Fragment)
    : FragmentStateAdapter(activity) {

    private var categoryFragmentHolders = listOf<CategoryHolder>()

    override fun createFragment(position: Int): Fragment =
        categoryFragmentHolders[position].fragment

    override fun containsItem(itemId: Long): Boolean =
        categoryFragmentHolders.firstOrNull { it.sums.sum.toLong() == itemId } != null

    override fun getItemId(position: Int): Long =
        categoryFragmentHolders[position].sums.sum.toLong()

    override fun getItemCount(): Int =
        categoryFragmentHolders.size

    fun setCategories(sums: List<Sum>) {
        if (sums != categoryFragmentHolders.map { it.sums }) {
            categoryFragmentHolders = sums.map {
                val fragment = XFragment.newInstance(it.sum, it.type)
                CategoryHolder(fragment, it)
            }
            notifyDataSetChanged()
        }
    }

    private data class CategoryHolder(val fragment: Fragment, val sums: Sum)
}