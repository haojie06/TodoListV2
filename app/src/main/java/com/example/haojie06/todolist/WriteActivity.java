package com.example.haojie06.todolist;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity  {

    private FloatingActionButton fab2;
    private EditText title,content;

    String saveTitle,saveContent,time,saveColor = "0";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        final EditText title = (EditText)findViewById(R.id.title_edit);
        final EditText content = (EditText)findViewById(R.id.content_edit);

        Button colorGray = (Button) findViewById(R.id.gray);
        Button colorGreen = (Button) findViewById(R.id.green);
        Button colorBlue = (Button) findViewById(R.id.blue);
        Button colorYellow = (Button) findViewById(R.id.yellow);
        Button colorRed = (Button) findViewById(R.id.red);

        final EditText editText = (EditText)findViewById(R.id.content_edit);


        //通过按钮设置标签颜色
        colorGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveColor = "0";
                editText.setBackgroundResource(R.drawable.writehuise);
                Toast.makeText(WriteActivity.this,"You choose Gray!",Toast.LENGTH_SHORT).show();
            }
        });

        colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveColor = "1";
                editText.setBackgroundResource(R.drawable.writelvse);
                Toast.makeText(WriteActivity.this,"You choose Green!",Toast.LENGTH_SHORT).show();
            }
        });

        colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setBackgroundResource(R.drawable.writelanse);
                saveColor = "2";
                Toast.makeText(WriteActivity.this,"You choose Blue!",Toast.LENGTH_SHORT).show();
            }
        });

        colorYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setBackgroundResource(R.drawable.writehuangse);
                saveColor = "3";
                Toast.makeText(WriteActivity.this,"You choose Yellow!",Toast.LENGTH_SHORT).show();
            }
        });

        colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setBackgroundResource(R.drawable.writehongse);
                saveColor = "4";
                Toast.makeText(WriteActivity.this,"You choose Red!",Toast.LENGTH_SHORT).show();
            }
        });


        fab2 = (FloatingActionButton)findViewById(R.id.fab_ok);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTitle = title.getText().toString();
                saveContent = content.getText().toString();
                Log.e("savetitle",saveTitle);
                Log.e("savecontent",saveContent);
                //获得写下todo的时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy--MM--dd");
                time = format.format(new Date());
                Intent intent = getIntent();
                intent.putExtra("obj","obj");
                intent.putExtra("title",saveTitle);
                intent.putExtra("content",saveContent);
                intent.putExtra("time",time);
                intent.putExtra("color",saveColor);
                setResult(1,intent);
                finish();

            }
        });
    }
}
