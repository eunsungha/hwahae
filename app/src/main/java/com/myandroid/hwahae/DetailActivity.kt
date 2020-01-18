package com.myandroid.hwahae

import android.content.Intent
import android.graphics.Outline
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myandroid.hwahae.model.DetailItem
import kotlinx.android.synthetic.main.rv_item.view.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var closeBtn: FloatingActionButton

    lateinit var iv_image: ImageView
    lateinit var tv_title: TextView
    lateinit var tv_price:TextView
    lateinit var tv_description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        iv_image = findViewById(R.id.iv_image)
        tv_title = findViewById(R.id.tv_title)
        tv_price = findViewById(R.id.tv_price)
        tv_description = findViewById(R.id.tv_description)
        closeBtn = findViewById(R.id.closeBtn)

        val item = intent.getParcelableExtra<DetailItem>("detailItem")!!

        Glide.with(this)
            .load(item.full_size_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_image)

        val nFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)

        tv_title.text = item.title
        tv_price.text = nFormat.format(item.price) + "원"
        tv_description.text = item.description.replace("\\n","\n")

        closeBtn.setOnClickListener{
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
