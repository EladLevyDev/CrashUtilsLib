package com.crashreportutils.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    ArrayList<String> data;
    Context context;
    LayoutInflater layoutInflater;


    public UserAdapter(ArrayList<String> data, Context context) {
        super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.user_item, null);

        TextView txt=(TextView)convertView.findViewById(R.id.textView);
        ImageView image=(ImageView)convertView.findViewById(R.id.imageView);

        txt.setText(data.get(position));



        return convertView;
    }

}