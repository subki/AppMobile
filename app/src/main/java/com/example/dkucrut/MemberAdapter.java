package com.example.dkucrut;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by subki on 10/13/2015.
 */
public class MemberAdapter extends ArrayAdapter<Anggota> {

    Context context;
    int layout;
    List<Anggota> listProduct;

    public MemberAdapter(Context context, int textViewResourceId,
                          List<Anggota> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.layout=textViewResourceId;
        this.listProduct=objects;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        View v=convertView;
        MembertHolder holder;

        if(v==null){
            LayoutInflater vi=((Activity)context).getLayoutInflater();
            v=vi.inflate(layout, parent, false);

            holder=new MembertHolder();
            holder.textViewName=(TextView) v.findViewById(R.id.txtName);
            v.setTag(holder);
        }else{
            holder=(MembertHolder) v.getTag();
        }

        Anggota product=listProduct.get(position);
        holder.textViewName.setText(product.getNama());

        return v;
    }

    static class MembertHolder{
        TextView textViewName;
    }
}