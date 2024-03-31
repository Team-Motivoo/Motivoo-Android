package sopt.motivoo.util.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import sopt.motivoo.R

abstract class BindingDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
) : DialogFragment() {
    private var _binding: T? = null
    protected val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    protected fun setLayoutSizeRatio(widthPercent: Float, heightPercent: Float) {
        context?.resources?.displayMetrics?.let { metrics ->
            binding.root.layoutParams.apply {
                width = ((metrics.widthPixels * widthPercent).toInt())
                height = ((metrics.heightPixels * heightPercent).toInt())
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
