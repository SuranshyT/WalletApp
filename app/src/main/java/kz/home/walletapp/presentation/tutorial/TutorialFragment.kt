package kz.home.walletapp.presentation.tutorial

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.home.walletapp.R
import kz.home.walletapp.data.Data

class TutorialFragment : Fragment(R.layout.fragment_tutorial) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skipTutorial2TextView = view.findViewById<TextView>(R.id.tv_skip_tutorial2)
        skipTutorial2TextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_tutorialFragment_to_loginFragment)
        }

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        val adapter = TutorialAdapter()
        adapter.setTutorials(Data.tutorials)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(0, true)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //tab.text = adapter.getTitle(position)
        }.attach()



    }
}