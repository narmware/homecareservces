package com.narmware.homecare.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.narmware.homecare.R;
import com.narmware.homecare.helper.SharedPreferencesHelper;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static EditText mEdtName;
    public static  EditText mEdtMobile;
    public static  EditText mEdtMail;
    public static EditText mEdtDesc;
    public static EditText mEdtAlterMobile;

    String mName,mMobile,mMail;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalDetailsFragment newInstance(String param1, String param2) {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
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
        View view= inflater.inflate(R.layout.fragment_personal_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init(view);
        mName= SharedPreferencesHelper.getBookName(getContext());
        mMail= SharedPreferencesHelper.getBookMail(getContext());
        mMobile= SharedPreferencesHelper.getBookMobile(getContext());

        if(mName!=null)
        {
            mEdtName.setText(mName);
        }
        if(mMail!=null)
        {
            mEdtMail.setText(mMail);
        } if(mMobile!=null)
        {
            mEdtMobile.setText(mMobile);
        }
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this,view);

        mEdtName=view.findViewById(R.id.edt_name);
        mEdtMobile=view.findViewById(R.id.edt_mobile);
        mEdtAlterMobile=view.findViewById(R.id.edt_altermobile);
        mEdtMail=view.findViewById(R.id.edt_email);
        mEdtDesc=view.findViewById(R.id.edt_order_desc);
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
}
