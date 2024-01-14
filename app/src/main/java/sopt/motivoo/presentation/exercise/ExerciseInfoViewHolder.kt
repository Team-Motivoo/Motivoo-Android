package sopt.motivoo.presentation.exercise

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sopt.motivoo.R
import sopt.motivoo.data.model.ExerciseInfo
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseEachDateInfoViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseInfoData: ExerciseInfo.EachDateInfo) {
        with(binding) {
            val context = binding.root.context
            changePosition(exerciseInfoData, binding, context)

            tvItemExerciseDate.text = exerciseInfoData.date
            ivItemExerciseImg1.load(exerciseInfoData.myMissionImgUrl)
            ivItemExerciseImg2.load(exerciseInfoData.opponentMissionImgUrl)
            tvItemExerciseImgBtmTxt1.text = exerciseInfoData.myMissionContent
            tvItemExerciseImgBtmTxt2.text = exerciseInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseInfoData.opponentMissionStatus
            imageVisibility(exerciseInfoData, binding)
            checkStatus(exerciseInfoData, binding, context)
            compareDate(binding, context)
        }
    }
}

fun changePosition(
    exerciseInfoData: ExerciseInfo.EachDateInfo,
    binding: ItemExerciseBinding,
    context: Context,
) {
    if (exerciseInfoData.userType == "PARENT") {
        binding.tvItemExerciseMyExercise.text = context.getString(R.string.exercise_my_exercise)
        binding.tvItemExerciseParentExercise.text =
            context.getString(R.string.exercise_child_exercise)
    }
}

fun imageVisibility(exerciseInfoData: ExerciseInfo.EachDateInfo, binding: ItemExerciseBinding) {
    if (exerciseInfoData.myMissionImgUrl == null) {
        binding.ivItemExerciseImg1.visibility = View.INVISIBLE
        binding.tvItemExerciseNoImageBeforeExercise1.visibility = View.VISIBLE
    } else {
        binding.ivItemExerciseImg1.visibility = View.VISIBLE
        binding.tvItemExerciseNoImageBeforeExercise1.visibility = View.INVISIBLE
    }
    if (exerciseInfoData.opponentMissionImgUrl == null) {
        binding.ivItemExerciseImg2.visibility = View.INVISIBLE
        binding.tvItemExerciseNoImageBeforeExercise2.visibility = View.VISIBLE
    } else {
        binding.ivItemExerciseImg2.visibility = View.VISIBLE
        binding.tvItemExerciseNoImageBeforeExercise2.visibility = View.INVISIBLE
    }
}

fun checkStatus(
    exerciseInfoData: ExerciseInfo.EachDateInfo,
    binding: ItemExerciseBinding,
    context: Context,
) {
    when (exerciseInfoData.myMissionStatus) {
        "진행중" -> {
            binding.tvItemExerciseMyState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
            val textColorPink: Int =
                ContextCompat.getColor(context, R.color.pink_FF19A3)
            binding.tvItemExerciseMyState.setTextColor(textColorPink)
        }

        "성공" -> {
            binding.tvItemExerciseMyState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
            val textColorBlue: Int =
                ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
            binding.tvItemExerciseMyState.setTextColor(textColorBlue)
        }

        else -> {
            binding.tvItemExerciseMyState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
            val textColorGray: Int =
                ContextCompat.getColor(context, R.color.gray_700_464747)
            binding.tvItemExerciseMyState.setTextColor(textColorGray)
        }
    }
    when (exerciseInfoData.opponentMissionStatus) {
        "진행중" -> {
            binding.tvItemExerciseParentState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
            val textColorPink: Int =
                ContextCompat.getColor(context, R.color.pink_FF19A3)
            binding.tvItemExerciseParentState.setTextColor(textColorPink)
        }

        "성공" -> {
            binding.tvItemExerciseParentState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
            val textColorBlue: Int =
                ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
            binding.tvItemExerciseParentState.setTextColor(textColorBlue)
        }

        else -> {
            binding.tvItemExerciseParentState.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
            val textColorGray: Int =
                ContextCompat.getColor(context, R.color.gray_700_464747)
            binding.tvItemExerciseParentState.setTextColor(textColorGray)
        }
    }
}

fun compareDate(binding: ItemExerciseBinding, context: Context) {
    val currentDate = getCurrentDate()
    if (currentDate == binding.tvItemExerciseDate.text.substring(0, 13)) {
        binding.tvItemExerciseNoImageBeforeExercise1.text =
            context.getString(R.string.exercise_no_image_before_exercise_today)
        binding.tvItemExerciseNoImageBeforeExercise2.text =
            context.getString(R.string.exercise_no_image_before_exercise_today)
    }

}

fun getCurrentDate(): String {
    val dataFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    val currentDate = Date()
    return dataFormat.format(currentDate)
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseInfo.NoticeInfo) {
        binding.tvExerciseTodayMission.text = exerciseNoticeData.missionContent
    }
}