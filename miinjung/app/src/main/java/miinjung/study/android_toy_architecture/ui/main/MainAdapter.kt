package miinjung.study.android_toy_architecture.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miinjung.study.android_toy_architecture.R
import miinjung.study.android_toy_architecture.data.model.RepoItem

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    private var dataList = arrayListOf<RepoItem>()
    private lateinit var clickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val convertView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main, parent, false)
        return MainViewHolder(convertView)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.let{
            dataList[position].let{data ->
                it.fullName.text = data.fullName
                if(data.language == null)
                    it.language.setText(R.string.no_language)
                else
                    it.language.text = data.language
                Glide.with(it.itemView.context).load(data.owner.avatarUrl).into(it.userImage)

                it.itemBox.setOnClickListener {
                    if (position != RecyclerView.NO_POSITION)
                        clickListener.onItemClick(data.owner.login,data.name)
                }
            }
        }
    }
    override fun getItemCount(): Int = dataList.size

    fun getData(dataList  : ArrayList<RepoItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(login : String,name : String)
    }

    class MainViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fullName : TextView = view.findViewById(R.id.tv_fragment_main_full_name) as TextView
        val language : TextView = view.findViewById(R.id.tv_fragment_main_language) as TextView
        val userImage : ImageView = view.findViewById(R.id.iv_fragment_main_user) as ImageView
        val itemBox : LinearLayout = view.findViewById(R.id.mainItemBox)
    }
}