package com.deuksoft.tamjiat.itemAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropWeekDTO
import com.deuksoft.tamjiat.databinding.MainCultivatedItemBinding

class CropWeekAdapter(var context: Context, var cropList: List<CropWeekDTO>, var itemClick: (CropWeekDTO)->Unit):RecyclerView.Adapter<CropWeekAdapter.CropsViewHolder>() {

    inner class CropsViewHolder(mainCultivatedItemBinding: MainCultivatedItemBinding, itemClick: (CropWeekDTO) -> Unit):RecyclerView.ViewHolder(mainCultivatedItemBinding.root){
        var cropNameItem = mainCultivatedItemBinding.cropNameItem
        var passMonth = mainCultivatedItemBinding.passMonth

        fun bind(cropWeekDTO: CropWeekDTO){
            cropNameItem.text = cropWeekDTO.cropsName
            passMonth.text = "${cropWeekDTO.month}주"
            itemView.setOnClickListener{itemClick(cropWeekDTO)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropWeekAdapter.CropsViewHolder {
        var mainCultivatedItemBinding = MainCultivatedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CropsViewHolder(mainCultivatedItemBinding, itemClick)
    }

    override fun onBindViewHolder(holder: CropWeekAdapter.CropsViewHolder, position: Int) {
        holder.bind(cropList[position])
    }

    override fun getItemCount(): Int {
        return cropList.size
    }
}