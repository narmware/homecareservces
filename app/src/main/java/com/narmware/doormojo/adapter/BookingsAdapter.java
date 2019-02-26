package com.narmware.doormojo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.doormojo.R;
import com.narmware.doormojo.activity.BookAppointmentActivity;
import com.narmware.doormojo.activity.WebViewActivity;
import com.narmware.doormojo.dialogs.PropDialogFragment;
import com.narmware.doormojo.helper.Constants;
import com.narmware.doormojo.pojo.MyBookings;

import java.util.ArrayList;


/**
 * Created by Lincoln on 31/03/16.
 */

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    ArrayList<MyBookings> bookings;
    Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtBService_name,mTxtBDate,mTxtBDesc,mTxtPrice,mTxtBNo;
        RatingBar mRatingBar;
        TextView mImgStaus;
        MyBookings mItem;
        //RelativeLayout mRelativeItem;

        public MyViewHolder(View view) {
            super(view);
            mTxtBService_name= view.findViewById(R.id.txt_name);
            mTxtBDate=view.findViewById(R.id.txt_date);
            mTxtBDesc=view.findViewById(R.id.txt_desc);
            mRatingBar=view.findViewById(R.id.simpleRatingBar);
            mImgStaus=view.findViewById(R.id.status_img);
            mTxtBNo=view.findViewById(R.id.txt_order_no);
            mTxtPrice=view.findViewById(R.id.txt_price);

            // mRelativeItem=view.findViewById(R.id.relative);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // int position= (int) mRelativeItem.getTag();
                    //Toast.makeText(mContext, mItem.getOrder_id()+"", Toast.LENGTH_SHORT).show();

                    if (mItem.getB_price().equals("0.00") || mItem.getB_price().equals("0") || mItem.getB_price().equals("")) {
                        Toast.makeText(mContext, "Your payment is not generated yet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (mItem.getB_status().equals(Constants.PAID)) {
                            setFragment(mItem.getB_service_name(), mItem.getOrder_id());
                        }
                        if (mItem.getB_status().equals(Constants.UNPAID)) {
                            Activity activity = (Activity) mContext;
                            Intent intent = new Intent(activity, WebViewActivity.class);
                            intent.putExtra(Constants.BOOKING_ID, mItem.getOrder_id());
                            activity.startActivity(intent);
                        }
                    }
                }
            });


        }
    }

    public BookingsAdapter(Context context, ArrayList<MyBookings> bookings, FragmentManager fragmentManager) {
        this.mContext = context;
        this.bookings = bookings;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_boooking, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyBookings booking = bookings.get(position);


        //holder.mRelativeItem.setTag(position);
        holder.mTxtBDate.setText(booking.getB_date());
        holder.mTxtBService_name.setText(booking.getB_service_name());
        holder.mTxtBDesc.setText(booking.getB_desc());
        holder.mTxtBNo.setText("#"+booking.getB_no());
        holder.mTxtPrice.setText("Rs. "+booking.getB_price());

            if(booking.getB_status().equals(Constants.PAID))
            {
                holder.mImgStaus.setText(Constants.PAID);
                holder.mImgStaus.setTextColor(mContext.getResources().getColor(R.color.green_700));
                holder.mRatingBar.setVisibility(View.VISIBLE);

                holder.mRatingBar.setRating(booking.getB_ratings());
            }
            if(booking.getB_status().equals(Constants.UNPAID))
            {
                holder.mImgStaus.setText(Constants.UNPAID);
                holder.mImgStaus.setTextColor(mContext.getResources().getColor(R.color.red_700));
            }

        holder.mItem=booking;
    }

    public void setFragment(String name,String id) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag("dialog");
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = PropDialogFragment.newInstance();
            Bundle args = new Bundle();
            args.putString("name",name);
            args.putString("book_id",id);

            newFragment.setArguments(args);
            //newFragment.setSharedElementEnterTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.slide_left));
            newFragment.setEnterTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.slide_right));
            newFragment.setCancelable(false);
            newFragment.show(fragmentTransaction, "dialog");
        }
    }
    @Override
    public int getItemCount() {
        return bookings.size();
    }

}