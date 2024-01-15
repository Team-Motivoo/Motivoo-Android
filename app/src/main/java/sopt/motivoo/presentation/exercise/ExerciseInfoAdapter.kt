package sopt.motivoo.presentation.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.ExerciseInfo

class ExerciseInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val noticeInfoType = 0
        const val dateExerciseInfoType = 1
    }

    private var exerciseInfoList: List<ExerciseInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        return when (viewType) {
            noticeInfoType -> {
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
            is ExerciseInfo.NoticeInfo -> noticeInfoType
            is ExerciseInfo.EachDateInfo -> dateExerciseInfoType
        }
    }

    override fun getItemCount() = exerciseInfoList.size

    fun setExerciseList(exerciseList: List<ExerciseInfo>) {
        this.exerciseInfoList = exerciseList.toList()
        notifyDataSetChanged()
    }
}
