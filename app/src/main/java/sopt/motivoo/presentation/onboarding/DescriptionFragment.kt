package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentOnboardingDescriptionBinding
import sopt.motivoo.util.binding.BindingFragment

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
