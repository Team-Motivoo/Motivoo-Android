package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.activity.OnBackPressedCallback
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

    private val termsOfUseViewModel by viewModels<TermsOfUseViewModel>()

    @Inject
    lateinit var motivooStorage: MotivooStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.termsOfViewModel = termsOfUseViewModel

        overrideOnBackPressed()
        makeBackButtonGone()
        clickDoneButton()
        setupCheckAllListener()
        goToTermsWebFragment()
        setupTermsCheckListeners()
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }

    private fun setupTermsCheckListeners() {
        binding.clTermsOfUseAll.setOnSingleClickListener {
            toggleCheckBox(binding.cbCheckAll)
            termsOfUseViewModel.setAllTermsChecked(binding.cbCheckAll.isChecked)
        }

        binding.clTermsOfUse.setOnSingleClickListener {
            toggleCheckBox(binding.cbTermsOfUseAccess)
        }

        binding.clTermsGetInfo.setOnSingleClickListener {
            toggleCheckBox(binding.cbTermsGetInfoAccess)
        }

        binding.clTermsUseInfo.setOnSingleClickListener {
            toggleCheckBox(binding.cbTermsUseInfoAccess)
        }
    }

    private fun toggleCheckBox(checkBox: CheckBox) {
        checkBox.isChecked = !checkBox.isChecked
    }

    private fun goToTermsWebFragment() {
        binding.tvTermsOfUseLink.setOnSingleClickListener {
            val action = TermsOfUseFragmentDirections
                .actionTermsOfUseFragmentToWebViewFragment(getString(R.string.terms_of_use_lnk))
            findNavController().navigate(action)
        }

        binding.tvTermsGetInfoLink.setOnSingleClickListener {
            val action = TermsOfUseFragmentDirections
                .actionTermsOfUseFragmentToWebViewFragment(getString(R.string.terms_of_get_info_lnk))
            findNavController().navigate(action)
        }

        binding.tvTermsUseInfoLink.setOnSingleClickListener {
            val action = TermsOfUseFragmentDirections
                .actionTermsOfUseFragmentToWebViewFragment(getString(R.string.terms_of_use_info_lnk))
            findNavController().navigate(action)
        }
    }

    private fun clickDoneButton() {
        binding.btnTermsOfUseDone.setOnSingleClickListener {
            motivooStorage.isFinishedTermsOfUse = true
            findNavController().navigate(R.id.action_termsOfUseFragment_to_AgeQuestionFragment)
        }
    }

    private fun makeBackButtonGone() {
        binding.includeTermsToolbar.tvToolbarBack.visibility = View.INVISIBLE
    }

    private fun setupCheckAllListener() {
        binding.cbCheckAll.setOnSingleClickListener {
            val isChecked = binding.cbCheckAll.isChecked
            termsOfUseViewModel.setAllTermsChecked(isChecked)
        }
    }
}
