package com.example.stpl.loyality_ui.aappii;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stpl.loyality_ui.Adapter.ListAdapter;
import com.example.stpl.loyality_ui.ApiClient;
import com.example.stpl.loyality_ui.R;
import com.example.stpl.loyality_ui.bean.BuyMerchandise;
import com.example.stpl.loyality_ui.bean.PacketData;
import com.example.stpl.loyality_ui.bean.ProductData;


public class ApiUiPrev extends AppCompatActivity implements View.OnClickListener{
        RequestQueue request;
        private String jsonResponse;
        Button b, reedemData, buyMerchandise,current_tier,next_tier;
        TextView tv_maintainance,tv_tier_Bonus,tv_displayName,tv_a,tv_b,tv_c;
        EditText id, size;
        ArrayList<PacketData> list;
        ArrayList<ProductData> listProduct;
        ArrayList<BuyMerchandise> listbuyMerchandise;
        Current_Tier listcurrent_tier[] = new Current_Tier[1];
        ListView listView;
        ProgressDialog dialog;
        int idd;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_api_ui_prev);
            init();
            dialog.setCancelable(false);
            dialog.setMessage("Please Wait...");

            request = Volley.newRequestQueue(ApiUiPrev.this);

            b.setOnClickListener(this);
            reedemData.setOnClickListener(this);
            buyMerchandise.setOnClickListener(this);
            current_tier.setOnClickListener(this);
            next_tier.setOnClickListener(this);
        }


        public void init() {


            idd = 0;
            list = new ArrayList<>();
            listProduct = new ArrayList<>();
            dialog = new ProgressDialog(this);
            id = findViewById(R.id.id);
            listView = findViewById(R.id.listView);
            current_tier=findViewById(R.id.current_tier);
            next_tier=findViewById(R.id.next_tier);
            tv_displayName=findViewById(R.id.tv_displayName);
            tv_maintainance=findViewById(R.id.tv_maintainance);
            tv_tier_Bonus=findViewById(R.id.tv_tier_Bonus);
            buyMerchandise = findViewById(R.id.buyMerchandise);
            b = (Button) findViewById(R.id.response);
            reedemData = findViewById(R.id.Reedemdata);
            tv_a=findViewById(R.id.tv_a);
            tv_b=findViewById(R.id.tv_b);
            tv_c=findViewById(R.id.tv_c);


        }



        @Override
        public void onClick(View v) {

            int rid = v.getId();

            switch (rid) {
                case R.id.response: {
                    dialog.show();
                    list.clear();
                    JSONObject object = new JSONObject();
                    idd=602;
                    try {
                        if(!id.getText().toString().equals(""))
                            idd = Integer.parseInt(id.getText().toString());

                        Toast.makeText(ApiUiPrev.this, "Custom Id : " + idd, Toast.LENGTH_SHORT).show();

                        if (idd > 0) {
                            Toast.makeText(ApiUiPrev.this, "Custom Id and Size Block", Toast.LENGTH_SHORT).show();
                            object.put("playerId", idd);
                            object.put("size", "FULL");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    makeJsonObjectRequest_LoyalPlayer(object);
                    break;
                }
                case R.id.Reedemdata: {

                    dialog.show();
                    listProduct.clear();
                    JSONObject object = new JSONObject();
                    try {
                       // object.put("domainName", "dev.khelplayrummy.com");
                        object.put("domainName", ApiClient.domainName);
                        object.put("playerId", 394);
                        object.put("playerToken", "e101c124ad1961b619a6b17d72c46c76");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    makeJsonObjectRequest_ReedemData(object);
                    break;
                }
                case R.id.buyMerchandise: {
                    dialog.show();
                    JSONObject object = new JSONObject();
                    try {
                        //object.put("domainName", "dev.khelplayrummy.com");
                        object.put("domainName", ApiClient.domainName);
                        object.put("playerId", 1);
                        object.put("playerToken", "c74d68da980a82506c1d94abdba0bf66");
                        object.put("productId", 1);
                        object.put("quantity", "1");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    makeJsonObjectRequest_BuyMerchandise(object);
                    break;
                }

                case R.id.current_tier: {
                    dialog.show();
//           listcurrent_tier.clear();
                    idd=602;
                     if(!id.getText().toString().equals(""))
                        idd = Integer.parseInt(id.getText().toString());

                    JSONObject object = new JSONObject();
                    try
                    {
                        object.put("playerId", idd);
                        object.put("size", "FULL");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    makeJsonObjectRequest_CurrentTier(object);
                    break;
                }
                case R.id.next_tier:{

                    dialog.show();
                    idd=602;
                    if(!id.getText().toString().equals(""))
                        idd = Integer.parseInt(id.getText().toString());
                    JSONObject object = new JSONObject();
                    try {
                        object.put("playerId",idd);
                        object.put("size","FULL");
                    } catch (JSONException e) {
                        Toast.makeText(ApiUiPrev.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    makeJsonObjectRequest_NextTier(object);
                    break;
                }
            }

        }


        private void makeJsonObjectRequest_LoyalPlayer(JSONObject object) {
            list.clear();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    ApiClient.BaseUrl + ApiClient.Loyal_Player_Detail, object, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("errorCode") == 0) {
                            JSONArray packets = response.getJSONArray("packets");
                            for (int i = 0; i < packets.length(); i++) {
                                JSONObject packet = packets.getJSONObject(i);

                                PacketData packetData = new PacketData();
                                packetData.setAccumulationStartDate(packet.getLong("accumulationStartDate"));
                                packetData.setTotalEarning(Float.valueOf(packet.getString("totalEarning")));
                                packetData.setId(packet.getInt("id"));
                                packetData.setState(packet.getString("state"));


                                if (packet.has("expiryDate")) {
                                    packetData.setExpiryDate(packet.getLong("expiryDate"));
                                }
                                if (packet.has("accumulationEndDate"))
                                    packetData.setAccumulationEndDate(packet.getLong("accumulationEndDate"));


                                list.add(packetData);

                                Log.e("abcc", list.get(i).getAccumulationEndDate() + "\t" + list.get(i).getState() + "\t" + list.get(i).getId() + "\t" + list.get(i).getTotalEarning() + "\t");

                            }

                            final ListAdapter adapter = new ListAdapter(ApiUiPrev.this, list);
                            listView.setAdapter(adapter);

                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(ApiUiPrev.this, "Invalid Request", Toast.LENGTH_SHORT).show();
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

        public void makeJsonObjectRequest_ReedemData(JSONObject object) {

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Redeem_Page, object, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.e("response", response.toString());
                        if (response.getInt("errorCode") == 0) {
                            JSONArray products = response.getJSONArray("products");
                            for (int i = 0; i < products.length(); i++) {
                                JSONObject packet = products.getJSONObject(i);
                                Toast.makeText(ApiUiPrev.this, "Request  " + i, Toast.LENGTH_SHORT).show();

                                ProductData productData = new ProductData();
                                productData.setProductId(packet.getInt("productId"));
                                productData.setPrice(packet.getInt("price"));
                                productData.setQuantity(packet.getInt("quantity"));
                                productData.setCreator(packet.getString("creator"));
                                productData.setDisplayName(packet.getString("displayName"));
                                productData.setImageFilename(packet.getString("imageFilename"));
                                listProduct.add(productData);
                            }

                            final ListProductAdapter adapter = new ListProductAdapter(ApiUiPrev.this, listProduct);
                            listView.setAdapter(adapter);

                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(ApiUiPrev.this, "Invalid Request", Toast.LENGTH_SHORT).show();
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
            request.add(jor);
        }


        private void makeJsonObjectRequest_NextTier(JSONObject object) {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Loyal_Player_Detail, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if(response.getInt("errorCode")==0){
                            Log.e("Response :",response.toString());
                            JSONObject tier = response.getJSONObject("playerInfo");
                            JSONObject tier1 = tier.getJSONObject("nextTier");

                      /* tv_maintainance.setText(tier1.getInt("entryPoints")+"");
                       tv_tier_Bonus.setText(tier1.getInt("tierBonusPercentage")+"");
                       tv_displayName.setText(tier1.getString("displayName"));*/
                            tv_a.setText(tier1.getInt("entryPoints")+"");
                            tv_b.setText(tier1.getInt("tierBonusPercentage")+"");
                            tv_c.setText(tier1.getString("displayName"));
                            dialog.dismiss();
                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(ApiUiPrev.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(ApiUiPrev.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            request.add(jor);
        }





        private void makeJsonObjectRequest_CurrentTier(JSONObject object) {

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Loyal_Player_Detail, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("errorCode") == 0) {
                            Log.e("Response : ", response.toString());
                            JSONObject tier = response.getJSONObject("playerInfo");
                            JSONObject tier1 = tier.getJSONObject("currentTier");

                            tv_maintainance.setText(tier1.getInt("maintanancePoints")+"");
                            tv_tier_Bonus.setText(tier1.getInt("tierBonusPercentage")+"");
                            tv_displayName.setText(tier1.getString("displayName"));
/*                    Current_Tier ct = new Current_Tier();
                    ct.setMaintanancePoints(tier1.getInt("maintanancePoints"+""));
                    ct.setTierBonusPercentage(tier1.getInt("tierBonusPercentage"+""));
                    ct.setDisplayName(tier1.getString("displayName"));*/
                            //      listcurrent_tier.add(ct);

                            //final ListCurrentTierAdapter adapter = new ListCurrentTierAdapter(MainActivity.this, listcurrent_tier);
                            //listView.setAdapter(adapter);

                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(ApiUiPrev.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(ApiUiPrev.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            request.add(jor);
        }


        private void makeJsonObjectRequest_BuyMerchandise(JSONObject object) {
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Buy_Merchandise, object, new Response.Listener<JSONObject>() {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Buy_Merchandise, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("errorCode") == 0) {
                            Log.e("Response : ", response.toString());
                            JSONArray merchandise = response.getJSONArray("merchant");
                            for (int i = 0; i < merchandise.length(); i++) {
                                JSONObject merchand = merchandise.getJSONObject(i);
                                BuyMerchandise bm = new BuyMerchandise();

                                bm.setCode(merchand.getInt("code"));
                                bm.setDescription(merchand.getString("description"));
                                bm.setMessage(merchand.getString("message"));

                                listbuyMerchandise.add(bm);
                            }
                            final ListBuyMerchandiseAdapter adapter = new ListBuyMerchandiseAdapter(ApiUiPrev.this, listbuyMerchandise);
                            listView.setAdapter(adapter);

                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(ApiUiPrev.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(ApiUiPrev.this,e.getMessage(), Toast.LENGTH_SHORT).show();
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