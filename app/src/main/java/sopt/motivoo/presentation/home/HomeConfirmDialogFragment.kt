package sopt.motivoo.presentation.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import sopt.motivoo.R
import sopt.motivoo.databinding.DialogHomeCofirmBinding
import sopt.motivoo.util.binding.BindingDialogFragment
import sopt.motivoo.util.extension.bitmapToFile

class HomeConfirmDialogFragment :
    BindingDialogFragment<DialogHomeCofirmBinding>(R.layout.dialog_home_cofirm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutSize(0.91f, 0.45f)

        val safeArgs: HomeConfirmDialogFragmentArgs by navArgs()
        context?.bitmapToFile(safeArgs.photoBitmap, "FILE_NAME")
        // TODO:: network success exercise verify
    }
}
