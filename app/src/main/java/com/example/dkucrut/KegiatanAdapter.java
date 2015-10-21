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
public class KegiatanAdapter extends ArrayAdapter<Kegiatan> {

    Context context;
    int layout;
    List<Kegiatan> listKegiatan;


    public KegiatanAdapter(Context context, int textViewResourceId,
                         List<Kegiatan> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.layout=textViewResourceId;
        this.listKegiatan=objects;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        View v=convertView;
        MembertHolder holder;

        if(v==null){
            LayoutInflater vi=((Activity)context).getLayoutInflater();
            v=vi.inflate(layout, parent, false);

            holder=new MembertHolder();
            holder.textViewName=(TextView) v.findViewById(R.id.txtName);
            holder.textViewDate=(TextView) v.findViewById(R.id.txtDate);
            v.setTag(holder);
        }else{
            holder=(MembertHolder) v.getTag();
        }

        Kegiatan kegiatan=listKegiatan.get(position);
        holder.textViewName.setText(kegiatan.getEvent());
        holder.textViewDate.setText(kegiatan.getTanggal());

        return v;
    }

    static class MembertHolder{
        TextView textViewName;
        TextView textViewDate;
    }
}