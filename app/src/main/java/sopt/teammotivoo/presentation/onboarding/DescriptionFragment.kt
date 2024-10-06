package sopt.teammotivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.FragmentOnboardingDescriptionBinding
import sopt.teammotivoo.util.binding.BindingFragment

class DescriptionFragment :
    BindingFragment<FragmentOnboardingDescriptionBinding>(R.layout.fragment_onboarding_description) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDescription()
    }

    private fun showDescription() {
        lifecycleScope.launch {
            delay(4000L)
            findNavController().navigate(R.id.action_descriptionFragment_to_nickNameFragment)
        }
    }
}
