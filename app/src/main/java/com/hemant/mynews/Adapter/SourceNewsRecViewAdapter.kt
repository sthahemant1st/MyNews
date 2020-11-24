package com.hemant.mynews.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hemant.mynews.ListNews
import com.hemant.mynews.Model.Website
import com.hemant.mynews.R

class SourceNewsRecViewAdapter(private val context : Context, private val websites : Website) : RecyclerView.Adapter<SourceNewsRecViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view : View =LayoutInflater.from(parent.context)
                                .inflate(R.layout.source_news_layout, parent, false)
        var holder : ViewHolder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNewsHeading.text = websites.sources!![position].name
        holder.cardView.setOnClickListener{
            Toast.makeText(context,"Click is working", Toast.LENGTH_SHORT).show()
            var intent : Intent = Intent(context,ListNews::class.java)
            intent.putExtra("source", websites.sources!![position].id)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return websites.sources!!.size
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var imgNewsImage : ImageView
        lateinit var txtNewsHeading : TextView
        lateinit var cardView : CardView

        init {
            initializeViews()
        }

        //initialize all views to be shown in source news
        private fun initializeViews() {
            imgNewsImage = itemView.findViewById(R.id.news_image)
            txtNewsHeading = itemView.findViewById(R.id.news_heading)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }


}