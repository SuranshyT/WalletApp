package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.home.walletapp.R
import kz.home.walletapp.data.Data
import kz.home.walletapp.domain.model.Bank

class AccountsFragment : Fragment(R.layout.fragment_accounts), ChooseBankBottomSheetFragment.BankBottomSheetListener {

    lateinit var recyclerViewAdapter: BankAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if(acct!=null){
            view.findViewById<TextView>(R.id.textView2).text = acct.displayName
        }*/

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        setupViewPager(viewPager, tabLayout)

        setupRecyclerView(recyclerView)

        val addWalletButton = view.findViewById<ConstraintLayout>(R.id.addWalletButton)

        addWalletButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_walletTypeFragment)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?) {
        recyclerViewAdapter = BankAdapter()
        val recyclerViewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = recyclerViewManager
        }

        recyclerViewAdapter.submitList(Data.bankList)
    }

    private fun setupViewPager(
        viewPager: ViewPager2,
        tabLayout: TabLayout
    ) {
        val viewPagerAdapter = AccountsViewPagerAdapter()
        viewPagerAdapter.setSum(Data.sums)
        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(0, true)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Banks"
                else -> tab.text = "Crypto"
            }
        }.attach()
    }

    override fun addAccountClicked(bank: Bank) {
        recyclerViewAdapter.submitList(Data.bankList + bank)
    }
}