package sopt.motivoo.presentation.Exercise

import android.graphics.Color
import android.view.View
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
            ivItemExerciseImg1.load(exerciseInfoData.my_mission_img_url)
            ivItemExerciseImg2.load(exerciseInfoData.opponent_mission_img_url)
            tvItemExerciseMyExercise.text = exerciseInfoData.my_mission_content
            tvItemExerciseParentExercise.text = exerciseInfoData.opponent_mission_content
            tvItemExerciseMyState.text = exerciseInfoData.my_mission_status
            tvItemExerciseParentState.text = exerciseInfoData.opponent_mission_status
            if (exerciseInfoData.my_mission_img_url == null) {
                ivItemExerciseImg1.visibility = View.INVISIBLE
                ivItemExerciseImg1.visibility = View.VISIBLE
            } else {
                ivItemExerciseImg1.visibility = View.VISIBLE
                ivItemExerciseImg1.visibility = View.INVISIBLE
            }
            if (exerciseInfoData.opponent_mission_img_url == null) {
                ivItemExerciseImg2.visibility = View.INVISIBLE
                ivItemExerciseImg2.visibility = View.VISIBLE
            } else {
                ivItemExerciseImg2.visibility = View.VISIBLE
                ivItemExerciseImg2.visibility = View.INVISIBLE
            }
            if (exerciseInfoData.my_mission_status == "진행중") {
                tvItemExerciseMyState.setBackgroundColor(Color.parseColor(R.color.pink_100_FFE6F5.toString()))
                tvItemExerciseMyState.setTextColor(Color.parseColor(R.color.pink_FF19A3.toString()))
            } else if (exerciseInfoData.my_mission_status == "성공") {
                tvItemExerciseMyState.setBackgroundColor(Color.parseColor(R.color.blue_100_D7F6FF.toString()))
                tvItemExerciseMyState.setTextColor(Color.parseColor(R.color.blue_600_2E9ABB.toString()))
            } else {
                tvItemExerciseMyState.setBackgroundColor(Color.parseColor(R.color.gray_300_D5D5D7.toString()))
                tvItemExerciseMyState.setTextColor(Color.parseColor(R.color.gray_700_464747.toString()))
            }
        }
    }
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseInfo.NoticeInfo) {
        binding.tvExerciseTodayExercise.text = exerciseNoticeData.mission_content
    }
}