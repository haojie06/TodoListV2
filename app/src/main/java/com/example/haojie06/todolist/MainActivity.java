package com.example.haojie06.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    String Num;
    final List<Things> thingsList = new ArrayList<>();
    private  myBaseAdapter myAdapter;
     TextView undoNum;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        undoNum = (TextView)findViewById(R.id.undoNum);//显示未完成的任务数量

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
                //startActivityForResult();
            }
        });



            listView = (ListView)findViewById(R.id.list_view);
            //读取储存的信息
            try{
                in = openFileInput("DAT");
                reader = new BufferedReader(new InputStreamReader(in));
                    while (reader.readLine() != null){
                    String inputTitle,inputContent,inputTime;
                    String inputColor;
                    inputTitle = reader.readLine();
                    inputContent = reader.readLine();
                    inputTime = reader.readLine();
                    inputColor = reader.readLine();
                    Things inputThing = new Things(inputTitle,inputContent,inputTime,inputColor);//inputColor
                    thingsList.add(inputThing);
                }
                reader.close();
            }catch (IOException ex){ex.printStackTrace();}finally {

            }
            myAdapter = new myBaseAdapter(getApplicationContext(),thingsList);
             listView.setAdapter(myAdapter);
            Num = String.valueOf(thingsList.size());
            undoNum.setText(Num);
        //获得初始时候还剩下多少todo
         Num = String.valueOf(thingsList.size());
        undoNum.setText(Num);
        //长按完成任务！
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,int poistion, long l) {
                thingsList.remove(poistion);
                myAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,"Great!",Toast.LENGTH_SHORT).show();
                Num = String.valueOf(thingsList.size());
                undoNum.setText(Num);
                return false;
            }
        });

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

                String getTitle,getContent,getTime;
                String getColor;
                getTitle = data.getStringExtra("title");
                getContent = data.getStringExtra("content");
                getTime = data.getStringExtra("time");
                getColor = data.getStringExtra("color");
                Things recThing = new Things(getTitle,getContent,getTime,getColor);//getColor
                thingsList.add(recThing);
                Num = String.valueOf(thingsList.size());
                undoNum.setText(Num);
                myAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Ondestroy","I am running");
        Toast.makeText(MainActivity.this,"bye,bye~",Toast.LENGTH_SHORT).show();
        save(thingsList);

    }

    //文件储存
    public void save(List<Things> thingsList){
        int size = thingsList.size();
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("DAT",MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            for(int i = 0;i < size;i++)
            {   String title,content,time,color;
                title = thingsList.get(i).getTitle();
                content = thingsList.get(i).getContent();
                time = thingsList.get(i).getTime();
                color = thingsList.get(i).getColor();
                writer.newLine();
                writer.write(title);
                writer.newLine();
                writer.write(content);
                writer.newLine();
                writer.write(time);
               writer.newLine();
                writer.write(color);

            }
            writer.close();
        }catch (IOException ex){ex.printStackTrace();}
    }
}

