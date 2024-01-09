package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.util.binding.BindingFragment

class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMypageMyInfo.setOnClickListener{
            navigateToMyInfo()
        }
    }

    private fun navigateToMyInfo() {
        findNavController().navigate(R.id.action_myPageFragment_to_myInfoFragment)
    }
}
