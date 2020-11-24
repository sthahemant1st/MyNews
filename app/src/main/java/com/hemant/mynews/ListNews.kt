package com.hemant.mynews


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.flaviofaria.kenburnsview.KenBurnsView
import com.github.florent37.diagonallayout.DiagonalLayout
import com.hemant.mynews.Adapter.ListNewsAdapter
import com.hemant.mynews.Common.Common
import com.hemant.mynews.Interface.NewsService
import com.hemant.mynews.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source = ""

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter : ListNewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var mService: NewsService
    lateinit var topImage: KenBurnsView
    lateinit var topTitle: TextView
    lateinit var topAuthor: TextView
    lateinit var webUrl : String
    lateinit var diagonalLayout :DiagonalLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_news)
        recyclerView =findViewById(R.id.list_news)
        layoutManager = LinearLayoutManager(this)
        topImage = findViewById(R.id.top_image)
        topTitle = findViewById(R.id.top_title)
        topAuthor = findViewById(R.id.top_author)
        diagonalLayout = findViewById(R.id.diagonalLayout)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager



        if (intent != null){
            source = intent.getStringExtra("source")!!
            if (!source.isEmpty()){
                loadNews(source,false)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(source, true)
        }
        diagonalLayout.setOnClickListener{

            var intent : Intent = Intent(this,NewsDetalis::class.java)
            intent.putExtra("url",webUrl)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

    }
    private fun loadNews(source : String?,isRefreshed: Boolean){
        if (isRefreshed){
            Toast.makeText(baseContext,"refreshed",Toast.LENGTH_LONG).show()
            mService.getNewsFormSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News>{
                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipeRefreshLayout.isRefreshing=false
                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(topImage)
                        topTitle.text = response.body()!!.articles!![0].title
                        topAuthor.text = response.body()!!.articles!![0].author

                        webUrl = response.body()!!.articles!![0].url!!

                        var removeFirstItem = response.body()!!.articles
                        removeFirstItem?.removeAt(0)

                        adapter = ListNewsAdapter(baseContext, removeFirstItem!!)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter

                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        Toast.makeText(this@ListNews,"failed id news retrival", Toast.LENGTH_LONG).show()
                    }

                })

        }else{
            swipeRefreshLayout.isRefreshing=true
            mService.getNewsFormSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News>{
                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipeRefreshLayout.isRefreshing=false
                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(topImage)
                        topTitle.text = response.body()!!.articles!![0].title
                        topAuthor.text = response.body()!!.articles!![0].author

                        webUrl = response.body()!!.articles!![0].url!!

                        var removeFirstItem = response.body()!!.articles
                        removeFirstItem?.removeAt(0)

                        adapter = ListNewsAdapter(baseContext, removeFirstItem!!)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter

                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        Toast.makeText(this@ListNews,"failed id news retrival", Toast.LENGTH_LONG).show()
                    }

                })


        }

    }

}