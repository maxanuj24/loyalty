package com.example.stpl.loyality_ui.aappii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.stpl.loyality_ui.R;
import com.example.stpl.loyality_ui.bean.ProductData;

import java.util.ArrayList;

/**
 * Created by stpl on 27/2/18.
 */

public class ListProductAdapter extends BaseAdapter{

    private static LayoutInflater inflater = null;
    ArrayList<ProductData> productData;
    Context context;

    public ListProductAdapter(Context context,ArrayList<ProductData> productData) {
        //
        this.productData = productData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productData.size();
    }

    @Override
    public Object getItem(int position) {
        return productData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R
                    .layout.list_row, null, false);

        TextView pid = (TextView) convertView.findViewById(R.id.a);
        TextView price = (TextView) convertView.findViewById(R.id.b);
        TextView quantity = (TextView) convertView.findViewById(R.id.c);
        TextView creator = (TextView) convertView.findViewById(R.id.d);
        TextView imgFilename = (TextView) convertView.findViewById(R.id.e);
        TextView displayName = (TextView) convertView.findViewById(R.id.f);

        int Pid = productData.get(position).getProductId();
        int Price =productData.get(position).getPrice();
        int Quantity =productData.get(position).getQuantity();

        String Creator = productData.get(position).getCreator();
        String Imgfilename = productData.get(position).getImageFilename();

        String Displayname=productData.get(position).getDisplayName();


       pid.setText("Product Id : "+Pid+"");
        price.setText("Price : "+Price+"");
        quantity.setText("Quantity : "+Quantity + "");
        creator.setText("Creator : "+Creator);
        imgFilename.setText("Image File Name : "+Imgfilename);
        displayName.setText("Display Name : "+Displayname);

        return convertView;
    }

}
