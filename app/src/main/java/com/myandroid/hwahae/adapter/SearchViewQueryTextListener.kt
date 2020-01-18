package com.myandroid.hwahae.adapter

import androidx.appcompat.widget.SearchView
import com.myandroid.hwahae.MainActivity

class SearchViewQueryTextListener(var mActivity: MainActivity) : SearchView.OnQueryTextListener{
    override fun onQueryTextSubmit(query: String?): Boolean {

        mActivity.search = query
        mActivity.refreshRvItemList()

        mActivity.searchView.clearFocus()
        mActivity.searchView.onActionViewCollapsed()

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}