package sopt.motivoo.presentation.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo

class ExerciseAdapter(private val userType: String) : ListAdapter<ExerciseItemInfo, RecyclerView.ViewHolder>(diffUtil) {
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
                val noticeInfo = currentList[position]
                holder.onBind(noticeInfo as ExerciseItemInfo.NoticeItemInfo)
            }

            is ExerciseEachDateInfoViewHolder -> {
                val dateExerciseInfo = currentList[position]
                holder.onBind(dateExerciseInfo as ExerciseItemInfo.EachDateItemInfo, userType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ExerciseItemInfo.NoticeItemInfo -> NOTICE_INFO_TYPE
            is ExerciseItemInfo.EachDateItemInfo -> DATE_EXERCISE_INFO_TYPE
        }
    }

    companion object {
        const val NOTICE_INFO_TYPE = 0
        const val DATE_EXERCISE_INFO_TYPE = 1

        val diffUtil = object : DiffUtil.ItemCallback<ExerciseItemInfo>() {
            override fun areItemsTheSame(
                oldItem: ExerciseItemInfo,
                newItem: ExerciseItemInfo
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ExerciseItemInfo,
                newItem: ExerciseItemInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
