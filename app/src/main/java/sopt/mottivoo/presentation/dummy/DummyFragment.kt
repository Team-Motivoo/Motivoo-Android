package sopt.mottivoo.presentation.dummy

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import sopt.mottivoo.R
import sopt.mottivoo.databinding.FragmentDummyBinding
import sopt.mottivoo.util.binding.BindingFragment

@AndroidEntryPoint
class DummyFragment : BindingFragment<FragmentDummyBinding>(R.layout.fragment_dummy) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
