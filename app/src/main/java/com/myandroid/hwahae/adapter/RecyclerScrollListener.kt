package com.myandroid.hwahae.adapter

import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myandroid.hwahae.MainActivity
import com.myandroid.hwahae.model.RvItem

class RecyclerScrollListener(var mActivity: MainActivity) : RecyclerView.OnScrollListener(){

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastVisibleItemPosition = mActivity.layoutManager.findLastCompletelyVisibleItemPosition()
        val itemTotalCount = mActivity.layoutManager.itemCount

        if(!mActivity.isLoading && lastVisibleItemPosition == itemTotalCount-1){
            mActivity.loadMore()
        }
    }
}