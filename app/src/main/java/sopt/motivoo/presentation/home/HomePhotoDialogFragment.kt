package sopt.motivoo.presentation.home

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomePhotoBinding
import sopt.motivoo.util.binding.BindingDialogFragment

class HomePhotoDialogFragment :
    BindingDialogFragment<DialogHomePhotoBinding>(R.layout.dialog_home_photo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 1f, heightPercent = 1f)

        val safeArgs: HomePhotoDialogFragmentArgs by navArgs()

        // TODO:: 선택된 앨범 사진 불러오기
        binding.ivPhoto.load(safeArgs.photoUri)

        binding.tvConfirm.setOnClickListener {
            createPhotoBitmap(safeArgs.photoUri)
            val action =
                HomePhotoDialogFragmentDirections.actionHomePhotoDialogFragmentToHomeConfirmDialogFragment(
                    createPhotoBitmap(safeArgs.photoUri)
                )
            findNavController().navigate(action)
        }
    }

    private fun createPhotoBitmap(photoUri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source =
                ImageDecoder.createSource(requireContext().contentResolver, photoUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(
                requireContext().contentResolver,
                photoUri
            )
        }
}
