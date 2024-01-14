package sopt.motivoo.presentation.home

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import sopt.motivoo.R
import sopt.motivoo.databinding.BottomSheetHomeBinding

class HomeBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHomeBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val isCameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false) {
                    permissionGranted = false
                }
            }
            if (!permissionGranted) {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_SHORT)
                    .show()
            } else {
                start()
            }
        }

    private val takePictureResult =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { photoBitmap ->
            if (photoBitmap != null) {
                val action =
                    HomeBottomSheetFragmentDirections.actionHomeBottomSheetFragmentToHomeConfirmDialogFragment(
                        photoBitmap
                    )
                findNavController().navigate(action)
            }
        }

    private val pickMedia =
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
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSize()

        binding.clTakePhoto.setOnClickListener {
            if (allPermissionGranted()) {
                start()
            } else {
                isCameraPermissionResult.launch(REQUIRED_PERMISSIONS)
            }
        }

        binding.clSelectAlbum.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun start() {
        takePictureResult.launch(null)
    }

    private fun setLayoutSize() {
        context?.resources?.displayMetrics?.let { metrics ->
            binding.root.layoutParams?.run {
                height = (metrics.heightPixels * heightPercent).toInt()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val heightPercent = 0.45f

        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
