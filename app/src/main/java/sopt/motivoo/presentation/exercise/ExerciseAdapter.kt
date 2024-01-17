package sopt.motivoo.presentation.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.exercise.ExerciseInfo

class ExerciseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var exerciseInfoList: List<ExerciseInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            NOTICE_INFO_TYPE -> {
                val binding = ItemExerciseNoticeBinding.inflate(inflater, parent, false)
                ExerciseNoticeViewHolder(binding)
            }

            else -> {
                val binding = ItemExerciseBinding.inflate(inflater, parent, false)
                ExerciseEachDateInfoViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExerciseNoticeViewHolder -> {
                val noticeInfo = exerciseInfoList[position]
                holder.onBind(noticeInfo as ExerciseInfo.NoticeInfo)
            }

            is ExerciseEachDateInfoViewHolder -> {
                val dateExerciseInfo = exerciseInfoList[position]
                holder.onBind(dateExerciseInfo as ExerciseInfo.EachDateInfo)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (exerciseInfoList[position]) {
            is ExerciseInfo.NoticeInfo -> NOTICE_INFO_TYPE
            is ExerciseInfo.EachDateInfo -> DATE_EXERCISE_INFO_TYPE
        }
    }

    override fun getItemCount() = exerciseInfoList.size

    fun setExerciseList(exerciseList: List<ExerciseInfo>) {
        this.exerciseInfoList = exerciseList.toList()
        notifyDataSetChanged()
    }

    companion object {
        const val NOTICE_INFO_TYPE = 0
        const val DATE_EXERCISE_INFO_TYPE = 1
    }
}
