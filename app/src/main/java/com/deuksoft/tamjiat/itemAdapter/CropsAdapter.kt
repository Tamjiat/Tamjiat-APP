package com.deuksoft.tamjiat.itemAdapter

import android.util.Log
import android.view.View
import android.widget.TextView
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropDetailDTO
import com.deuksoft.tamjiat.R
import com.zarinpal.libs.cardviwepager.BaseCardViewPagerItem
import java.text.SimpleDateFormat
import java.util.*

class CropsAdapter: BaseCardViewPagerItem<CropDetailDTO>() {
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun getLayout(): Int {
        return R.layout.crop_list_layout
    }

    override fun bindView(view: View?, item: CropDetailDTO) {
        Log.e("sdfdsfds", item.toString())
        view!!.apply {
            findViewById<TextView>(R.id.productName).text = item.cropsName
            findViewById<TextView>(R.id.cultivation_period_txt).text = "${CalcPeriod(item.cropsStart)}개월"
            findViewById<TextView>(R.id.categoryTxt).text = item.cropsCultivar
            findViewById<TextView>(R.id.locateTxt).text = item.locate
        }
    }

    fun CalcPeriod(cropsPeriod : String):String{
        var calendar = Calendar.getInstance()
        var currentMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH)
        calendar.time = dateFormat.parse(cropsPeriod)!!
        var targetMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH)

        return (currentMonth - targetMonth).toString()
    }
}