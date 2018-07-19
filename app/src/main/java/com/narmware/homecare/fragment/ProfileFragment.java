package com.narmware.homecare.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.homecare.R;
import com.narmware.homecare.helper.ImageBlur;
import com.narmware.homecare.helper.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bitmap bitmap;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.background) ImageView mImgBlurrBg;
    @BindView(R.id.prof_img) ImageView mImgProf;
    @BindView(R.id.prof_name) TextView mTxtProfName;
    @BindView(R.id.prof_mail) TextView mTxtProfMail;
    @BindView(R.id.prof_addr) TextView mTxtProfAddr;
    @BindView(R.id.prof_addr_office) TextView mTxtProfAddrOffice;
    @BindView(R.id.img_btn_edit) ImageButton mImgBtnEdit;

    String mName,mMail,mAddr,mOfficeAddr;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this,view);

        mName=SharedPreferencesHelper.getBookName(getContext());
        mMail=SharedPreferencesHelper.getBookMail(getContext());
        mAddr=SharedPreferencesHelper.getBookAddress(getContext());
        mOfficeAddr=SharedPreferencesHelper.getBookOfficeAddress(getContext());

        try {
            String image_url = "https://storage.googleapis.com/idx-acnt-gs.ihouseprd.com/AR706484/file_manager/Latelier_geantsduweb.jpg";
            bitmap = new ImageBlur().getBitmapFromURL(image_url);
            mImgBlurrBg.setImageBitmap(new ImageBlur().fastblur(bitmap, 1));
        }catch (Exception e){
            e.printStackTrace();
        }

        mTxtProfName.setText(mName);
        mTxtProfMail.setText(mMail);
        mTxtProfAddr.setText(mAddr);
        mTxtProfAddrOffice.setText(mOfficeAddr);

        mImgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(AddressFragment.newInstance(true));
            }
        });

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

    public void setFragment(Fragment fragment)
    {
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

}
