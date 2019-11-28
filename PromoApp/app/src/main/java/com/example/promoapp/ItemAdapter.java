package com.example.promoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item = (Item) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imageView);
        TextView title = (TextView) view.findViewById(R.id.txtTitle);
        TextView detail = (TextView) view.findViewById(R.id.txtDes);
        TextView vigencia = (TextView) view.findViewById(R.id.txtVigencia);

        imgFoto.setImageResource(item.getImgPromo());
        title.setText(item.getsTiltle());
        detail.setText(item.getsDetails());
        vigencia.setText(item.getsVigencia());

        return view;
    }
}
