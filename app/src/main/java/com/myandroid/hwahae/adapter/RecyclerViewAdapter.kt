package com.myandroid.hwahae.adapter

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myandroid.hwahae.DetailActivity
import com.myandroid.hwahae.MainActivity
import com.myandroid.hwahae.R
import com.myandroid.hwahae.model.RvItem
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.rv_item.view.*
import java.text.NumberFormat
import java.util.*

class RecyclerViewAdapter(private val mActivity: MainActivity) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false))
    }

    override fun getItemCount(): Int {
        return mActivity.showRvItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mActivity.showRvItem[position]
            val listener = View.OnClickListener{
            val intent = Intent(mActivity, DetailActivity::class.java)
            intent.putExtra("detailItem",mActivity.detailItem[item.id])
            startActivity(mActivity, intent, null)
        }

        holder.apply {
            setSize(mActivity.iv_width, mActivity.iv_height)
            bind(listener, item)
        }

    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){

        val nFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)
        private var view: View = v

        fun bind(listener: View.OnClickListener, item: RvItem){

            Glide.with(view)
                .load(item.thumbnail_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view.rvitem_ivthumbnail)

            view.rvitem_title.text = item.title
            view.rvitem_tvprice.text = nFormat.format(item.price)
            view.setOnClickListener(listener)
        }

        fun setSize(w: Int, h:Int ){
            view.rvitem_ivthumbnail.layoutParams.width = (w * 0.9).toInt()
            view.rvitem_ivthumbnail.layoutParams.height = (h * 0.9).toInt()
            view.rvitem_layout.layoutParams.width = w
            view.rvitem_layout.layoutParams.height = (h * 1.3).toInt()
            view.rvitem_cdview.layoutParams.width = (w * 0.9).toInt()
            view.rvitem_cdview.layoutParams.height = (h * 0.9).toInt()
            view.rvitem_title.layoutParams.width = (w * 0.9).toInt()
        }
    }
}