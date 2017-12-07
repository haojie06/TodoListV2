package com.example.haojie06.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haojie06.todolist.Notice.ClockService;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Things> thingsList;
    String Num;
     TextView undoNum;
    ThingsAdapter adapter;
    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags;
            final int swipeFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动的holder的position

            int toPosition = target.getAdapterPosition();//得到目标holder的position
            int temp = 0;
            //执行交换
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    temp = 0;
                    temp = thingsList.get(i).getnum();
                    thingsList.get(i).setnum(thingsList.get(i + 1).getnum());
                    thingsList.get(i).save();
                    thingsList.get(i + 1).setnum(temp);
                    thingsList.get(i + 1).save();
                    Collections.swap(thingsList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    temp = 0;
                    temp = thingsList.get(i).getnum();
                    thingsList.get(i).setnum(thingsList.get(i - 1).getnum());
                    thingsList.get(i - 1).setnum(temp);
                    thingsList.get(i).save();
                    thingsList.get(i - 1).save();
                    Collections.swap(thingsList, i, i - 1);
                }
            }

            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            Things delThing = thingsList.get(position);
            delThing.delete();
            thingsList.remove(position);
            Num = String.valueOf(thingsList.size());
            undoNum.setText(Num);
            adapter.notifyItemRemoved(position);

        }

        /*拖拽时改变颜色
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }*/
    });
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        //活动开始时创建活动
        Intent startIntent = new Intent(this, ClockService.class);
        startService(startIntent);
        //建立数据库  （使用litepal
        thingsList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        Log.d("msg", "创建数据库");
        try {
            thingsList = DataSupport.order("num").find(Things.class);
        } catch (DataSupportException ex) {
            ex.printStackTrace();
        }
        //建立recycleview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ThingsAdapter(thingsList);
        recyclerView.setAdapter(adapter);
        helper.attachToRecyclerView(recyclerView);
        undoNum = (TextView)findViewById(R.id.undoNum);//显示未完成的任务数量

        adapter = new ThingsAdapter(thingsList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        FileInputStream in = null;
        BufferedReader reader = null;                                            //为什么声明都写在前面？
        //之后设置监听，fab监听为新增任务
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WriteActivity.class);
                startActivityForResult(intent,4);
            }
        });
        //现在还无法排序！！！！！！！！！！！！！！！！！！！！！！！！！！！！

        //获得初始时候还剩下多少todo
         Num = String.valueOf(thingsList.size());
        undoNum.setText(Num);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            Toast.makeText(MainActivity.this, "IT's null", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (resultCode == 1) {
                // Things recThing = (Things) (this.getIntent().getSerializableExtra("thing"));
                //thingsList.add(recThing);
                int code = resultCode;
                String str = data.getStringExtra("obj");
                Log.e("msg",str);
                String getContent, getTime;
                String getColor, getClockTime;
                //获取当前id
                getContent = data.getStringExtra("content");
                getTime = data.getStringExtra("time");
                getColor = data.getStringExtra("color");
                getClockTime = data.getStringExtra("clockTime");
                Things recThing = new Things();//getColor
                recThing.setColor(getColor);
                recThing.setContent(getContent);
                recThing.setTime(getTime);
                recThing.setnum(thingsList.size());
                recThing.setClockTime(getClockTime);
                //将对象储存至数据库
                recThing.save();
                //添加到list用于显示
                thingsList.add(recThing);
                Num = String.valueOf(thingsList.size());
                undoNum.setText(Num);
                adapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    protected void onDestroy() {
        for (Things th : thingsList)
            th.save();
        Toast.makeText(MainActivity.this, "Bye bye", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        Log.e("Ondestroy","I am running");


    }

}


