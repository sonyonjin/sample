package kr.mediascope.smartsing.demo

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kr.mediascope.smartsing.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        init()
    }

    override fun init() {
        initView()
    }

    override fun initView() {
        binding.btKaraoke.setOnClickListener {
            startActivity(Intent(this, KaraokeActivity::class.java))
        }
    }
}