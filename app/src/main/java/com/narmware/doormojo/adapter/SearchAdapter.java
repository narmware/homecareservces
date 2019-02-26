package com.narmware.doormojo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.narmware.doormojo.R;
import com.narmware.doormojo.pojo.ServiceMain;
import com.narmware.doormojo.pojo.SubServices;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable{

    Context mContext;
    ArrayList<SubServices> subServices;
    ArrayList<SubServices> suggestions;

    public SearchAdapter(Context mContext, ArrayList<SubServices> subServices) {
        this.mContext = mContext;
        this.subServices = subServices;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_search, viewGroup, false);

        TextView mTxtSearch=itemView.findViewById(R.id.txt_search);
        mTxtSearch.setText(suggestions.get(i).getSer_name());
        return itemView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((SubServices)(resultValue)).getSer_name();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                for (SubServices customer : subServices) {
                    if(customer.getSer_name().contains(constraint)){
                        suggestions.add(customer);
                    }
                }

            }
            if(constraint == null)
            {
                suggestions=subServices;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = suggestions;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                suggestions = (ArrayList<SubServices>) results.values;
                notifyDataSetChanged();
        }
    };

}
