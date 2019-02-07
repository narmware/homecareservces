package com.narmware.doormojo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.narmware.doormojo.R;
import com.narmware.doormojo.fragment.SubSrvicesFragment;
import com.narmware.doormojo.pojo.ServiceMain;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class ViewMoreAdapter extends RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder> implements Filterable {

     static List<ServiceMain> services;
    static List<ServiceMain> servicesFiltered=new ArrayList<>();
    static Context mContext;
    ImageView mImgFrame;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ContactsAdapterListener listener;
    //boolean isFiltering=false;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //isFiltering=false;

                    servicesFiltered = services;
                } else {
                   // isFiltering=true;

                    List<ServiceMain> filteredList = new ArrayList<>();
                    for (ServiceMain row : services) {

                        if (row.getService_title().toUpperCase().contains(charString.toUpperCase())) {
                            Toast.makeText(mContext, "Filtered "+charString.toUpperCase(), Toast.LENGTH_SHORT).show();
                            filteredList.add(row);
                        }
                    }

                    servicesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = servicesFiltered;
                return filterResults;            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                servicesFiltered = (ArrayList<ServiceMain>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mthumb_title;
        ServiceMain mItem;
        //RelativeLayout mRelativeItem;

        public MyViewHolder(View view) {
            super(view);
            mthumb_title= view.findViewById(R.id.txt_serv_name);
            mImgFrame=view.findViewById(R.id.img_serv);
           // mRelativeItem=view.findViewById(R.id.relative);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   // int position= (int) mRelativeItem.getTag();
                    //Toast.makeText(mContext, mItem.getService_title()+"", Toast.LENGTH_SHORT).show();
                    setFragment(SubSrvicesFragment.newInstance(Integer.parseInt(mItem.getService_id()),mItem.getService_image(),mItem.getService_title(),mItem.getService_desc()),"subservices");
                    }
            });


        }
    }

    public ViewMoreAdapter(Context context, List<ServiceMain> services, FragmentManager fragmentManager) {
        this.mContext = context;
        this.services = services;
        this.servicesFiltered = services;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_more, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceMain serviceMain = servicesFiltered.get(position);

        //holder.mRelativeItem.setTag(position);
        Activity activity= (Activity) mContext;

        String img_extension=serviceMain.getService_image().substring(serviceMain.getService_image().length() - 3);

       // Log.e("trimWord", serviceMain.getService_image().substring(serviceMain.getService_image().length() - 3));
        if(img_extension.equals("svg")) {
            SvgLoader.pluck()
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(serviceMain.getService_image(), mImgFrame);
        }else {
            Picasso.with(mContext)
                    .load(serviceMain.getService_image())
                    .fit()
                    .noFade()
                    .placeholder(mContext.getResources().getDrawable(R.drawable.placeholder))
                    .into(mImgFrame);
        }
        holder.mthumb_title.setText(serviceMain.getService_title());
        holder.mItem=serviceMain;

    }

    public void setFragment(Fragment fragment,String tag) {
        /*fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //fragment.setSharedElementReturnTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.explode));
            fragment.setExitTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.explode));

            // Create new fragment to add (Fragment B)
            //fragment.setSharedElementEnterTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.slide_left));
            fragment.setEnterTransition(TransitionInflater.from(mContext).inflateTransition(android.R.transition.explode));

            // Add Fragment B
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment,tag)
                    .addToBackStack(null)
                    /*.addToBackStack("transaction")
                    .addSharedElement(mImgFrame, "MyTransition")*/;

            fragmentTransaction.commit();
        }
        else{
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment,tag)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    @Override
    public int getItemCount() {

      /*  if(isFiltering==false) {
            return services.size();
        }

        if(isFiltering==true) {
            return servicesFiltered.size();
        }*/

        return servicesFiltered.size();
    }
    public interface ContactsAdapterListener {
        void onContactSelected(ServiceMain contact);
    }


}