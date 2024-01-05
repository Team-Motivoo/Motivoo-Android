package sopt.motivoo.presentation.intro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentPermissionBinding
import sopt.motivoo.util.binding.BindingFragment

class PermissionFragment :
    BindingFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    private val deniedPermissions = mutableSetOf<String>()
    private var requiredPermissions = mutableSetOf<String>()

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { (permission, granted) ->
                if (granted) {
                    requiredPermissions.remove(permission)
                } else if (shouldShowRequestPermissionRationale(permission)) {
                    deniedPermissions.add(permission)
                    requiredPermissions.remove(permission)
                }
            }
            if (requiredPermissions.isEmpty()) {
                navigateToLogin()
            }
            return@registerForActivityResult
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRequiredPermissions()
        if (checkPermissionsStatus() || isAllPermissionsGranted()) {
            navigateToLogin()
        } else {
            getPermission()
        }
    }

    private fun initRequiredPermissions() {
        val isAndroidTOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val isAndroidQOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        val storagePermission = if (isAndroidTOrAbove) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        val permissions = mutableListOf(
            Manifest.permission.CAMERA,
            storagePermission
        )

        if (isAndroidQOrAbove) {
            permissions.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }

        for (permission in permissions) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                requiredPermissions.add(permission)
            }
        }
    }

    private fun checkPermissionsStatus(): Boolean {
        for (permission in requiredPermissions) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                return false
            }
        }
        return true
    }

    private fun isAllPermissionsGranted(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getPermission() {
        binding.btnPermissionDone.setOnClickListener { checkAndRequestPermissions() }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = requiredPermissions.filterNot { deniedPermissions.contains(it) }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissions(permissionsToRequest.toTypedArray())
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        requestMultiplePermissionsLauncher.launch(permissions)
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_permissionFragment_to_loginFragment)
    }
}
