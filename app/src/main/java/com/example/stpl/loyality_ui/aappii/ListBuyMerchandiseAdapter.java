package com.example.stpl.loyality_ui.aappii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.stpl.loyality_ui.R;
import com.example.stpl.loyality_ui.bean.BuyMerchandise;

import java.util.ArrayList;

/**
 * Created by stpl on 27/2/18.
 */

public class ListBuyMerchandiseAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<BuyMerchandise> merchandise;
    public ListBuyMerchandiseAdapter(Context context, ArrayList<BuyMerchandise> merchadise) {
    this.context=context;
    this.merchandise=merchadise;
    inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return merchandise.size();
    }

    @Override
    public Object getItem(int position) {
        return merchandise.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null, false);


        TextView code = (TextView) convertView.findViewById(R.id.a);
        TextView description = (TextView) convertView.findViewById(R.id.b);
        TextView message = (TextView) convertView.findViewById(R.id.c);

        int Code = merchandise.get(position).getCode();
        String Description =merchandise.get(position).getDescription();
        String Message =merchandise.get(position).getMessage();

        code.setText("Code : "+Code+"");
        description.setText("Description : "+Description+"");
        message.setText("Message : "+Message + "");

        return convertView;
    }
}
