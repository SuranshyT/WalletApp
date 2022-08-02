package kz.home.walletapp.presentation.transactions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.accounts.AccountsViewPagerAdapter
import kz.home.walletapp.presentation.accounts.BankAdapter
import kz.home.walletapp.presentation.accounts.BankItemTouch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var viewPagerAdapter: TransactionsViewPagerAdapter
    private lateinit var recyclerViewAdapter: TransactionsAdapter
    private val viewModel: TransactionsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        setupViewPager(viewPager, tabLayout)
        setupRecyclerView(recyclerView)

        val addButton = view.findViewById<ImageView>(R.id.addButton)
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment_to_addTransactionBottomSheetFragment)
        }

        recyclerViewAdapter.submitList(Data.transactions)
        viewPagerAdapter.setSum(Data.transactionsSum)

        /*viewModel.transactions.observe(viewLifecycleOwner){
            recyclerViewAdapter.submitList(it)
        }*/

        viewModel.sums.observe(viewLifecycleOwner){
            viewPagerAdapter.setSum(it)
        }
    }

    private fun setupViewPager(
        viewPager: ViewPager2,
        tabLayout: TabLayout
    ) {
        viewPagerAdapter = TransactionsViewPagerAdapter()
        viewPagerAdapter.setSum(Data.transactionsSum)

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(0, true)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = "7 days"
                1 -> tab.text = "1 month"
                2 -> tab.text = "3 months"
            }
        }.attach()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?) {
        recyclerViewAdapter = TransactionsAdapter(requireActivity(), viewModel::deleteTransaction)
        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemTouchHelper = ItemTouchHelper(TransactionItemTouch(recyclerViewAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }
    }
}