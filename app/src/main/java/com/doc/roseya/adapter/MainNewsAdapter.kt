//package com.doc.roseya.adapter
//
//import android.content.Context
//import android.content.Intent
//import android.text.method.TextKeyListener.clear
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.cardview.widget.CardView
//import androidx.recyclerview.widget.RecyclerView
//import com.doc.roseya.R
//import com.doc.roseya.model.ModelNews
//import com.doc.roseya.ui.alquran.AlquranActivity
//import kotlinx.android.synthetic.main.list_item_news.view.*
//import java.util.*
//
//
//class MainNewsAdapter(private val mContext: Context) : RecyclerView.Adapter<MainNewsAdapter.ViewHolder>() {
//    private val modelNewsList = ArrayList<ModelNews>()
//
//    fun setAdapter(items: ArrayList<ModelNews>) {
//        modelNewsList.clear()
//        modelNewsList.addAll(items)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = modelNewsList[position]
//
//        holder.tvauthor.text = data.author
//        holder.tvtitle.text = data.title
//        holder.tvurlToImage.text = data.urlToImage
//        holder.tvpublishedAt.text = data.publishedAt
//
//        holder.cvnews.setOnClickListener {
////            val intent = Intent(mContext, AlquranActivity::class.java)
////            intent.putExtra(AlquranActivity.DETAIL_SURAH, modelNewsList[position])
////            mContext.startActivity(intent)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return modelNewsList.size
//    }
//
//    //Class Holder
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var cvnews: CardView
//        var tvauthor: TextView
//        var tvtitle: TextView
//        var tvurlToImage: TextView
//        var tvpublishedAt: TextView
//
//        init {
//            cvnews = itemView.cvnews
//            tvauthor = itemView.tvauthor
//            tvtitle = itemView.tvtitle
//            tvurlToImage = itemView.tvurlToImage
//            tvpublishedAt = itemView.tvpublishedAt
//        }
//    }
//}