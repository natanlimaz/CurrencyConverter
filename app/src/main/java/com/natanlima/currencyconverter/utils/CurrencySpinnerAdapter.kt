package com.natanlima.currencyconverter.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.natanlima.currencyconverter.R
import com.natanlima.currencyconverter.model.Currency

class CurrencySpinnerAdapter(private val context: Context, private val currencies: List<Currency>) : BaseAdapter() {
    override fun getCount(): Int {
        return currencies.size;
    }

    override fun getItem(p0: Int): Any {
        return currencies[p0];
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = p1 ?:LayoutInflater.from(context).inflate(R.layout.spinner_item, p2, false);

        bindView(view, currencies[p0]);
        return view;
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        bindView(view, currencies[position]);
        return view;
    }

    fun bindView(view: View, currency: Currency) {
        val ivCurrency = view.findViewById<ImageView>(R.id.iv_currency_image);
        val tvCurrencyIso = view.findViewById<TextView>(R.id.tv_currency_iso);

        ivCurrency.setImageResource(currency.image);
        tvCurrencyIso.text = currency.currency;
    }

}