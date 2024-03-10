package sopt.motivoo.presentation.home.dialog

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomeCofirmBinding
import sopt.motivoo.presentation.home.HomeFragment.Companion.HOME_STATE_CONFIRM
import sopt.motivoo.presentation.home.HomeFragment.Companion.HOME_STATE_CONFIRM_RESULT_OK
import sopt.motivoo.presentation.home.viewmodel.HomeViewModel
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.px

@AndroidEntryPoint
class HomeConfirmDialogFragment :
    BindingDialogFragment<DialogHomeCofirmBinding>(R.layout.dialog_home_cofirm) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 0.91f, heightPercent = 0.48f)

        val safeArgs: HomeConfirmDialogFragmentArgs by navArgs()

        safeArgs.photoUri?.let { loadPhoto(it) }
        binding.btnConfirm.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadPhoto(uri: Uri) {
        binding.ivPhoto.load(uri) {
            scale(Scale.FILL)
            transformations(RoundedCornersTransformation(8.px))
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            HOME_STATE_CONFIRM,
            HOME_STATE_CONFIRM_RESULT_OK
        )
    }
}
