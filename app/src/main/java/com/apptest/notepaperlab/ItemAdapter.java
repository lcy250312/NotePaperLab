package com.apptest.notepaperlab;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private ArrayList<VwItem> vwitems;
    public ItemAdapter(Context c, ArrayList<VwItem> vwitem) {
        myInflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        this.vwitems = vwitem;
    }

    @Override
    public int getCount() {
        if (vwitems != null)
            return vwitems.size();
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if (vwitems != null)
            return vwitems.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (vwitems != null)
            return vwitems.get(position).hashCode();
//            return vwitems.indexOf(getItem(position));
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VwItem vwitem = (VwItem) getItem(position);

        View v = myInflater.inflate(R.layout.item_view, null);
        TextView idt = (TextView) v.findViewById(R.id.txtIdt);
        idt.setText(vwitem.idt);
        TextView notedate = (TextView) v.findViewById(R.id.txtNotedate);
        notedate.setText(vwitem.notedate);
        TextView notetime = (TextView) v.findViewById(R.id.txtNotetime);
        notetime.setText(vwitem.notetime);
        TextView notecontents = (TextView) v.findViewById(R.id.txtNotecontents);
        notecontents.setText(vwitem.notecontents);
        TextView color = (TextView) v.findViewById(R.id.txtColor);
        color.setText(vwitem.color);
        ImageView pic = (ImageView) v.findViewById(R.id.pic);
        pic.setBackgroundColor(Color.parseColor(vwitem.color));
//        LinearLayout lnlitemview = (LinearLayout) v.findViewById(R.id.lnlItemView);
//        lnlitemview.setBackgroundColor(Color.parseColor(vwitem.color));
        return v;
    }
}
