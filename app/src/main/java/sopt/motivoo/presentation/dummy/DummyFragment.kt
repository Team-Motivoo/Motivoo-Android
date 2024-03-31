package sopt.motivoo.presentation.dummy

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentDummyBinding
import sopt.motivoo.util.binding.BindingFragment

@AndroidEntryPoint
class DummyFragment : BindingFragment<FragmentDummyBinding>(R.layout.fragment_dummy) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
