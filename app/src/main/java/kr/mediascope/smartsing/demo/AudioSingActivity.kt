package kr.mediascope.smartsing.demo

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import app.singit.smartsing.common.FileDownloader
//import app.singit.smartsing.common.SmartSingUtility
//import app.singit.smartsing.recording.SmartSingRecording
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.*
import kr.mediascope.smartsing.demo.adaptor.StyleAdaptor
import kr.mediascope.smartsing.demo.adaptor.StyleAdaptor.ItemClickListener
import org.json.JSONObject
import java.io.File
import java.util.*

class AudioSingActivity :
    AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_audio_sing)

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                val intent = Intent()
                intent.putExtra("SING_OPTION", intent.getStringExtra("SING_OPTION"))

                val scoreJsonOb = JSONObject()
                scoreJsonOb.put("RhythmAccuracy", 1f)
                scoreJsonOb.put("PitchAccuracy", 1f)
                intent.putExtra("SCORE", scoreJsonOb.toString())

                setResult(KaraokeActivity.RESULT_CODE_AUDIO_SING_SCORE, intent)
                finish()
            }
        }
    }

}