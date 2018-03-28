package com.example.stpl.loyality_ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.stpl.loyality_ui.R;
import com.example.stpl.loyality_ui.RecyclerViewListener;
import com.example.stpl.loyality_ui.bean.ProductData;

import java.util.ArrayList;

/**
 * Created by stpl on 5/3/18.
 */

public class List_RedeemProduct extends RecyclerView.Adapter<List_RedeemProduct.MyViewHolder> implements CompoundButton.OnCheckedChangeListener{


    private LayoutInflater inflater;
    private ArrayList<ProductData> list_product;
    Integer[] qty;
    int Quantity;
    int Code;
    String Description,Message,ResponseMessage;
    private RecyclerViewListener listener;


    public List_RedeemProduct(Context context, ArrayList<ProductData> list_product, RecyclerViewListener listener) {
        this.list_product = list_product;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_redeemable_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.d("log", "List position : " + position);
        Log.d("log", "Selected values : " + list_product.get(position).getQuantity());

        final ProductData l = list_product.get(position);

        Quantity = list_product.get(position).getQuantity();
        holder.text1.setText(l.getDisplayName() + "");
        holder.text2.setText("Price :- "+l.getPrice() + "");
        ResponseMessage = l.getRespMsg();
        Message =l.getMessage();
        Description =l.getDescription();
        Code =l.getCode();

        final Integer[] qty = setQty(Quantity);

        ArrayAdapter<Integer> aa = new ArrayAdapter<Integer>(holder.quantity_spin.getContext(), android.R.layout.simple_spinner_item, qty);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantity_spin.setAdapter(aa);


        holder.quantity_spin.setSelection(aa.getPosition((Integer) list_product.get(position).getSelected_qty()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onItemClick(l);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_product.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1, text2, imgFilename;
        public Spinner quantity_spin;
        //public CheckBox checkBox;

        public RelativeLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.name);
            text2 = itemView.findViewById(R.id.price);
            quantity_spin = itemView.findViewById(R.id.quantity_spin);
            imgFilename = itemView.findViewById(R.id.e);
          //  checkBox = itemView.findViewById(R.id.checkBox);
            parent = itemView.findViewById(R.id.parent);

/*
            TextView code = (TextView) itemView.findViewById(R.id.code_buyProducts);
            TextView description = (TextView)itemView.findViewById(R.id.description_buyProducts);
            TextView message = (TextView) itemView.findViewById(R.id.message_buyProducts);
            TextView respMessage=(TextView)itemView.findViewById(R.id.respMsg_buyProducts);


            code.setText("Code : "+Code+"");
            description.setText("Description : "+Description+"");
            message.setText("Message : "+Message + "");
            respMessage.setText("Reaponse Message : "+ ResponseMessage+"");

*/



            //checkBox.setClickable(false);
            quantity_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("log","getAdapterPosition() : "+getAdapterPosition());
                    Log.d("log","Value : "+(Integer) parent.getItemAtPosition(position));
                    list_product.get(getAdapterPosition()).setSelected_qty((Integer) parent.getItemAtPosition(position));


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
      }



    }


    private Integer[] setQty(int maxValue) {
        qty = new Integer[maxValue+1];
        for (int i = 0; i <= maxValue; i++) {
            qty[i] = i;
        }
        return qty;
    }

}







