package sopt.motivoo.util.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import sopt.motivoo.R
import sopt.motivoo.databinding.MotivooDeniedPermissionBinding

class MotivooDeniedPermission @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private lateinit var binding: MotivooDeniedPermissionBinding
    var onButtonClickListener: OnClickListener? = null

    init {
        initView()
    }

    private fun initView() {
        binding = MotivooDeniedPermissionBinding.bind(
            inflate(
                context,
                R.layout.motivoo_denied_permission,
                this
            )
        ).apply {
            btnAllow.setOnClickListener { onButtonClickListener?.onClick(it) }
        }
    }

    inline fun setOnClickListener(crossinline onClick: (View) -> Unit) {
        this.onButtonClickListener = OnClickListener { view -> onClick(view) }
    }
}
