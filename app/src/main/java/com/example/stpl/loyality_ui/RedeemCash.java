package com.example.stpl.loyality_ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;


public class RedeemCash extends Fragment implements View.OnClickListener {

    RequestQueue request;
    JSONObject object;
    EditText editAmount;
    Button r10,r50,r100,r200,r500,r1000,buy;
    TextView message,description,respMsg,Code;
    String Amount;

    public RedeemCash() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_redeem_cash, container, false);
        buy =v.findViewById(R.id.buy);
        r10= v.findViewById(R.id.r10);
        r50= v.findViewById(R.id.r50);
        r100= v.findViewById(R.id.r100);
        r200= v.findViewById(R.id.r200);
        r500= v.findViewById(R.id.r500);
        r1000= v.findViewById(R.id.r1000);

        message =v.findViewById(R.id.message);
        description=v.findViewById(R.id.description);
        respMsg = v.findViewById(R.id.respMsg);
        Code = v.findViewById(R.id.code);

        editAmount = v.findViewById(R.id.editAmount);

        r10.setOnClickListener(this);
        r50.setOnClickListener(this);
        r100.setOnClickListener(this);
        r200.setOnClickListener(this);
        r500.setOnClickListener(this);
        r1000.setOnClickListener(this);
        buy.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.r10:
                editAmount.setText("10");
                break;
            case R.id.r50:
                editAmount.setText("50");
                break;

            case R.id.r100:
                editAmount.setText("100");
                break;

            case R.id.r200:
                editAmount.setText("200");
                break;

            case R.id.r500:
                editAmount.setText("500");
                break;

            case R.id.r1000:
                editAmount.setText("1000");
                break;
            case R.id.buy:
               // dialog.show();
                Amount=editAmount.getText().toString();
                JSONObject object = new JSONObject();
                try {

                    object.put("amount",Amount);
                    object.put("domainName", ApiClient.domainName);
                    object.put("playerId", ApiClient.playerId);
                    object.put("playerToken", ApiClient.playerToken);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                makeJsonObjectRequest_GetCash(object);
                break;
        }


    }

    private void makeJsonObjectRequest_GetCash(JSONObject object) {

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ApiClient.BaseUrl + ApiClient.Get_Cash, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getInt("errorCode")==0){
                        Log.e("Response :",response.toString());


                        JSONObject tier = response.getJSONObject("result");

                         Code.setText(tier.getInt("code")+"");
                         description.setText(tier.getInt("description")+"");
                         respMsg.setText(response.getString("respMsg"));
                         message.setText(tier.getInt("message")+"");

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Invalid Cash Request", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage()+"", Toast.LENGTH_SHORT).show();

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
}