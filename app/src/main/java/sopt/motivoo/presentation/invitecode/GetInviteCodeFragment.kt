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

class GetInviteCodeFragment :
    BindingFragment<FragmentGetInviteCodeBinding>(R.layout.fragment_get_invite_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMatchingToastAnimation()
        setClipboard()
    }

    private fun setClipboard() {
        val clipboard: ClipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val inviteCode = binding.tvGetInviteCode.text
        val formattedText = getString(R.string.share_text_message, inviteCode)
        val clip = ClipData.newPlainText(SHARE_TEXT, formattedText)

        binding.btnGetInviteCodeCopy.setOnSingleClickListener {
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                setClipBoardToastAnimation()
        }
    }

    private fun setMatchingToastAnimation() {
        binding.btnGetInviteCodeCheckMatching.setOnSingleClickListener {
            val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeIn)
            binding.tvGetInviteCodeMatchingWaiting.visibility = VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeOut)
                binding.tvGetInviteCodeMatchingWaiting.visibility = View.GONE
            }, TWO_SECONDS)
        }
    }

    private fun setClipBoardToastAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvGetInviteCodeToast.startAnimation(fadeIn)
        binding.tvGetInviteCodeToast.visibility = VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvGetInviteCodeToast.startAnimation(fadeOut)
            binding.tvGetInviteCodeToast.visibility = View.GONE
        }, TWO_SECONDS)
    }

    companion object {
        const val TWO_SECONDS = 2000L
        private const val SHARE_TEXT = "share_text"
    }
}
