package com.hemant.mynews.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.diagonallayout.DiagonalLayout
import com.hemant.mynews.Model.Article
import com.hemant.mynews.NewsDetalis
import com.hemant.mynews.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ListNewsAdapter(private val context : Context, private val articles : List<Article>): RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        //load image
        Picasso.get().load(articles[position].urlToImage).into(holder.articleImage)
        if (articles[position].title!!.length > 60){
            holder.articleTitle.text = articles[position].title!!.substring(0,60)
        }else{
            holder.articleTitle.text = articles[position].title
        }

        holder.articleTime.text = articles[position].publishedAt

        holder.newsCardView.setOnClickListener{

            var intent : Intent = Intent(context,NewsDetalis::class.java)
            intent.putExtra("url",articles[position].url)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return articles.size
    }
    class ListNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var articleTime : TextView = itemView.findViewById(R.id.article_time)
        var articleImage : CircleImageView = itemView.findViewById(R.id.article_image)
        var articleTitle : TextView = itemView.findViewById(R.id.article_title)
        var newsCardView : CardView = itemView.findViewById(R.id.news_card_view)


    }
}