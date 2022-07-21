package com.example.nycschools.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.nycschools.databinding.SchoolListItemBinding

import com.example.nycschools.Model.Schools

class SchoolsRecyclerViewAdapter(private val list: MutableList<Schools> = mutableListOf(),
                                 private val openDetails: (Schools) -> Unit) : RecyclerView.Adapter<SchoolsRecyclerViewAdapter.SchoolViewHolder>(){
    inner class SchoolViewHolder(private val binding: SchoolListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun onBind(school : Schools)
        {
            binding.tvSchoolName.text = school.schoolName
            binding.tvLocation.text = school.location
            binding.tvPhoneNumber.text = school.phoneNumber
            binding.tvDBN.text = school.dbn
            binding.tvExtrasActual.text = school.extracurricular_activities

            binding.card.setOnClickListener {
                openDetails(school)
            }
        }
    }

    fun setSchool(schoolList: List<Schools>)
    {
        list.clear()
        list.addAll(schoolList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder = SchoolViewHolder(
        SchoolListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
