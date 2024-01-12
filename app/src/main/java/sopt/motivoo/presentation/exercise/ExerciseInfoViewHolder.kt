package sopt.motivoo.presentation.exercise

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sopt.motivoo.R
import sopt.motivoo.data.model.ExerciseInfo
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding

class ExerciseEachDateInfoViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseInfoData: ExerciseInfo.EachDateInfo) {
        with(binding) {
            tvItemExerciseDate.text = exerciseInfoData.date
            ivItemExerciseImg1.load(exerciseInfoData.myMissionImgUrl)
            ivItemExerciseImg2.load(exerciseInfoData.opponentMissionImgUrl)
            tvItemExerciseImgBtmTxt1.text = exerciseInfoData.myMissionContent
            tvItemExerciseImgBtmTxt2.text = exerciseInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseInfoData.opponentMissionStatus
            if (exerciseInfoData.myMissionImgUrl == null) {
                ivItemExerciseImg1.visibility = View.INVISIBLE
                tvItemExerciseNoImageBeforeExercise1.visibility = View.VISIBLE
            } else {
                ivItemExerciseImg1.visibility = View.VISIBLE
                tvItemExerciseNoImageBeforeExercise1.visibility = View.INVISIBLE
            }
            if (exerciseInfoData.opponentMissionImgUrl == null) {
                ivItemExerciseImg2.visibility = View.INVISIBLE
                tvItemExerciseNoImageBeforeExercise2.visibility = View.VISIBLE
            } else {
                ivItemExerciseImg2.visibility = View.VISIBLE
                tvItemExerciseNoImageBeforeExercise2.visibility = View.INVISIBLE
            }
            when (exerciseInfoData.myMissionStatus) {
                "진행중" -> {
                    tvItemExerciseMyState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.pink_100_FFE6F5)
                    val textColorPink: Int = ContextCompat.getColor(itemView.context, R.color.pink_FF19A3)
                    tvItemExerciseMyState.setTextColor(textColorPink)
                }
                "성공" -> {
                    tvItemExerciseMyState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.blue_100_D7F6FF)
                    val textColorBlue: Int = ContextCompat.getColor(itemView.context, R.color.blue_600_2E9ABB)
                    tvItemExerciseMyState.setTextColor(textColorBlue)
                }
                else -> {
                    tvItemExerciseMyState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.gray_300_D5D5D7)
                    val textColorGray: Int = ContextCompat.getColor(itemView.context, R.color.gray_700_464747)
                    tvItemExerciseMyState.setTextColor(textColorGray)
                }
            }
            when (exerciseInfoData.opponentMissionStatus) {
                "진행중" -> {
                    tvItemExerciseParentState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.pink_100_FFE6F5)
                    val textColorPink: Int = ContextCompat.getColor(itemView.context, R.color.pink_FF19A3)
                    tvItemExerciseParentState.setTextColor(textColorPink)
                }
                "성공" -> {
                    tvItemExerciseParentState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.blue_100_D7F6FF)
                    val textColorBlue: Int = ContextCompat.getColor(itemView.context, R.color.blue_600_2E9ABB)
                    tvItemExerciseParentState.setTextColor(textColorBlue)
                }
                else -> {
                    tvItemExerciseParentState.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.gray_300_D5D5D7)
                    val textColorGray: Int = ContextCompat.getColor(itemView.context, R.color.gray_700_464747)
                    tvItemExerciseParentState.setTextColor(textColorGray)
                }
            }
        }
    }
}

// 날짜별 텍스트 문구 다름
// 자식, 부모에 따라 위치 바꾸기

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseInfo.NoticeInfo) {
        binding.tvExerciseTodayMission.text = exerciseNoticeData.missionContent
    }
}