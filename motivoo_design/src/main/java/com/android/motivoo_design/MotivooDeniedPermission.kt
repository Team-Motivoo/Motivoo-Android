package com.android.motivoo_design

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.motivoo_design.databinding.MotivooDeniedPermissionBinding

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