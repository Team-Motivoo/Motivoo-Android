package sopt.teammotivoo.presentation.home.dialog

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sopt.teammotivoo.R
import sopt.teammotivoo.databinding.DialogHomePhotoBinding
import sopt.teammotivoo.presentation.home.HomePictureState
import sopt.teammotivoo.presentation.home.viewmodel.HomeViewModel
import sopt.teammotivoo.util.BitmapUtil
import sopt.teammotivoo.util.Constants.S3_BUCKET_NAME
import sopt.teammotivoo.util.binding.BindingDialogFragment
import sopt.teammotivoo.util.extension.showToast

@AndroidEntryPoint
class HomePhotoDialogFragment :
    BindingDialogFragment<DialogHomePhotoBinding>(R.layout.dialog_home_photo) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var photoUri: Uri
    private lateinit var bitmapUtil: BitmapUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 1f, heightPercent = 1f)
        bitmapUtil = BitmapUtil(requireContext())

        val safeArgs: HomePhotoDialogFragmentArgs by navArgs()
        photoUri = safeArgs.photoUri

        binding.ivPhoto.load(photoUri)

        binding.tvConfirm.setOnClickListener {
            bitmapUtil.createUriToBitmap(photoUri, size = 2)?.let { bitmap ->
                binding.pvLoading.visibility = View.VISIBLE
                viewModel.getMissionImage(
                    S3_BUCKET_NAME, bitmap
                )
            } ?: requireContext().showToast("createUriToBitmap is null")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homePictureState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest { homeBottomSheetState ->
                when (homeBottomSheetState) {
                    HomePictureState.Idle -> Unit

                    is HomePictureState.SuccessMissionData -> {
                        viewModel.uploadPhoto(
                            homeBottomSheetState.imgPresignedUrl,
                            homeBottomSheetState.fileName,
                            homeBottomSheetState.pictureBitmap
                        )
                    }

                    is HomePictureState.UploadFile -> {
                        viewModel.patchMissionImage(
                            homeBottomSheetState.fileName,
                            homeBottomSheetState.pictureBitmap
                        )
                    }

                    is HomePictureState.SuccessImageUpload -> {
                        binding.pvLoading.visibility = View.GONE
                        val action =
                            HomePhotoDialogFragmentDirections.actionHomePhotoDialogFragmentToHomeConfirmDialogFragment(
                                photoUri
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}
