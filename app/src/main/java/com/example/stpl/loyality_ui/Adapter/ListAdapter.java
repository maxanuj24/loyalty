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
 * Created by stpl on 26/2/18.
 */

public class ListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<PacketData> packageData;
    Context context;

    public ListAdapter(Context context,ArrayList<PacketData> packetData) {
        //
        this.packageData = packetData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return packageData.size();
    }

    @Override
    public Object getItem(int position) {
        return packageData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null, false);

        TextView id = (TextView) convertView.findViewById(R.id.a);
        TextView state = (TextView) convertView.findViewById(R.id.b);
        TextView accumulationStartDate = (TextView) convertView.findViewById(R.id.c);
        TextView totalEarning = (TextView) convertView.findViewById(R.id.d);
        TextView accumulationEndDate = (TextView) convertView.findViewById(R.id.e);
        TextView ExpiryDate = (TextView) convertView.findViewById(R.id.f);

        long aed = packageData.get(position).getAccumulationEndDate();
        long asd =packageData.get(position).getAccumulationStartDate();
        long expiry =packageData.get(position).getExpiryDate();

        String asdd = packageData.get(position).getAstart_date();
        String aedd = packageData.get(position).getAend_date();
        String exp =packageData.get(position).getExpiryString();

        int id1 = packageData.get(position).getId();
            float totalEarning1 = packageData.get(position).getTotalEarning();

        String states=packageData.get(position).getState();
            id.setText("Id : "+id1+"");
            state.setText("State : "+states);
           // accumulationStartDate.setText("Accumulation Start Date : "+asd + "");

        accumulationStartDate.setText(""+asdd);
       // accumulationEndDate.setText("Accumulation End Date : "+aed+"");
        accumulationEndDate.setText(aedd+"");
            totalEarning.setText("Total Earning : "+totalEarning1+"");
            ExpiryDate.setText("Expiry Date : "+exp+"");

        return convertView;
    }
}