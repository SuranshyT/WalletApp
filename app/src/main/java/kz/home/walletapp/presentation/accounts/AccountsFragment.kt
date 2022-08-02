package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.util.Log
import android.view.View
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

        /*val e = arguments?.getString(EMAIL_KEY)
        val p = arguments?.getString(PASSWORD_KEY)

        if (e != null && p != null) {
            viewModel.initialize(e, p)
        }*/

        setupViewPager(viewPager, tabLayout)
        setupRecyclerView(recyclerView)

        val addWalletButton = view.findViewById<ConstraintLayout>(R.id.addWalletButton)
        addWalletButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountsFragment_to_walletTypeFragment)
        }

        viewModel.accounts.observe(viewLifecycleOwner){
            recyclerViewAdapter.submitList(it)
            Log.e("", "$it !!!!!!!!!!")
            //viewModel.saveAccounts()
        }

        viewModel.sums.observe(viewLifecycleOwner){
            viewPagerAdapter.setSum(it)
            //adapter.setCategories(it)
        }


        /*view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (keyCode == KeyEvent.KEYCODE_BACK) {
                    findNavController().popBackStack()
                    viewModel.clear()
                    true
                } else false
            }
        })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //viewModel.clear()
        Log.e("", "OnDestroyView")
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
        viewPagerAdapter.setSum(Data.accountsSums)

        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(0, true)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Banks"
                2 -> tab.text = "Crypto"
            }
        }.attach()
    }
}