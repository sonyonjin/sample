package kr.mediascope.smartsing.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kr.mediascope.smartsing.demo.common.divider.BaseDividerItemDecoration
import kr.mediascope.smartsing.demo.common.extension.navigationBarHeight
import kr.mediascope.smartsing.demo.common.extension.repeatOnStarted
import kr.mediascope.smartsing.demo.common.utils.Utils
import kr.mediascope.smartsing.demo.data.model.remote.MrItem
import kr.mediascope.smartsing.demo.databinding.ActivityKaraokeBinding
import org.json.JSONException
import org.json.JSONObject


/**
 * 노래방 화면
 */
class KaraokeActivity : BaseActivity() {
    private lateinit var binding: ActivityKaraokeBinding
    private val viewModel: KaraokeViewModel by viewModels()
    private var clSearchTop: Int = 0
    private var mrItem: MrItem? = null
    private var isMoreScroll: Boolean = false

    private val audioSingStartForResult = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        audioSingActivityResult(result)
    }

    private fun audioSingActivityResult(result: ActivityResult) {
        when (result.resultCode) {
            RESULT_CODE_AUDIO_SING_FORCED_FINISH -> {
                finish()
            }

            RESULT_CODE_AUDIO_SING_RETRY -> {
                result.data?.getStringExtra("SING_OPTION")?.let {
                    val intent = Intent(this@KaraokeActivity, AudioSingActivity::class.java)
                    intent.putExtra("SING_OPTION", it)
                    audioSingStartForResult.launch(intent)
                }
            }

            RESULT_CODE_AUDIO_SING_SCORE -> {
                val intent = Intent(this@KaraokeActivity, ScoreActivity::class.java)
                intent.putExtra("SING_OPTION", result.data?.getStringExtra("SING_OPTION"))
                intent.putExtra("TITLE", mrItem?.title_ko)
                intent.putExtra("SCORE", result.data?.getStringExtra("SCORE"))
                audioSingStartForResult.launch(intent)
            }
        }
    }

    private val mrListAdapter: MrListAdapter by lazy {
        MrListAdapter(onItemClick = { mrItem ->
            this.mrItem = mrItem
            checkPermission()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_karaoke)
        binding.lifecycleOwner = this
        init()
    }

    /**
     * 초기화
     */
    override fun init() {
        isMoreScroll = true
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        /** StatusBar, NavigationBar padding 설정 */
        binding.svMain.setPadding(0, 0, 0, this.navigationBarHeight())
        binding.clSearch.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                clSearchTop = binding.clSearch.top + Utils.dpToPx(this@KaraokeActivity, 88F).toInt()
                binding.clSearch.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        binding.ibArrowBack.setOnClickListener {
            finish()
        }

        binding.ivSearch.setOnClickListener {
            searchMr(binding.etSearchSong.text.toString(), true)
        }

        binding.etSearchSong.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    searchMr(binding.etSearchSong.text.toString(), true)
                    true
                }

                else -> {
                    false
                }
            }
        }

        // 입력창 포커스되면 자동완성 문자열이 방해되니 추가로 추가로 위로스크롤하기
        binding.etSearchSong.setOnFocusChangeListener { v, hasFocus ->
            Log.d("syj", "isMoreScroll=" + isMoreScroll)
            if (hasFocus && isMoreScroll) {
                isMoreScroll = false // 한번하면 다음 검색시까지 플래그처리로 추가로 스크롤제한
                CoroutineScope(Dispatchers.IO).launch {
                    delay(300)
                    withContext(Dispatchers.Main) {
                        binding.svMain.smoothScrollBy(
                            0, Utils.dpToPx(this@KaraokeActivity, 60F).toInt()
                        )
                    }
                }
            }
        }

        // 스크롤뷰 터치하면 스크롤 포커스 클리어
        binding.svMain.setOnTouchListener { v, event ->
            val action = event.action and MotionEvent.ACTION_MASK
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.etSearchSong.clearFocus()
                }
            }
            false
        }

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvSearchSong.apply {
            adapter = mrListAdapter
            addItemDecoration(BaseDividerItemDecoration(context, R.drawable.rv_line_divider))
        }
    }

    private fun setupViewModel() {
        repeatOnStarted {
            viewModel.uiState.collectLatest { state ->
                setLoading(state.isLoading)
                if (state.moveToListTop) {
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(100)
                        withContext(Dispatchers.Main) {
                            binding.svMain.smoothScrollTo(
                                0, clSearchTop
                            )
                        }
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.mrList.collectLatest { mrList ->
                mrListAdapter.submitList(mrList)
            }
        }
    }

    private fun setLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun searchMr(keyword: String) {
        viewModel.searchMr(keyword, 0, 100)
    }

    private fun searchMr(keyword: String, hideKeyboard: Boolean) {
        if (hideKeyboard) {
            Utils.hideKeyboard(this@KaraokeActivity, binding.etSearchSong)

            // 다시 키보드를 사용시 EditText가 보이도록 추가 스크롤을 해주기위해서 플레그설정
            isMoreScroll = true
        }
        viewModel.searchMr(keyword, 0, 100)
    }

    override fun AudioRecordingSingActivity() {
        // mrItem 값으로 Sing 호출

        // singType
        //SING_SOLO = 0;
        //SING_PART_A = 1;
        //SING_PART_B = 2;
        //SING_JOIN_A = 3;  // B Part에 join, A Part부름
        //SING_JOIN_B = 4;  // A Part에 join  B Part부름
        try {
            mrItem?.let { mrItem ->
                val requestObj = JSONObject()
                requestObj.put("mrId", mrItem.mr_id)
                requestObj.put("selectedKey", mrItem.original_key) // m, w
                requestObj.put("singType", 0)
                Log.d(TAG, "AudioSing Start : $requestObj")
                val intent = Intent(this, AudioSingActivity::class.java)
                intent.putExtra("SING_OPTION", requestObj.toString())
                audioSingStartForResult.launch(intent);
            }
        } catch (e: JSONException) {
            //
        }
    }

    companion object {
        const val RESULT_CODE_AUDIO_SING_FORCED_FINISH = 100
        const val RESULT_CODE_AUDIO_SING_RETRY = 101
        const val RESULT_CODE_AUDIO_SING_SCORE = 102
    }
}