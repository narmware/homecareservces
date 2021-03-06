package com.narmware.doormojo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.doormojo.MyApplication;
import com.narmware.doormojo.R;
import com.narmware.doormojo.adapter.SubServicesAdapter;
import com.narmware.doormojo.helper.Constants;
import com.narmware.doormojo.helper.SharedPreferencesHelper;
import com.narmware.doormojo.helper.SupportFunctions;
import com.narmware.doormojo.pojo.SubServiceResponse;
import com.narmware.doormojo.pojo.SubServices;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubSrvicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubSrvicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubSrvicesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SERVICE_ID = "service_id";
    private static final String SERVICE_IMAGE = "img";
    private static final String SERVICE_TITLE = "title";
    private static final String SERVICE_DESC = "desc";

    // TODO: Rename and change types of parameters
    private String  service_id;
    private String service_title;
    private String service_img;
    private String service_desc;

    @BindView(R.id.img_serv) ImageView mImgServ;
    @BindView(R.id.txt_serv_name) TextView mtxtServ;
    @BindView(R.id.txt_serv_desc) TextView mtxtDesc;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private OnFragmentInteractionListener mListener;

    ArrayList<SubServices> services;
    SubServicesAdapter mAdapter;
    RequestQueue mVolleyRequest;

    public SubSrvicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubSrvicesFragment newInstance(int ser_id,String service_img,String service_title,String service_desc) {
        SubSrvicesFragment fragment = new SubSrvicesFragment();
        Bundle args = new Bundle();
        args.putInt(SERVICE_ID, ser_id);
        args.putString(SERVICE_IMAGE,service_img);
        args.putString(SERVICE_TITLE, service_title);
        args.putString(SERVICE_DESC, service_desc);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service_id = String.valueOf(getArguments().getInt(SERVICE_ID));
            service_img=getArguments().getString(SERVICE_IMAGE);
            service_title=getArguments().getString(SERVICE_TITLE);
            service_desc=getArguments().getString(SERVICE_DESC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sub_services, container, false);

        init(view);
        SharedPreferencesHelper.setIsMainAct("SubServices",getContext());

        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this,view);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        Picasso.with(getContext())
                .load(service_img)
                .fit()
                .noFade()
                .into(mImgServ);

        mtxtServ.setText(service_title);
        mtxtDesc.setText(service_desc);
        setServiceAdapter(new GridLayoutManager(getContext(),3));
        GetSubServices(service_id);
    }

    public void setServiceAdapter(RecyclerView.LayoutManager mLayoutManager){
        services=new ArrayList<>();
       /* services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-124439-ElectricalInstallation.jpg","Electrical","1",String.valueOf(service_id)));
        services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-123558-plumbing.jpg","Cleaning","1",String.valueOf(service_id)));
        services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-124439-ElectricalInstallation.jpg","Electrical","1",String.valueOf(service_id)));
        services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-123558-plumbing.jpg","Cleaning","1",String.valueOf(service_id)));
        services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-124439-ElectricalInstallation.jpg","Electrical","1",String.valueOf(service_id)));
        services.add(new SubServices("http://doormojo.com/admin/pages/uploads/CAT-123558-plumbing.jpg","Cleaning","1",String.valueOf(service_id)));*/

        SnapHelper snapHelper = new LinearSnapHelper();

        mAdapter = new SubServicesAdapter(getContext(),services,getActivity().getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void GetSubServices(String id) {


        /*final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting sub services ...");
        dialog.setCancelable(false);
        dialog.show();*/

        HashMap<String,String> param = new HashMap();
        param.put(Constants.SERVICE_ID,id);

        //url with params
        String url= SupportFunctions.appendParam(MyApplication.URL_GET_SUBSERVICES,param);

        //url without params
        //String url= MyApplication.URL_GET_SERVICES;

        Log.e("SubService url",url);
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

                            Log.e("SubService Json_string",response.toString());
                            Gson gson = new Gson();

                            SubServiceResponse subServiceResponse= gson.fromJson(response.toString(), SubServiceResponse.class);

                            if(subServiceResponse.getResponse().equals("100")) {
                                SubServices[] service = subServiceResponse.getData();
                                for (SubServices item : service) {

                                    SubServices sub=new SubServices(item.getSer_img_url(),item.getSer_name(),item.getSub_service_id(),service_id,item.getSub_service_desc());
                                    services.add(sub);
                                    Log.e("SubService img title", item.getSer_name());

                                }

                                mAdapter.notifyDataSetChanged();

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
                        // showNoConnectionDialog();
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
