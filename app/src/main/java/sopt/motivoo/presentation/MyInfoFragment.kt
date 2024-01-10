package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageMyinfoBinding
import sopt.motivoo.util.binding.BindingFragment


class MyInfoFragment :
    BindingFragment<FragmentMypageMyinfoBinding>(R.layout.fragment_mypage_myinfo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clMyinfoOut.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myServiceOut)
        }
    }
}
