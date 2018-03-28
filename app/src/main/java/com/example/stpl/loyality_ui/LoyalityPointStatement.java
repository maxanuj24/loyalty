package com.example.stpl.loyality_ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stpl.loyality_ui.Adapter.ListAdapter;

import com.example.stpl.loyality_ui.Adapter.loyalityStatementAdapter;
import com.example.stpl.loyality_ui.bean.PacketData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoyalityPointStatement extends AppCompatActivity {

    ListView listView;
    ArrayList<PacketData> list;
    RequestQueue request;
    Button b;
    Dialog dialog;
    JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyality_point_statement);

        dialog = new ProgressDialog(this);
        list = new ArrayList<>();
    listView =findViewById(R.id.list);


        request = Volley.newRequestQueue(this);

       try {
            if (getIntent() != null)
                object = new JSONObject(getIntent().getStringExtra("JSON"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeJsonObjectRequest_LoyalPlayer(object);

    }

    private void makeJsonObjectRequest_LoyalPlayer(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ApiClient.BaseUrl+ApiClient.Loyal_Player_Detail, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("errorcode", ""+response.getInt("errorCode"));
                    if (response.getInt("errorCode") == 0) {
                        JSONArray packets = response.getJSONArray("packets");
                        for (int i = 0; i < packets.length(); i++) {
                            JSONObject packet = packets.getJSONObject(i);

                            PacketData packetData = new PacketData();
                            packetData.setAccumulationStartDate(packet.getLong("accumulationStartDate"));
                            packetData.setTotalEarning(Float.valueOf(packet.getString("totalEarning")));
                            packetData.setId(packet.getInt("id"));
                            packetData.setState(packet.getString("state"));
                            packetData.setSerialno(i+1);


                            if (packet.has("expiryDate")) {
                                packetData.setExpiryDate(packet.getLong("expiryDate"));
                            }
                            if (packet.has("accumulationEndDate"))
                                packetData.setAccumulationEndDate(packet.getLong("accumulationEndDate"));


                            list.add(packetData);

                            Log.e("abcc", list.get(i).getAccumulationEndDate() + "\t" + list.get(i).getState() + "\t" + list.get(i).getId() + "\t" + list.get(i).getTotalEarning() + "\t");

                        }

                        final loyalityStatementAdapter adapter = new loyalityStatementAdapter(LoyalityPointStatement.this, list);
                        listView.setAdapter(adapter);

                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoyalityPointStatement.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjReq);


    }
}
