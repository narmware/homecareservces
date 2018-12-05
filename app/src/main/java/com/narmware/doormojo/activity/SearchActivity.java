package com.narmware.doormojo.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.doormojo.MyApplication;
import com.narmware.doormojo.R;
import com.narmware.doormojo.adapter.SearchAdapter;
import com.narmware.doormojo.pojo.MainServiceResponse;
import com.narmware.doormojo.pojo.ServiceMain;
import com.narmware.doormojo.pojo.SubServiceResponse;
import com.narmware.doormojo.pojo.SubServices;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.auto_complete) AutoCompleteTextView searchView;
    RequestQueue mVolleyRequest;
    SearchAdapter searchAdapter;
    ArrayList<SubServices> subServices;

    ArrayList<String> subServices1;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
        mVolleyRequest = Volley.newRequestQueue(SearchActivity.this);

        setAutoAdapter();
        GetAllSubServices();
    }

    private void setAutoAdapter() {
        subServices=new ArrayList<>();
        subServices1=new ArrayList<>();

        //searchAdapter=new SearchAdapter(SearchActivity.this,subServices);
        arrayAdapter=new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,subServices1);
        searchView.setAdapter(arrayAdapter);

       // searchAdapter.notifyDataSetChanged();
        arrayAdapter.notifyDataSetChanged();

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(SearchActivity.this,searchView.getText()+"  "+subServices.size(), Toast.LENGTH_SHORT).show();

                for(int i=0;i<subServices.size();i++)
                {
                    Log.e("Matching",subServices.get(i).getSer_name()+"  "+searchView.getText());
                    //if(searchView.getText().equals(subServices.get(i).getSer_name()))
                    if(subServices.get(i).getSer_name().equals(searchView.getText()))
                    {
                        Toast.makeText(SearchActivity.this,"id "+subServices.get(i).getSub_service_id(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void GetAllSubServices() {


        final ProgressDialog dialog = new ProgressDialog(SearchActivity.this);
        dialog.setMessage("getting services ...");
        dialog.setCancelable(false);
        //dialog.show();

        HashMap<String,String> param = new HashMap();
        // param.put(Constants.REQUEST_ALBUM,mAlbumId);

        //url with params
        //String url= SupportFunctions.appendParam(MyApplication.URL_GET_SERVICES,param);

        //url without params
        String url= MyApplication.URL_GET_SEARCH_SUBSERVICES;

        Log.e("Service url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            // testMasterDetails = testMasterArray.toString();

                            Log.e("Service Json_string",response.toString());
                            Gson gson = new Gson();

                            SubServiceResponse mainServiceResponse= gson.fromJson(response.toString(), SubServiceResponse.class);

                            if(mainServiceResponse.getResponse().equals("100")) {
                                SubServices[] service = mainServiceResponse.getData();
                                for (SubServices item : service) {
                                    subServices.add(item);
                                    subServices1.add(item.getSer_name());

                                    Log.e("Featured img title", item.getSer_name()+"  "+item.getSub_service_id());
                                }
                               // searchAdapter.notifyDataSetChanged();
                                arrayAdapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();
                        }
                        //dialog.dismiss();
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
