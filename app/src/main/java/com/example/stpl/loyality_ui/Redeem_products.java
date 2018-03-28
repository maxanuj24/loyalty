package com.example.stpl.loyality_ui;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.stpl.loyality_ui.Adapter.List_RedeemProduct;
import com.example.stpl.loyality_ui.bean.BuyMerchandise;
import com.example.stpl.loyality_ui.bean.ProductData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Redeem_products extends AppCompatActivity implements View.OnClickListener, RecyclerViewListener {
    RecyclerView redeemList;
    ProgressDialog progressDialog;
    RequestQueue request;
    ArrayList<ProductData> list_product;
    JSONObject object;
    Spinner quantity_spin;
    Button buyNow, cashRedeem;
    private JSONArray merchandise;

    private static final String TAG = Redeem_products.class.getSimpleName();

    //CheckBox checkBox;
    // Boolean isChecked=false;
    private int productId, productQuantity;
    private RecyclerViewListener listener;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_products);

        request = Volley.newRequestQueue(this);

        listener = this;

        try {
            if (getIntent() != null)
                object = new JSONObject(getIntent().getStringExtra("JSON"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        init();

        buyNow.setOnClickListener(this);
        cashRedeem.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeJsonObjectRequest_ReedemData(object);

    }

    public void init() {
        list_product = new ArrayList<>();
        redeemList = findViewById(R.id.redeemList);
        progressDialog = new ProgressDialog(this);
        quantity_spin = findViewById(R.id.quantity_spin);
        buyNow = findViewById(R.id.buyProduct);
        cashRedeem = findViewById(R.id.redeemCash);
        //checkBox = findViewById(R.id.checkBox);

    }


    public void makeJsonObjectRequest_ReedemData(JSONObject object) {

        //    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Redeem_Page, object, new Response.Listener<JSONObject>() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Redeem_Page, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG, response.toString());
                    if (response.getInt("errorCode") == 0) {

                        JSONArray products = response.getJSONArray("products");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject packet = products.getJSONObject(i);
                            ProductData productData = new ProductData();
                            productData.setProductId(packet.getInt("productId"));
                            productData.setPrice(packet.getInt("price"));
                            productData.setQuantity(packet.getInt("quantity"));
                            productData.setCreator(packet.getString("creator"));
                            productData.setDisplayName(packet.getString("displayName"));
                            productData.setImageFilename(packet.getString("imageFilename"));

                            list_product.add(productData);

                        }

                        final List_RedeemProduct adapter =
                                new List_RedeemProduct(Redeem_products.this, list_product, listener);
                        redeemList.setAdapter(adapter);

                        LinearLayoutManager llm = new LinearLayoutManager(Redeem_products.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        redeemList.setLayoutManager(llm);
                        redeemList.setAdapter(adapter);

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Redeem_products.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.e("EXC", "onResponse: " + e.getMessage());
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("res", "onErrorResponse: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jor);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.buyProduct: {
                /*dialog.show();
                JSONObject object = new JSONObject();
                try {
                    //  object.put("domainName", "dev.khelplayrummy.com");
                    object.put("domainName", ApiClient.domainName);
                    object.put("playerId", ApiClient.playerId);
                    object.put("playerToken", ApiClient.playerToken);


                        * player id and player token as per the player login
                        * product id
                        * product Quantity  as per the users selection


                    object.put("productId", productId);
                    object.put("quantity", productQuantity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                makeJsonObjectRequest_BuyMerchandise(object);*/
                break;
            }
            case R.id.redeemCash: {
                Fragment fragment = new RedeemCash();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_redeemCash, fragment).commit();
                FrameLayout fm = findViewById(R.id.fragment_redeemCash);
                fm.setVisibility(View.VISIBLE);

                RelativeLayout chats = (RelativeLayout) findViewById(R.id.relative);
                chats.setVisibility(View.GONE);

                RelativeLayout noChats = (RelativeLayout) findViewById(R.id.relative);
                noChats.setVisibility(View.GONE);


            }
        }

    }


    private void makeJsonObjectRequest_BuyMerchandise(JSONObject object, final ProductData data) {
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Buy_Merchandise, object, new Response.Listener<JSONObject>() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Buy_Merchandise, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("errorCode") == 0) {
                        Log.e("Response : ", response.toString());
                        merchandise = response.getJSONArray("merchant");
                        for (int i = 0; i < merchandise.length(); i++) {
                            JSONObject merchand = merchandise.getJSONObject(i);
                            ProductData bm = new ProductData();

                            bm.setCode(merchand.getInt("code"));
                            bm.setDescription(merchand.getString("description"));
                            bm.setMessage(merchand.getString("message"));
                        }
                        progressDialog.dismiss();
                        setData(data);
                    } else {
                        Toast.makeText(Redeem_products.this, response.getString("respMsg"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        request.add(jor);

    }


    private void setData(ProductData data) {
        productQuantity = 0;
        productId = data.getProductId();
        productQuantity = data.getSelected_qty();

        if (productQuantity == 0) {
            Toast.makeText(Redeem_products.this, "Select at least 1 quantity of " + data.getDisplayName(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(Redeem_products.this, data.getSelected_qty() + " " + data.getProductId(), Toast.LENGTH_SHORT).show();

            //dialog.show();
            JSONObject object = new JSONObject();
            try {
                object.put("domainName", ApiClient.domainName);
                object.put("playerId", ApiClient.playerId);
                object.put("playerToken", ApiClient.playerToken);
                object.put("productId", productId);
                object.put("quantity", productQuantity);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            for (int i = 0; i < merchandise.length(); i++) {
                JSONObject merchant = merchandise.getJSONObject(i);
                ProductData bm = new ProductData();

                bm.setCode(merchant.getInt("code"));
                bm.setDescription(merchant.getString("description"));
                bm.setMessage(merchant.getString("message"));
            }
        } catch (Exception e) {
            Log.e(TAG, "onItemClick: " + e.getMessage());
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("  Buy Merchandise ");

        TextView text1 = (TextView) dialog.findViewById(R.id.name);
        TextView text2 = (TextView) dialog.findViewById(R.id.quantity);
        TextView text3 = dialog.findViewById(R.id.buy_message);
        text1.setText("" + data.getDisplayName());
        text2.setText("Quantity:" + data.getSelected_qty());
        text3.setText("" + data.getMessage() + " " + data.getDescription() + " " + data.getRespMsg());
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(ProductData data) {
        progressDialog.show();

        JSONObject object = new JSONObject();
        try {
            //  object.put("domainName", "dev.khelplayrummy.com");
            object.put("domainName", ApiClient.domainName);
            object.put("playerId", ApiClient.playerId);
            object.put("playerToken", ApiClient.playerToken);
                        /*

                        * player id and player token as per the player login
                        *
                        * product id
                        * product Quantity  as per the users selection
                        * */
            object.put("productId", productId);
            object.put("quantity",productQuantity);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        makeJsonObjectRequest_BuyMerchandise(object, data);
    }
}
