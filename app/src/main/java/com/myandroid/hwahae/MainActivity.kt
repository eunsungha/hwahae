package com.myandroid.hwahae

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myandroid.hwahae.adapter.*
import com.myandroid.hwahae.model.DetailItem
import com.myandroid.hwahae.model.RvItem
import com.myandroid.hwahae.network.NetworkClient
import com.myandroid.hwahae.network.NetworkInterface
import com.myandroid.hwahae.network.RepoDetail
import com.myandroid.hwahae.network.RepoPaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var storeItem:ArrayList<RvItem>        // 상품목록에 보여지기전에 아이템을 담아둘 리스트
    lateinit var showRvItem:ArrayList<RvItem>       // 상품목록에 보여질 아이템 리스트
    lateinit var detailItem:HashMap<Int,DetailItem> // id 에 따른 상품 상세화면 정보를 담아둘 해시맵

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var pb_bar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerScrollListener:RecyclerScrollListener
    lateinit var btn_reset: Button
    lateinit var client: NetworkInterface
    lateinit var searchView: SearchView

    var skin_type: String? = null
    var page:Int = 1
    var search: String? = null

    var iv_width = 0
    var iv_height = 0

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))

        storeItem = ArrayList()
        showRvItem = ArrayList()

        detailItem = HashMap()

        recyclerViewAdapter = RecyclerViewAdapter(this)
        pb_bar = findViewById(R.id.pb_bar)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        layoutManager  = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = recyclerViewAdapter
        recyclerScrollListener = RecyclerScrollListener(this)
        recyclerView.addOnScrollListener(recyclerScrollListener)

        client = NetworkClient.getClient()
            .create(NetworkInterface::class.java)

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = SpinnerItemSelectedListener(this)

        searchView = findViewById<SearchView>(R.id.sv_keyword)
        searchView.setOnQueryTextListener(SearchViewQueryTextListener(this))
        searchView.setOnClickListener {
            searchView.onActionViewExpanded()
        }

        searchView.setOnCloseListener {
            false
        }

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)

        iv_width = displaymetrics.widthPixels / 2
        iv_height = displaymetrics.heightPixels / 3

        recyclerView.layoutParams.height = (displaymetrics.heightPixels * 0.8).toInt()

        btn_reset = findViewById(R.id.btn_reset)
        btn_reset.setOnClickListener{
            skin_type = null
            search = null
            refreshRvItemList()
            spinner.setSelection(0)
            searchView.setQuery("",false)
            searchView.clearFocus()
        }
    }

    /*
        상품목록 조회
     */
    private fun getProductList(skin_type: String?, page: Int?, search: String?){

        client.paging(skin_type, page, search).enqueue(object : Callback<RepoPaging> {
            override fun onFailure(call: Call<RepoPaging>, t: Throwable) {

            }

            override fun onResponse(call: Call<RepoPaging>, response: Response<RepoPaging>) {
                if(response.isSuccessful){
                    val products = response.body()?.body
                    for(product in products!!){
                        storeItem.add(
                            RvItem(
                                product.id,
                                product.thumbnail_image,
                                product.title,
                                product.price,
                                product.oily_score,
                                product.dry_score,
                                product.sensitive_score
                            )
                        )

                    }
                    updateRvItem()
                }
            }
        })
    }

    /*
        상품목록 갱신
        아이템 상세정보 추가
     */
    fun updateRvItem(){
        showRvItem = storeItem
        recyclerViewAdapter.notifyDataSetChanged()

        for(item in storeItem){
            addDetailItem(item.id)
        }
    }

    /*
        검색이나 필터 변경시 제품 리스트 초기화
     */
    fun refreshRvItemList(){
        recyclerView.scrollToPosition(0)
        isLoading = true

        storeItem = ArrayList()
        page = 1
        getProductList(skin_type, page, search)
        updateRvItem()

        Handler().postDelayed({
            isLoading = false
        },5000)
    }

    /*
        스크롤이 마지막 아이템 도달시 상품목록 갱신
     */
    fun loadMore(){
        pb_bar.visibility = View.VISIBLE
        isLoading = true

        Handler().postDelayed({
            page++
            getProductList(skin_type, page, search)
            isLoading = false
            pb_bar.visibility = View.INVISIBLE
        },5000)
    }

    /*
        id에 해당하는 제품 상세정보 조회
     */
    private fun addDetailItem(id: Int){
        client.detail(id).enqueue(object : Callback<RepoDetail>{
            override fun onFailure(call: Call<RepoDetail>, t: Throwable) {

            }

            override fun onResponse(call: Call<RepoDetail>, response: Response<RepoDetail>) {
                val detail = response.body()?.body!!
                if(!detailItem.containsKey(id)){
                    detailItem[id]=
                        DetailItem(
                            detail.id,
                            detail.full_size_image,
                            detail.title,
                            detail.description,
                            detail.price,
                            detail.oily_score,
                            detail.dry_score,
                            detail.sensitive_score
                        )
                }
            }
        })
    }
}
