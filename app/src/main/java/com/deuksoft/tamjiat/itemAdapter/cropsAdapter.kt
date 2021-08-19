package com.deuksoft.tamjiat.itemAdapter

import android.view.View
import android.widget.TextView
import com.deuksoft.tamjiat.HTTPManager.DTOManager.testDTO
import com.deuksoft.tamjiat.R
import com.zarinpal.libs.cardviwepager.BaseCardViewPagerItem

class cropsAdapter: BaseCardViewPagerItem<testDTO>() {
    override fun getLayout(): Int {
        return R.layout.test_layout
    }

    override fun bindView(view: View?, item: testDTO?) {
        var textV = view!!.findViewById<TextView>(R.id.textV)
        textV.text = item!!.text
    }
}