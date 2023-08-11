package kr.mediascope.smartsing.demo

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONException
import org.json.JSONObject

open class BaseActivity : AppCompatActivity() {

    protected open fun init() {}
    protected open fun initView() {}
    protected open fun setListener() {}

    private var selectedMR = 0

    // 버튼마다 Tag 에 mrId값이 있음.
    fun selectMR(v: View) {
        val mrId = v.tag.toString().toInt()
        Log.d(TAG, "SELECT MR : $mrId")
        if (mrId > 0) {
            selectedMR = mrId
            checkPermission()
        }
    }

    // 권한 체크
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.WAKE_LOCK
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ), 1001
            )
        } else {
            Log.d(TAG, "모든 권한 이미 가지고 있음")
            AudioRecordingSingActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isAllChecked = true
        if (requestCode == 1001) {
            for (ii in grantResults.indices) {
                if (grantResults[ii] == PackageManager.PERMISSION_DENIED) {
                    Log.d(TAG, ">>>" + permissions[ii])

                    // Android 13 (33) WRITE_EXTERNAL_STORAGE 변경, 여기서는 Demo이므로 편의상 무시
                    if (permissions[ii] != "android.permission.WRITE_EXTERNAL_STORAGE") {
                        isAllChecked = false
                        break
                    }
                }
            }

            // 노래방 실행을 위한 권한 확인 완료
            if (isAllChecked) {
                AudioRecordingSingActivity()
            } else {
                MaterialAlertDialogBuilder(this).setCancelable(false) // 대화창 외부 영역 클릭시 창 닫히지 않도록 설정
                    .setTitle("").setMessage("노래방 서비스이용을 위해 필요한 권한을 모두 허용해 주시기 바랍니다.")
                    .setNegativeButton("거부",
                        DialogInterface.OnClickListener { dialogInterface, ii -> //취소
                            return@OnClickListener
                        }).setPositiveButton("허용") { dialogInterface, ii -> // 권한설정을 위한 설정 이동
                        checkPermission()
                    }.show()
            }
        }
    }

    open fun AudioRecordingSingActivity() {
        // selectedMR 값으로 Sing 호출

        // singType
        //SING_SOLO = 0;
        //SING_PART_A = 1;
        //SING_PART_B = 2;
        //SING_JOIN_A = 3;  // B Part에 join, A Part부름
        //SING_JOIN_B = 4;  // A Part에 join  B Part부름
        try {
            val requestObj = JSONObject()
            requestObj.put("mrId", selectedMR)
            requestObj.put("selectedKey", "m") // m, w
            requestObj.put("singType", 0)
            Log.d(TAG, "AudioSing Start : $requestObj")
            val intent = Intent(this, AudioSingActivity::class.java)
            intent.putExtra("SING_OPTION", requestObj.toString())
            this.startActivity(intent)
        } catch (e: JSONException) {
            //
        }
    }

    companion object {
        const val TAG = "SINGIT"
    }
}