package sopt.motivoo.presentation

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.android.utils.Child
import com.android.utils.Parent
import sopt.motivoo.databinding.ActivitySampleBinding

class SampleDesignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customView.userType = Parent
        binding.customViewOther.userType = Child

        binding.leftSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, seekValue: Int, p2: Boolean) {
                binding.customView.setPercent(seekValue / 50f)
                binding.customView.userType = Parent
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.rightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, seekValue: Int, p2: Boolean) {
                binding.customViewOther.setPercent(seekValue / 50f)
                binding.customViewOther.userType = Child
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
}
