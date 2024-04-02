package com.example.journalapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.journalapp.databinding.JournalRowBinding

class JournalRecyclerAdapter(val context: Context, var journalList:List<Journal>)
    : RecyclerView.Adapter<JournalRecyclerAdapter.MyViewHolder>()
{

    lateinit var binding: JournalRowBinding


    class MyViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(context)
                            .inflate(R.layout.journal_row,parent,false)

        return MyViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val journal : Journal = journalList.get(position)

        var imageUrl : String = ""

    }
}