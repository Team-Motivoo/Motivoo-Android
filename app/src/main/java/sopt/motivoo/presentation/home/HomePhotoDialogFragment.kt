package sopt.motivoo.presentation.home

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomePhotoBinding
import sopt.motivoo.util.Constants.S3_BUCKET_NAME
import sopt.motivoo.util.binding.BindingDialogFragment

class HomePhotoDialogFragment :
    BindingDialogFragment<DialogHomePhotoBinding>(R.layout.dialog_home_photo) {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var photoUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 1f, heightPercent = 1f)

        val safeArgs: HomePhotoDialogFragmentArgs by navArgs()
        photoUri = safeArgs.photoUri

        binding.ivPhoto.load(photoUri)

        binding.tvConfirm.setOnClickListener {
            viewModel.setImageBitmap(createPhotoBitmap(safeArgs.photoUri))
            viewModel.patchMissionImage(S3_BUCKET_NAME)
        }

        observeData()
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

    private fun observeData() {
        viewModel.imageData.observe(viewLifecycleOwner) {
            viewModel.imageBitmap.value?.let { imageBitmap ->
                viewModel.uploadPhoto(it.imgPresignedUrl, imageBitmap)
            }
        }
        viewModel.isUploadImage.observe(viewLifecycleOwner) {
            viewModel.imageBitmap.value?.let { imageBitmap ->
                val action =
                    HomePhotoDialogFragmentDirections.actionHomePhotoDialogFragmentToHomeConfirmDialogFragment(
                        null, photoUri
                    )
                findNavController().navigate(action)
            }
        }
    }
}
