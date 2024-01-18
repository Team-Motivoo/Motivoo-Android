package sopt.motivoo.presentation.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo

class ExerciseAdapter(private val userType: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var exerciseItemInfoList: List<ExerciseItemInfo> = emptyList()

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
                val noticeInfo = exerciseItemInfoList[position]
                holder.onBind(noticeInfo as ExerciseItemInfo.NoticeItemInfo)
            }

            is ExerciseEachDateInfoViewHolder -> {
                val dateExerciseInfo = exerciseItemInfoList[position]
                holder.onBind(dateExerciseInfo as ExerciseItemInfo.EachDateItemInfo, userType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (exerciseItemInfoList[position]) {
            is ExerciseItemInfo.NoticeItemInfo -> NOTICE_INFO_TYPE
            is ExerciseItemInfo.EachDateItemInfo -> DATE_EXERCISE_INFO_TYPE
        }
    }

    override fun getItemCount() = exerciseItemInfoList.size

    fun updateItemList(exerciseList: List<ExerciseItemInfo>) {
        this.exerciseItemInfoList = exerciseList.toList()
        notifyDataSetChanged()
    }

    companion object {
        const val NOTICE_INFO_TYPE = 0
        const val DATE_EXERCISE_INFO_TYPE = 1
    }
}
