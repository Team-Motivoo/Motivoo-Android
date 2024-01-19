package sopt.motivoo.presentation.home

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomeCofirmBinding
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.px

class HomeConfirmDialogFragment :
    BindingDialogFragment<DialogHomeCofirmBinding>(R.layout.dialog_home_cofirm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 0.91f, heightPercent = 0.48f)

        val safeArgs: HomeConfirmDialogFragmentArgs by navArgs()

        safeArgs.photoUri?.let { loadPhoto(it) }
        safeArgs.photoBitmap?.let { loadPhoto(it) }
        binding.btnConfirm.setOnClickListener { dismiss() }

    }

    private fun loadPhoto(bitmap: Bitmap) {
        binding.ivPhoto.load(bitmap) {
            scale(Scale.FILL)
            transformations(RoundedCornersTransformation(8.px))
        }
    }

    private fun loadPhoto(uri: Uri) {
        binding.ivPhoto.load(uri) {
            scale(Scale.FILL)
            transformations(RoundedCornersTransformation(8.px))
        }
    }
}
