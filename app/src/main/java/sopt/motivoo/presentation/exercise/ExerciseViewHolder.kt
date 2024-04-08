package sopt.motivoo.presentation.exercise

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import sopt.motivoo.R
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseTodayBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo
import sopt.motivoo.presentation.exercise.ExerciseFragment.Companion.CHILD

class ExerciseEachDateInfoViewHolder(
    private val binding: ItemExerciseBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        opponentUserType: String,
    ) {
        setHistory(exerciseItemInfoData, opponentUserType)
    }

    private fun setHistory(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        opponentUserType: String,
    ) {
        with(binding) {
            initText(exerciseItemInfoData, binding, opponentUserType)
            val context = binding.root.context
            initImage(context, exerciseItemInfoData, binding)
            checkStatus(exerciseItemInfoData, binding, context)
        }
    }

    private fun initText(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        opponentUserType: String,
    ) {
        with(binding) {
            tvItemExerciseDate.text = exerciseItemInfoData.date
            tvItemExerciseImgBottomTxtLeft.text = exerciseItemInfoData.myMissionContent
            tvItemExerciseImgBottomTxtRight.text = exerciseItemInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseItemInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseItemInfoData.opponentMissionStatus
            tvItemExerciseOpponentExercise.text =
                if (opponentUserType == CHILD) root.context.getString(R.string.exercise_child_exercise) else root.context.getString(
                    R.string.exercise_parent_exercise
                )
        }
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        return dp * (context.resources.displayMetrics.density)
    }

    private fun initImage(
        context: Context,
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
    ) {
        val pxValue = dpToPx(context, 8f)
        with(binding) {
            if (exerciseItemInfoData.myMissionImgUrl != null) {
                ivItemExerciseLeftImage.load(exerciseItemInfoData.myMissionImgUrl) {
                    transformations(RoundedCornersTransformation(pxValue))
                }
            } else if (exerciseItemInfoData.myMissionStatus == "없음") {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_choose_exercise)
            } else {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_success_next_exercise)
            }
            if (exerciseItemInfoData.opponentMissionImgUrl != null) {
                ivItemExerciseRightImage.load(exerciseItemInfoData.opponentMissionImgUrl) {
                    transformations(RoundedCornersTransformation(pxValue))
                }
            } else if (exerciseItemInfoData.opponentMissionStatus == "없음") {
                ivItemExerciseRightImage.setImageResource(R.drawable.img_choose_exercise)
            } else {
                ivItemExerciseRightImage.setImageResource(R.drawable.img_success_next_exercise)
            }
        }
    }

    private fun checkStatus(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        context: Context,
    ) {
        when (exerciseItemInfoData.myMissionStatus) {
            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseMyState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseMyState.visibility = View.GONE
            }
        }
        when (exerciseItemInfoData.opponentMissionStatus) {
            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseParentState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseParentState.visibility = View.GONE
            }
        }
    }

    companion object {
        const val STATE_SUCCESS_TYPE = "성공"
    }
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseTodayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo,
        userType: String,
        opponentUserType: String,
        itemCount: Int,
    ) {
        clickQuestionMark()
        setCharacterIcon(userType, opponentUserType)
        setText(exerciseNoticeData)
        setEmptyHistory(itemCount)
    }

    private fun clickQuestionMark() {
        val context = binding.root.context
        val balloon = Balloon.Builder(context)
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidth(BalloonSizeSpec.WRAP)
            .setTextResource(R.string.exercise_today_question_notice)
            .setArrowColorResource(R.color.white_FFFFFF)
            .setBackgroundColorResource(R.color.white_FFFFFF)
            .setTextColorResource(R.color.gray_800_303031)
            .setTextSize(15f)
            .setTextTypeface(R.font.pretendard)
            .setPaddingLeft(11)
            .setPaddingRight(15)
            .setPaddingTop(14)
            .setPaddingBottom(15)
            .setMarginTop(3)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setIconDrawableResource(R.drawable.ic_notice)
            .setCornerRadius(6f)
            .setArrowPosition(0.5f)
            .setElevation(5)
            .setArrowSize(13)
            .build()

        binding.ivExerciseTodayIconQuestion.setOnClickListener {
            balloon.showAlignTop(binding.ivExerciseTodayIconQuestion)
        }
    }

    private fun setCharacterIcon(userType: String, opponentUserType: String) {
        if (userType == CHILD) binding.ivExerciseTodayIconLeft.setImageResource(R.drawable.ic_child_left) else binding.ivExerciseTodayIconLeft.setImageResource(
            R.drawable.ic_parent_left
        )
        if (opponentUserType == CHILD) binding.ivExerciseTodayIconRight.setImageResource(R.drawable.ic_child_right) else binding.ivExerciseTodayIconRight.setImageResource(
            R.drawable.ic_parent_right
        )
    }

    private fun setText(exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo) {
        val context = binding.root.context
        if (exerciseNoticeData.missionContent == null) {
            setTextNoMission(context)
        } else {
            setTextYesMission(context, exerciseNoticeData)
        }
    }

    private fun setTextNoMission(context: Context) {
        with(binding) {
            tvExerciseTodayExercise.text =
                context.getString(R.string.exercise_please_select_today_mission)
            clExerciseTodaySelectTodayMission.visibility = View.VISIBLE
            tvExerciseTodayMission.visibility = View.GONE
            ivExerciseTodayBubbleLeft.visibility = View.GONE
            ivExerciseTodayBubbleRight.visibility = View.GONE
            setClickEvents()
        }
    }

    private fun setClickEvents() {
        binding.clExerciseTodaySelectTodayMission.setOnClickListener {
            it.findNavController().navigate(R.id.action_exerciseFragment_to_homeFragment)
        }
    }

    private fun setTextYesMission(
        context: Context,
        exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo,
    ) {
        with(binding) {
            tvExerciseTodayExercise.text = context.getString(R.string.exercise_today_exercise)
            clExerciseTodaySelectTodayMission.visibility = View.GONE
            tvExerciseTodayMission.text = exerciseNoticeData.missionContent
        }
        setTodayImageAndBubble(context, exerciseNoticeData)
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        return dp * (context.resources.displayMetrics.density)
    }

    private fun setTodayImageAndBubble(
        context: Context,
        exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo,
    ) {
        val pxValue = dpToPx(context, 8f)
        if (exerciseNoticeData.missionDate == exerciseNoticeData.todayDate) {
            if (exerciseNoticeData.myMissionStatus == ExerciseEachDateInfoViewHolder.STATE_SUCCESS_TYPE) {
                binding.ivExerciseTodayBubbleLeft.setImageResource(R.drawable.ic_bubble_success)
                binding.ivExerciseTodayImageLeft.load(exerciseNoticeData.myMissionImgUrl) {
                    transformations(RoundedCornersTransformation(pxValue))
                }
            } else {
                binding.ivExerciseTodayBubbleLeft.setImageResource(R.drawable.ic_bubble_exercising)
            }
            if (exerciseNoticeData.opponentMissionStatus == ExerciseEachDateInfoViewHolder.STATE_SUCCESS_TYPE) {
                binding.ivExerciseTodayBubbleRight.setImageResource(R.drawable.ic_bubble_success)
                binding.ivExerciseTodayImageRight.load(exerciseNoticeData.opponentMissionImgUrl) {
                    transformations(RoundedCornersTransformation(pxValue))
                }
            } else {
                binding.ivExerciseTodayBubbleRight.setImageResource(R.drawable.ic_bubble_exercising)
            }
        }
    }

    private fun setEmptyHistory(itemCount: Int) {
        if (itemCount >= 2) {
            binding.ivExerciseTodayEmptyHistory.visibility = View.GONE
        }
    }
}
