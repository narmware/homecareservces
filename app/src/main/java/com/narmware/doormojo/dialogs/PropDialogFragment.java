package com.narmware.doormojo.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.doormojo.MyApplication;
import com.narmware.doormojo.R;
import com.narmware.doormojo.activity.OtpLoginActivity;
import com.narmware.doormojo.helper.Constants;
import com.narmware.doormojo.helper.ImageBlur;
import com.narmware.doormojo.helper.SupportFunctions;
import com.narmware.doormojo.pojo.Login;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

public class PropDialogFragment extends DialogFragment {

    String name,book_id;
    TextView mTxtName;
    RatingBar mRatingBar;
    ImageButton mImgBtnClose;
    Button mBtnSubmitRate;
    RequestQueue mVolleyRequest;
    String rating;

    @SuppressLint("ValidFragment")
    private PropDialogFragment() { /*empty*/ }

    /** creates a new instance of PropDialogFragment */
    public static PropDialogFragment newInstance() {
        return new PropDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //getting proper access to LayoutInflater is the trick. getLayoutInflater is a                   //Function
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mVolleyRequest = Volley.newRequestQueue(getContext());

        View view = inflater.inflate(R.layout.item_booking_rating, null);
        mTxtName=view.findViewById(R.id.txt_name);

        mImgBtnClose=view.findViewById(R.id.btn_close);
        mRatingBar=view.findViewById(R.id.simpleRatingBar);
        mBtnSubmitRate=view.findViewById(R.id.btn_submit_rate);

        name = getArguments().getString("name");
        book_id = getArguments().getString("book_id");

        mTxtName.setText(name);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getContext(), ratingBar.getRating()+"", Toast.LENGTH_SHORT).show();
                rating= String.valueOf(ratingBar.getRating());
            }
        });

        mBtnSubmitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendServiceRating();
                dismiss();
            }
        });
        mImgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();

    }

    private void sendServiceRating() {

        HashMap<String,String> param = new HashMap();
        param.put(Constants.BOOKING_ID,book_id);
        param.put(Constants.BOOKING_RATING,rating);

        //url with params
        String url= SupportFunctions.appendParam(MyApplication.URL_UPDATE_RATING,param);

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Rating Json_string",response.toString());
                            Gson gson = new Gson();

                            Login loginResponse= gson.fromJson(response.toString(), Login.class);
                            if(loginResponse.getResponse().equals("100"))
                            {
                                Toast.makeText(getContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");

                    }
                }
        );

        mVolleyRequest.add(obreq);
    }

}