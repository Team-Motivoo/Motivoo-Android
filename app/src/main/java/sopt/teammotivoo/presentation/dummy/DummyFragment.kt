package sopt.teammotivoo.presentation.dummy

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.FragmentDummyBinding
import sopt.teammotivoo.util.binding.BindingFragment

@AndroidEntryPoint
class DummyFragment : BindingFragment<FragmentDummyBinding>(R.layout.fragment_dummy) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
