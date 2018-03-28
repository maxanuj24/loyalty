package com.example.stpl.loyality_ui.aappii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stpl.loyality_ui.R;

import java.util.ArrayList;

/**
 * Created by stpl on 5/3/18.
 */

public class ListCurrentTierAdapter extends BaseAdapter {


  private static LayoutInflater inflater = null;
    ArrayList<Current_Tier> currentData;
    Context context;

    public ListCurrentTierAdapter(Context context,ArrayList<Current_Tier> currentData) {
        //
        this.currentData = currentData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return currentData.size();
    }

    @Override
    public Object getItem(int position) {
        return currentData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_main, null, false);

        TextView current = (TextView) convertView.findViewById(R.id.current_tier_text);
        //TextView state = (TextView) convertView.findViewById(R.id.b);
        TextView accumulationStartDate = (TextView) convertView.findViewById(R.id.c);
        TextView totalEarning = (TextView) convertView.findViewById(R.id.points);
        TextView accumulationEndDate = (TextView) convertView.findViewById(R.id.e);
        TextView ExpiryDate = (TextView) convertView.findViewById(R.id.f);

        int maintanancePoints=currentData.get(position).getMaintanancePoints();
        int tierBonusPercentage=currentData.get(position).getTierBonusPercentage();
        String displayName=currentData.get(position).getDisplayName();


    current.setText(displayName+"");
        //state.setText("Tier Bonus Percentage : "+tierBonusPercentage);
        //accumulationStartDate.setText("Display Name : "+displayName + "");
    totalEarning.setText(totalEarning+"");
        return convertView;
    }
}