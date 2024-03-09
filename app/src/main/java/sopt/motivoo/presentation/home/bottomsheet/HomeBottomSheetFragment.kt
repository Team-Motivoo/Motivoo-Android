package sopt.motivoo.presentation.home.bottomsheet

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.BottomSheetHomeBinding
import sopt.motivoo.presentation.home.HomePictureState
import sopt.motivoo.presentation.home.viewmodel.HomeViewModel
import sopt.motivoo.util.Constants.S3_BUCKET_NAME
import sopt.motivoo.util.UriManager
import sopt.motivoo.util.extension.createUriToBitmap
import java.text.SimpleDateFormat

@AndroidEntryPoint
class HomeBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHomeBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val viewModel: HomeViewModel by viewModels()

    var pictureUri: Uri? = null

    private val isCameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_CAMERA_PERMISSIONS && it.value == false) {
                    permissionGranted = false
                }
            }
            if (!permissionGranted) {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_SHORT)
                    .show()
            } else {
                takePhoto()
            }
        }

    private val takePictureResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                binding.pvLoading.visibility = View.VISIBLE
                pictureUri?.let {
                    viewModel.getMissionImage(S3_BUCKET_NAME, requireContext().createUriToBitmap(it))
                }
            }
        }

    private val pickMediaResult =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { photoUri ->
            if (photoUri != null) {
                val action =
                    HomeBottomSheetFragmentDirections.actionHomeBottomSheetFragmentToHomePhotoDialogFragment(
                        photoUri
                    )
                findNavController().navigate(action)
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.RoundedCornerBottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSize()
        collectHomePictureState()
        onClickTakePicture()
        onClickAlbum()
    }

    private fun onClickAlbum() {
        binding.clSelectAlbum.setOnClickListener {
            pickMediaResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun onClickTakePicture() {
        binding.clTakePhoto.setOnClickListener {
            if (allCameraPermissionGranted()) {
                takePhoto()
            } else {
                isCameraPermissionResult.launch(REQUIRED_CAMERA_PERMISSIONS)
            }
        }
    }

    private fun collectHomePictureState() {
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
                            HomeBottomSheetFragmentDirections.actionHomeBottomSheetFragmentToHomeConfirmDialogFragment(
                                photoUri = pictureUri
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun takePhoto() {
        UriManager(requireContext()).createImageUri()?.let {
            pictureUri = it
            takePictureResult.launch(it)
        }
    }

    private fun setLayoutSize() {
        context?.resources?.displayMetrics?.let { metrics ->
            binding.root.layoutParams?.run {
                height = (metrics.heightPixels * HEIGHT_PERCENT).toInt()
            }
        }
    }

    private fun allCameraPermissionGranted() = REQUIRED_CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val HEIGHT_PERCENT = 0.45f

        private val REQUIRED_CAMERA_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
