package com.example.stpl.loyality_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stpl.loyality_ui.R;
import com.example.stpl.loyality_ui.bean.PacketData;

import java.util.ArrayList;

/**
 * Created by stpl on 20/3/18.
 */

public class loyalityStatementAdapter extends BaseAdapter {
    Context context;
       ArrayList<PacketData> packetData;
   private static LayoutInflater inflater;

    public loyalityStatementAdapter(Context context,ArrayList<PacketData> packetData) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.packetData =packetData;
    };

    @Override
    public int getCount() {
        return packetData.size();
    }

    @Override
    public Object getItem(int position) {
        return packetData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        convertView = inflater.inflate(R.layout.loyality_statement_item, null, false);

         TextView serial,loyality_points,expiry_date,monthly_period;

        long aed = packetData.get(position).getAccumulationEndDate();
        long asd =packetData.get(position).getAccumulationStartDate();
        long expiry =packetData.get(position).getExpiryDate();
        float totalEarning1 = packetData.get(position).getTotalEarning();
        String asdd = packetData.get(position).getAstart_date();
        String aedd = packetData.get(position).getAend_date();
        String exp = packetData.get(position).getExpiryString();



     serial=convertView.findViewById(R.id.serial);
     loyality_points=convertView.findViewById(R.id.loyality_points);
     expiry_date=convertView.findViewById(R.id.expiry_date);
     monthly_period=convertView.findViewById(R.id.monthly_period);


     loyality_points.getText();
        monthly_period.setText(asdd+" - Till date");

    int i =packetData.get(position).getSerialno();
     serial.setText(i+"");
     expiry_date.setText(exp+"");
     loyality_points.setText(totalEarning1+"");

       return convertView;
    }

    public int getItemViewType(int position) {
        return position;
    }
}
