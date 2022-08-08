package kz.home.walletapp.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.home.walletapp.R
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.EMAIL_KEY
import kz.home.walletapp.presentation.login.PASSWORD_KEY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val RANK_1_SUM = 100000
const val RANK_2_SUM = 250000
const val RANK_3_SUM = 500000
const val RANK_4_SUM = 1000000

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val accountsViewModel: AccountsViewModel by sharedViewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val email = preferences?.getString(EMAIL_KEY, "")
        val password = preferences?.getString(PASSWORD_KEY, "")

        val userNameTv = view.findViewById<TextView>(R.id.user_name_tv)
        val emailTv = view.findViewById<TextView>(R.id.email_tv)
        val rankTv = view.findViewById<TextView>(R.id.rank_tv)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val imageView = view.findViewById<ImageView>(R.id.profile_image)

        val startRank = view.findViewById<TextView>(R.id.tv_current_rank)
        val startRankSum = view.findViewById<TextView>(R.id.tv_current_sum)
        val nextRank = view.findViewById<TextView>(R.id.tv_next_rank)
        val nextRankSum = view.findViewById<TextView>(R.id.tv_next_sum)

        lifecycleScope.launch {
            if (email != null && password != null) {
                profileViewModel.loginUser(email, password).collect {
                    if (it != null) {
                        withContext(Dispatchers.Main) {
                            userNameTv.text = getString(R.string.full_name, it.firstName, it.lastName)
                            emailTv.text = it.email
                        }
                    }
                }
            }
        }

        accountsViewModel.sums.observe(viewLifecycleOwner){
            val sum = it[0].sum
            if(sum < RANK_1_SUM){
                progressBar.setProgress((sum/RANK_1_SUM*100).toInt())
                startRank.text = "Intern"
                nextRank.text  = "Junior"
                startRankSum.text = "0"
                nextRankSum.text = RANK_1_SUM.toString()
                rankTv.text = "Intern"
                imageView.setImageResource(R.drawable.rank_1)
            } else if(sum < RANK_2_SUM){
                progressBar.setProgress(((sum - RANK_1_SUM)/(RANK_2_SUM-RANK_1_SUM)*100).toInt())
                startRank.text = "Junior"
                nextRank.text  = "Middle"
                startRankSum.text = RANK_1_SUM.toString()
                nextRankSum.text = RANK_2_SUM.toString()
                rankTv.text = "Junior"
                imageView.setImageResource(R.drawable.rank_2)
            } else if(sum < RANK_3_SUM){
                progressBar.setProgress(((sum - RANK_2_SUM)/(RANK_3_SUM-RANK_2_SUM)*100).toInt())
                startRank.text = "Middle"
                nextRank.text  = "Senior"
                startRankSum.text = RANK_2_SUM.toString()
                nextRankSum.text = RANK_3_SUM.toString()
                rankTv.text = "Middle"
                imageView.setImageResource(R.drawable.rank_3)
            } else if(sum < RANK_4_SUM){
                progressBar.setProgress(((sum - RANK_3_SUM)/(RANK_4_SUM-RANK_3_SUM)*100).toInt())
                startRank.text = "Senior"
                nextRank.text  = "Team Lead"
                startRankSum.text = RANK_3_SUM.toString()
                nextRankSum.text = RANK_4_SUM.toString()
                rankTv.text = "Senior"
                imageView.setImageResource(R.drawable.rank_4)
            } else {
                progressBar.setProgress(100)
                startRank.text = "Team Lead"
                nextRank.text  = "CEO"
                startRankSum.text = RANK_4_SUM.toString()
                nextRankSum.text = "∞"
                rankTv.text = "Team Lead"
                imageView.setImageResource(R.drawable.rank_5)
            }
        }


        view.findViewById<Button>(R.id.btn_log_out).setOnClickListener {
            preferences.edit().clear().apply()
            if(GoogleSignIn.getLastSignedInAccount(requireContext()) != null){
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
                mGoogleSignInClient.signOut()
            }
            accountsViewModel.logOut()
        }
    }
}