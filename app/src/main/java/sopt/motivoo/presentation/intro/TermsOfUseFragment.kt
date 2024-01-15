package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentTermsOfUseBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import javax.inject.Inject

@AndroidEntryPoint
class TermsOfUseFragment :
    BindingFragment<FragmentTermsOfUseBinding>(R.layout.fragment_terms_of_use) {

    @Inject
    lateinit var motivooStorage: MotivooStorage
    private val termsOfUseViewModel by viewModels<TermsOfUseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.termsOfViewModel = termsOfUseViewModel

        goToBack()
        clickDoneButton()
        setupCheckAllListener()
    }

    private fun clickDoneButton() {
        binding.btnTermsOfUseDone.setOnSingleClickListener {
            findNavController().navigate(R.id.action_termsOfUseFragment_to_startMotivooFragment)
        }
    }

    private fun goToBack() {
        binding.includeTermsToolbar.tvToolbarBack.setOnSingleClickListener {
            motivooStorage.clear()
            findNavController().popBackStack()
        }
    }

    private fun setupCheckAllListener() {
        binding.cbCheckAll.setOnSingleClickListener {
            val isChecked = binding.cbCheckAll.isChecked
            termsOfUseViewModel.setAllTermsChecked(isChecked)
        }
    }
}