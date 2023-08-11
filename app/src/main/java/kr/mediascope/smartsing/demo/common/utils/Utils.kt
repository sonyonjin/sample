package kr.mediascope.smartsing.demo.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kr.mediascope.smartsing.demo.R

class Utils {
    companion object {
        // 키보드 닫기
        fun hideKeyboard(context: Context, et: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(et.windowToken, 0)
        }

        // dp to pixel값
        fun dpToPx(context: Context, dp: Float): Float {
            val metrics = context.resources.displayMetrics
            return dp * metrics.density
        }

        // 점수를 숫자그림 형식으로 출력할때 사용
        fun scoreToNumImg(context: Context, score: Int, digit: Int): Int {
            if (!(score > 100 || digit == 100 || digit == 10 || digit == 1)) return 0

            return when (digit) {
                100 -> {
                    if (score > 100) return R.drawable.img_num_1 else 0
                }
                10 -> {
                    val devide = score / 10
                    getNumberImgResourceId(devide)
                }
                1 -> {
                    val remain = score % 10
                    getNumberImgResourceId(remain)
                }
                else -> {
                    0
                }
            }
        }

        fun getNumberImgResourceId(num: Int): Int {
            if (num > 10) return 0

            return when(num){
                0 -> {
                    R.drawable.img_num_0
                }
                1 -> {
                    R.drawable.img_num_1
                }
                2 -> {
                    R.drawable.img_num_2
                }
                3 -> {
                    R.drawable.img_num_3
                }
                4 -> {
                    R.drawable.img_num_4
                }
                5 -> {
                    R.drawable.img_num_5
                }
                6 -> {
                    R.drawable.img_num_6
                }
                7 -> {
                    R.drawable.img_num_7
                }
                8 -> {
                    R.drawable.img_num_8
                }
                9 -> {
                    R.drawable.img_num_9
                }
                else -> {
                    R.drawable.img_num_0
                }
            }
        }
    }
}