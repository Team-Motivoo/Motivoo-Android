package sopt.motivoo.presentation.exercise

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo
import sopt.motivoo.presentation.exercise.ExerciseFragment.Companion.CHILD
import sopt.motivoo.util.extension.prettyString
import java.time.LocalDate

class ExerciseEachDateInfoViewHolder(
    private val binding: ItemExerciseBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        userType: String,
        itemSize: Int,
    ) {
        setHistoryOrNot(exerciseItemInfoData, userType, itemSize)
    }

    private fun setHistoryOrNot(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        userType: String,
        itemSize: Int,
    ) {
        with(binding) {
            fun String.removeDayOfTheWeek(): String = this.removeRange(length - 4 until length)
            if (itemSize == 2 && exerciseItemInfoData.date!!.removeDayOfTheWeek() == LocalDate.now().prettyString) {
                ivExerciseEmptyHistory.visibility = View.VISIBLE
                ivItemExerciseLeftImage.visibility = View.GONE
                tvItemExerciseMyExercise.visibility = View.GONE
                tvItemExerciseOpponentExercise.visibility = View.GONE
                ivItemExerciseRightImage.visibility = View.GONE
            } else {
                ivExerciseEmptyHistory.visibility = View.GONE
                initText(exerciseItemInfoData, binding, userType)
                initImage(exerciseItemInfoData, binding)
                val context = binding.root.context
                checkStatus(exerciseItemInfoData, binding, context)
            }
        }
    }

    private fun initText(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        userType: String,
    ) {
        with(binding) {
            tvItemExerciseDate.text = exerciseItemInfoData.date
            tvItemExerciseImgBottomTxtLeft.text = exerciseItemInfoData.myMissionContent
            tvItemExerciseImgBottomTxtRight.text = exerciseItemInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseItemInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseItemInfoData.opponentMissionStatus
            tvItemExerciseOpponentExercise.text =
                if (userType == CHILD) root.context.getString(R.string.exercise_parent_exercise) else root.context.getString(
                    R.string.exercise_child_exercise
                )
        }
    }

    private fun initImage(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
    ) {
        with(binding) {
            if (exerciseItemInfoData.myMissionImgUrl != null) {
                ivItemExerciseLeftImage.load(exerciseItemInfoData.myMissionImgUrl)
            } else if (exerciseItemInfoData.myMissionStatus == "없음") {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_choose_exercise)
            } else {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_success_next_exercise)
            }
            if (exerciseItemInfoData.opponentMissionImgUrl != null) {
                ivItemExerciseRightImage.load(exerciseItemInfoData.opponentMissionImgUrl)
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

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo, userType: String) {
        setCharacterIcon(userType)
        setText(exerciseNoticeData)
    }

    private fun setCharacterIcon(userType: String) {
        if (userType == CHILD) {
            binding.ivExerciseTodayIconLeft.setImageResource(R.drawable.ic_child_left)
            binding.ivExerciseTodayIconRight.setImageResource(R.drawable.ic_parent_right)
        } else {
            binding.ivExerciseTodayIconLeft.setImageResource(R.drawable.ic_parent_left)
            binding.ivExerciseTodayIconRight.setImageResource(R.drawable.ic_child_right)
        }
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
            clExerciseSelectTodayMission.visibility = View.VISIBLE
            tvExerciseTodayMission.visibility = View.GONE
            ivExerciseTodayBubbleLeft.visibility = View.GONE
            ivExerciseTodayBubbleRight.visibility = View.GONE
            setClickEvents()
        }
    }

    private fun setClickEvents() {
        binding.clExerciseSelectTodayMission.setOnClickListener {
            it.findNavController().navigate(R.id.action_exerciseFragment_to_homeFragment)
        }
    }

    private fun setTextYesMission(
        context: Context,
        exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo,
    ) {
        with(binding) {
            tvExerciseTodayExercise.text = context.getString(R.string.exercise_today_exercise)
            clExerciseSelectTodayMission.visibility = View.GONE
            tvExerciseTodayMission.text = exerciseNoticeData.missionContent
        }
        setTodayImageAndBubble(exerciseNoticeData)
    }

    private fun setTodayImageAndBubble(
        exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo,
    ) {
        if (exerciseNoticeData.missionDate == exerciseNoticeData.todayDate) {
            if (exerciseNoticeData.myMissionStatus == ExerciseEachDateInfoViewHolder.STATE_SUCCESS_TYPE) {
                binding.ivExerciseTodayBubbleLeft.setImageResource(R.drawable.ic_bubble_success)
                binding.ivExerciseTodayImageLeft.load(exerciseNoticeData.myMissionImgUrl)
            } else {
                binding.ivExerciseTodayBubbleLeft.setImageResource(R.drawable.ic_bubble_exercising)
                binding.ivExerciseTodayImageLeft.setImageResource(R.drawable.img_notexercise)
            }
            if (exerciseNoticeData.opponentMissionStatus == ExerciseEachDateInfoViewHolder.STATE_SUCCESS_TYPE) {
                binding.ivExerciseTodayBubbleRight.setImageResource(R.drawable.ic_bubble_success)
                binding.ivExerciseTodayImageRight.load(exerciseNoticeData.opponentMissionImgUrl)
            } else {
                binding.ivExerciseTodayBubbleRight.setImageResource(R.drawable.ic_bubble_exercising)
            }
        }
    }
}
