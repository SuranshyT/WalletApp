package kz.home.walletapp.presentation.accounts

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import kz.home.walletapp.R

class XFragment : Fragment(R.layout.fragment_accounts_slide) {

    private var categoryId = 0.0
    private var categoryName = ""

    companion object {

        private const val EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID"
        private const val EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME"

        fun newInstance(categoryId: Double, categoryName: String): XFragment {
            val fragment = XFragment()
            val bundle = Bundle()
            bundle.putDouble(EXTRA_CATEGORY_ID, categoryId)
            bundle.putString(EXTRA_CATEGORY_NAME, categoryName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = arguments?.getDouble(EXTRA_CATEGORY_ID, categoryId) ?: categoryId
        categoryName = arguments?.getString(EXTRA_CATEGORY_NAME, categoryName) ?: categoryName

        val sumTV = view.findViewById<TextView>(R.id.sum_tv)
        sumTV.text = categoryId.toString()
    }
}