package com.example.stpl.loyality_ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.stpl.loyality_ui.aappii.ApiUiPrev;
import com.example.stpl.loyality_ui.bean.ProductData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button api_hit;
    Button redeemNow;
    ProgressDialog dialog;
    RequestQueue request;
    ListView listView;
    Spinner quantity_spin;

    ProgressBar loyality_progress;
    private float progress = 0;

    Button loyality_statement;

    TextView next_tier_text;
    TextView currentTier_text, points, currentTier_text1;
    ;
    TextView requireStatemant_text;
    ArrayList<ProductData> list_product;

    private int packet_id;
    private int user_id;
    private String state;
    private String reqire_statemant;
    private int curr_points;
    private String current_tier;
    private String next_tier;
    private int remaining_points_to_next_tier;
    int entry_points_next_tier, currentEarning;
    String accumulation_start_date;
    EditText id;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init() {
        idd = 0;
        quantity_spin = findViewById(R.id.quantity_spin);
        currentTier_text = findViewById(R.id.current_tier_text);
        next_tier_text = findViewById(R.id.next_tier_text);
        points = findViewById(R.id.points);
        dialog = new ProgressDialog(this);
        api_hit = findViewById(R.id.api_hit);
        redeemNow = findViewById(R.id.redeemNow);
        redeemNow.setOnClickListener(this);
        api_hit.setOnClickListener(this);
        loyality_progress = findViewById(R.id.loyality_progress);
        request = Volley.newRequestQueue(this);
        requireStatemant_text = findViewById(R.id.reqire_statemant);
        currentTier_text1 = findViewById(R.id.current_tier_text1);
        loyality_statement = findViewById(R.id.loyality_statement);
        loyality_statement.setOnClickListener(this);
        id = findViewById(R.id.id);
        loyality_progress.setProgress((int) progress);

    }

    @Override
    protected void onResume() {
        super.onResume();
        idd = 602;
        if (!id.getText().toString().equals(""))
            idd = Integer.parseInt(id.getText().toString());
        JSONObject object = new JSONObject();
        try {
            object.put("playerId", idd);
            object.put("size", ApiClient.size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeJsonObjectRequest_CurrentTier(object);



    }

    @Override
    public void onClick(View v) {

        int api_id = v.getId();
        switch (api_id) {
            case R.id.redeemNow:
                idd = 602;
                if (!id.getText().toString().equals(""))
                    idd = Integer.parseInt(id.getText().toString());
                JSONObject object = new JSONObject();
                try {
                   // object.put("domainName", "dev.khelplayrummy.com");
                    object.put("domainName", ApiClient.domainName);
                    object.put("playerId", idd);
                    object.put("playerToken",ApiClient.playerToken);
                            //"e101c124ad1961b619a6b17d72c46c76");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Redeem_products rp = new Redeem_products();
                Intent i = new Intent(MainActivity.this, Redeem_products.class);
                i.putExtra("JSON", object.toString());
                startActivity(i);
                break;

          /*  case R.id.api_hit:
                Intent i1 = new Intent(this, ApiUiPrev.class);
                startActivity(i1);
                break;*/
            case R.id.loyality_statement:
                idd = 608;
                if (!id.getText().toString().equals(""))
                    idd = Integer.parseInt(id.getText().toString());
                JSONObject object1 = new JSONObject();
                try {
                    object1.put("playerId", idd);
                    //object1.put("playerId", ApiClient.playerId);
                    object1.put("size", ApiClient.size);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i2 = new Intent(MainActivity.this, LoyalityPointStatement.class);
                i2.putExtra("JSON", object1.toString());
                startActivity(i2);
                break;


        }


    }

    private void makeJsonObjectRequest_CurrentTier(JSONObject object) {

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Loyal_Player_Detail, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.e("ERR", "onResponse: " + response.getInt("errorCode"));

                    if (response.getInt("errorCode") == 0) {
                        Log.e("Response : ", response.toString());
                        JSONObject tier = response.getJSONObject("playerInfo");
                        JSONObject tier1 = tier.getJSONObject("currentTier");
                        JSONObject tier2 = tier.getJSONObject("nextTier");
                        JSONArray tier3 = response.getJSONArray("packets");
                        points.setText("Points :- " + tier.getInt("currentTierEarning") + "");
                        currentEarning = tier.getInt("currentTierEarning");
                        currentTier_text.setText(tier1.getString("displayName").toUpperCase() + "");
                        current_tier = tier1.getString("displayName");

                        next_tier = tier2.getString("displayName");
                        entry_points_next_tier = tier2.getInt("entryPoints");

                        dialog.dismiss();
                        remaining_points_to_next_tier = entry_points_next_tier - currentEarning;
                        reqire_statemant = "You just need " + remaining_points_to_next_tier + " to unlock to " + next_tier + " club.";
                        requireStatemant_text.setText(reqire_statemant);

                        next_tier = next_tier.toUpperCase();
                        current_tier = current_tier.toUpperCase();
                        next_tier_text.setText(next_tier + " CLUB");
                        currentTier_text1.setText(current_tier + " CLUB");

                        progress = (currentEarning / entry_points_next_tier) * 100;
                    } else {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jor);
    }


}
