package com.myandroid.hwahae.adapter

import android.os.Handler
import android.view.View
import android.widget.AdapterView
import com.myandroid.hwahae.MainActivity
import com.myandroid.hwahae.SkinType
import kotlinx.android.synthetic.main.activity_main.*

class SpinnerItemSelectedListener(val mActivity: MainActivity) : AdapterView.OnItemSelectedListener{
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            2 -> {
                // 건성
                mActivity.skin_type = SkinType.DRY.type
            }
            3 -> {
                // 민감성
                mActivity.skin_type = SkinType.SENSITIVE.type
            }
            else -> {
                // 지성
                mActivity.skin_type = SkinType.OILY.type
            }
        }
        mActivity.search = null
        mActivity.refreshRvItemList()
    }
}