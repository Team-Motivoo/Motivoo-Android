package sopt.motivoo.presentation.invitecode

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentGetInviteCodeBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.showToast

class GetInviteCodeFragment :
    BindingFragment<FragmentGetInviteCodeBinding>(R.layout.fragment_get_invite_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetInviteCodeCheckMatching.setOnSingleClickListener {
            val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeIn)
            binding.tvGetInviteCodeMatchingWaiting.visibility = VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeOut)
                binding.tvGetInviteCodeMatchingWaiting.visibility = View.GONE
            }, 2000)
        }

        val clipboard: ClipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", "STRING")

        binding.btnGetInviteCodeCopy.setOnSingleClickListener {
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                requireContext().showToast("클립보드에 복사되었습니다.")
        }
    }
}
