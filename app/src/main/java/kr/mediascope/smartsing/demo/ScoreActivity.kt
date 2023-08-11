package kr.mediascope.smartsing.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.mediascope.smartsing.demo.common.extension.statusBarHeight
import kr.mediascope.smartsing.demo.common.utils.Utils
import kr.mediascope.smartsing.demo.databinding.ActivityScoreBinding
import org.json.JSONException
import org.json.JSONObject

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score)
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        initView()
        showScore()
    }

    private fun initView() {
        /** StatusBar, NavigationBar padding 설정 */
        binding.clScore.setPadding(0, this.statusBarHeight(), 0, 0)

        binding.tvScoreRetry.text = getString(R.string.audio_sing_retry)
        binding.tvScoreStop.text = getString(R.string.audio_sing_stop)
        binding.tvSongTitle.text = intent.getStringExtra("TITLE")
        binding.ibArrowBack.setOnClickListener {
            finish()
        }
        binding.tvScoreRetry.setOnClickListener {
            setResult(
                KaraokeActivity.RESULT_CODE_AUDIO_SING_RETRY,
                Intent().putExtra("SING_OPTION", intent.getStringExtra("SING_OPTION"))
            )
            finish()
        }
        binding.tvScoreStop.setOnClickListener {
            finish()
        }
    }

    private fun showScore() {
        intent.getStringExtra("SCORE")?.let { scoreJsonString ->
            try {
                val scoreJsonOb = JSONObject(scoreJsonString)
                val fRhythmAccuracy = scoreJsonOb.get("RhythmAccuracy").toString().toFloat()
                val fPitchAccuracy = scoreJsonOb.get("PitchAccuracy").toString().toFloat()
                val score = (((fRhythmAccuracy * 100) + (fPitchAccuracy * 100)) / 2).toInt()

                if (score >= 100) {
                    binding.ivNum100.visibility = View.VISIBLE
                } else {
                    binding.ivNum100.visibility = View.GONE
                }

                val scoreDivide10 = score / 10
                if (scoreDivide10 > 0) {
                    binding.ivNum10.visibility = View.VISIBLE
                    binding.ivNum10.setImageResource(Utils.scoreToNumImg(this, score, 10))
                } else {
                    binding.ivNum10.visibility = View.GONE
                }

                binding.ivNum1.setImageResource(Utils.scoreToNumImg(this, score, 1))

            } catch (je: JSONException) {
                //
            }
        }
    }

}