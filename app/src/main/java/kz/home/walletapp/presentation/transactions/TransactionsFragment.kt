package kz.home.walletapp.presentation.transactions

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.EMAIL_KEY
import kz.home.walletapp.presentation.login.PASSWORD_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var viewPagerAdapter: TransactionsViewPagerAdapter
    private lateinit var recyclerViewAdapter: TransactionsAdapter
    private val viewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        setupViewPager(viewPager, tabLayout)
        setupRecyclerView(recyclerView)

        val addButton = view.findViewById<ImageView>(R.id.addButton)
        addButton.setOnClickListener {
            //viewModel.addTransaction(Data.transactions[2])
            findNavController().navigate(R.id.action_transactionsFragment_to_addTransactionBottomSheetFragment)
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val e = preferences?.getString(EMAIL_KEY, "")
        val p = preferences?.getString(PASSWORD_KEY, "")
        if (e != "" && e != null && p != null && p != "") {
            viewModel.initializeTransactions(e, p)
        }

        //recyclerViewAdapter.submitList(Data.transactions)
        viewPagerAdapter.setSum(Data.transactionsSum)

        viewModel.transactions.observe(viewLifecycleOwner){
            recyclerViewAdapter.submitList(it)
        }

        viewModel.transactionsSums.observe(viewLifecycleOwner){
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