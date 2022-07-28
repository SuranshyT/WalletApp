package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.domain.model.Sum
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AccountsFragment : Fragment(R.layout.fragment_accounts) {

    private lateinit var recyclerViewAdapter: BankAdapter
    private lateinit var viewPagerAdapter: AccountsViewPagerAdapter
    //private lateinit var adapter: AnotherAdapter
    private val viewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        setupViewPager(viewPager, tabLayout)
        setupRecyclerView(recyclerView)

        val addWalletButton = view.findViewById<ConstraintLayout>(R.id.addWalletButton)

        addWalletButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_walletTypeFragment)
        }

        viewModel.accounts.observe(viewLifecycleOwner){
            recyclerViewAdapter.submitList(it)
            viewModel.saveAccounts()
        }

        viewModel.sums.observe(viewLifecycleOwner){
            viewPagerAdapter.setSum(it)
            //adapter.setCategories(it)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?) {
        recyclerViewAdapter = BankAdapter(viewModel::deleteAccount)
        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemTouchHelper = ItemTouchHelper(BankItemTouch(recyclerViewAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }
    }

    private fun setupViewPager(
        viewPager: ViewPager2,
        tabLayout: TabLayout
    ) {
        viewPagerAdapter = AccountsViewPagerAdapter()
        viewPagerAdapter.setSum(Data.sums)

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(0, true)

        //if (viewModel.getCount() == 1) {
            TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
                when (position) {
                    0 -> tab.text = "All"
                    1 -> tab.text = "Banks"
                    2 -> tab.text = "Crypto"
                }
            }.attach()

        val labels = listOf("All", "Banks", "Crypto")

        /*TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()*/
        //}
    }

    /*private fun setupViewPager(
        viewPager: ViewPager2,
        tabLayout: TabLayout
    ) {
        adapter = AnotherAdapter(this)
        adapter.setCategories(Data.sums)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter
        viewPager.setCurrentItem(1, false)

        val labels = listOf("All", "Banks", "Crypto")

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Banks"
                2 -> tab.text = "Crypto"
            }
        }.attach()

        *//*TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()*//*
    }*/
}