package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
import kz.home.walletapp.presentation.login.EMAIL_KEY
import kz.home.walletapp.presentation.login.PASSWORD_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AccountsFragment : Fragment(R.layout.fragment_accounts) {

    private lateinit var recyclerViewAdapter: BankAdapter
    private lateinit var viewPagerAdapter: AccountsViewPagerAdapter
    private val viewModel: AccountsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        setupData(viewPager, tabLayout, recyclerView)

        val addWalletButton = view.findViewById<ConstraintLayout>(R.id.addWalletButton)
        addWalletButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_walletTypeFragment)
        }

        viewModel.accounts.observe(viewLifecycleOwner){
            recyclerViewAdapter.submitList(it.toMutableList())
        }

        viewModel.sums.observe(viewLifecycleOwner){
            viewPagerAdapter.setSum(it)
        }
    }

    private fun setupData(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        recyclerView: RecyclerView?
    ) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val email = preferences?.getString(EMAIL_KEY, "")
        val password = preferences?.getString(PASSWORD_KEY, "")
        if (email != "" && email != null && password != null && password != "") {
            viewModel.initialize(email, password)
        }

        setupViewPager(viewPager, tabLayout)
        setupRecyclerView(recyclerView)
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

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(0, true)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Banks"
                2 -> tab.text = "Crypto"
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when(tab.position){
                        0 -> viewModel.showAllAccounts()
                        1 -> viewModel.showOnlyBanks()
                        2 -> viewModel.showOnlyCrypto()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}