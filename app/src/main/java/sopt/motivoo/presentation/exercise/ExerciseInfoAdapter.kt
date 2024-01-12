package sopt.motivoo.presentation.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sopt.motivoo.data.model.ExerciseInfo
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding

class ExerciseInfoAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private val notice_info_type = 0
    private val date_exercise_info_type = 1

    private var exerciseInfoList: List<ExerciseInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            notice_info_type -> {
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
                val date_exercise_info = exerciseInfoList[position]
                holder.onBind(date_exercise_info as ExerciseInfo.EachDateInfo)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (exerciseInfoList[position]) {
            is ExerciseInfo.NoticeInfo -> notice_info_type
            is ExerciseInfo.EachDateInfo -> date_exercise_info_type
        }
    }
    override fun getItemCount() = exerciseInfoList.size
    fun setExerciseList(exerciseList: List<ExerciseInfo>) {
        this.exerciseInfoList = exerciseList.toList()
        notifyDataSetChanged()
    }
}