package com.hemant.mynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.hemant.mynews.Adapter.SourceNewsRecViewAdapter
import com.hemant.mynews.Common.Common
import com.hemant.mynews.Interface.NewsService
import com.hemant.mynews.Model.Website
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mService: NewsService

    lateinit var layoutManager: LinearLayoutManager
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView

    lateinit var adapter : SourceNewsRecViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init cache db
        Paper.init(this)

        //init service
        mService = Common.newsService

        //init view
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            loadWebsiteSource(true)
        }
        recyclerView = findViewById(R.id.recycler_view_main)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        loadWebsiteSource(false)
    }

    private fun loadWebsiteSource(isRefresh: Boolean) {

        if (!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null"){
                //Read cache
                val website = Gson().fromJson<Website>(cache, Website::class.java)
                adapter= SourceNewsRecViewAdapter(baseContext,website)
                adapter.notifyDataSetChanged()

                recyclerView.adapter = adapter
            }else{
                //load website and write cache
                mService.sources.enqueue(object : retrofit2.Callback<Website>{
                    override fun onResponse(call: Call<Website>, response: Response<Website>) {
                        adapter = SourceNewsRecViewAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter
                        //save to cache
                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))

                    }

                    override fun onFailure(call: Call<Website>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed in service",Toast.LENGTH_SHORT).show()}
                })
            }
        }
        else{
            mService.sources.enqueue(object : retrofit2.Callback<Website>{
                override fun onResponse(call: Call<Website>, response: Response<Website>) {
                    adapter = SourceNewsRecViewAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                    //save to cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                    swipeRefreshLayout.isRefreshing=false

                }

                override fun onFailure(call: Call<Website>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed in service",Toast.LENGTH_SHORT).show()}
            })

        }
    }
}