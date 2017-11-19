package com.example.haojie06.todolist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by haojie06 on 2017/11/18.
 */


public class myBaseAdapter extends BaseAdapter {

    private List<Things> things;
    private Context mContext;            //传入的内容是否可更改？
    public myBaseAdapter(Context mContext, List<Things> object) {
        super();
        this.mContext = mContext;
        this.things = object;
    }

    @Override
    public int getCount() {
        return things.size();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item,null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView time = (TextView) view.findViewById(R.id.time);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        time.setText(things.get(position).getTime());
        title.setText((things.get(position)).getTitle());
        content.setText(things.get(position).getContent());

  //     int color = Integer.valueOf(things.get(position).getColor());
        int color = 0;
        String S_color = (things.get(position).getColor());
        try{
         color = Integer.parseInt(S_color);
    }catch(Exception e){
        e.printStackTrace();
    }



        switch (color)
        {
            case 0:
                linearLayout.setBackgroundResource(R.drawable.colorg);
                break;
            case 1:
                linearLayout.setBackgroundResource(R.drawable.colorgr);
                break;
            case 2:
                linearLayout.setBackgroundResource(R.drawable.colorb);
                break;
            case 3:
                linearLayout.setBackgroundResource(R.drawable.colorh);
                break;
            case 4:
                linearLayout.setBackgroundResource(R.drawable.colory);
                break;
        }


        /*
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


            }
        });         暂时不知道怎么使用？
*/



        return view;
    }
}
