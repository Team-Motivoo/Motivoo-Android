package sopt.motivoo.presentation.home.dialog

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
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomePhotoBinding
import sopt.motivoo.presentation.home.HomePictureState
import sopt.motivoo.presentation.home.viewmodel.HomeViewModel
import sopt.motivoo.util.Constants.S3_BUCKET_NAME
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.createUriToBitmap

@AndroidEntryPoint
class HomePhotoDialogFragment :
    BindingDialogFragment<DialogHomePhotoBinding>(R.layout.dialog_home_photo) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var photoUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSizeRatio(widthPercent = 1f, heightPercent = 1f)

        val safeArgs: HomePhotoDialogFragmentArgs by navArgs()
        photoUri = safeArgs.photoUri

        binding.ivPhoto.load(photoUri)

        binding.tvConfirm.setOnClickListener {
            binding.pvLoading.visibility = View.VISIBLE
            viewModel.getMissionImage(
                S3_BUCKET_NAME,
                requireContext().createUriToBitmap(safeArgs.photoUri)
            )
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
