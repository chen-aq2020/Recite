package com.app.chen.recite;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    String filepath;
    String questions;
    String solution;
    HashMap hashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        filepath = Environment.getExternalStorageDirectory().getPath() + "/" + getpath();
        System.out.println(filepath);
    }

    private String getpath() {

        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/Recite/");
        File ff = new File(Environment.getExternalStorageDirectory().getPath() + "/Recite/path.txt");
        if (!f.exists()) {
            try {
                f.mkdirs();
                if (!ff.exists()) {
                    ff.createNewFile();
                    Toast.makeText(this, "请设置路径", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream path = new FileInputStream(ff);
            byte[] c = new byte[50];

            if (path.read() != -1) {
                try {
                    return new String(c, 0, path.read(c));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return "";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取路径
        return "";
    }//获取路径

    public void Random(View view) {

        String question = null;
        String answer = null;

        if (random(true).get("questions") == null) {
            question = "没有题目";
        } else {

            question = hashMap.get("questions").toString();

            if (hashMap.get("solution") == null) {
                solution = "没有答案";
            } else {

                answer = hashMap.get("solution").toString();
            }
        }


        //参数判空

        TextView textView = findViewById(R.id.question);
        textView.setText(question);
        textView = findViewById(R.id.answers_real);
        textView.setText(answer);

        TextView text = findViewById(R.id.answers_real);
        text.setText("");//清空
    }//获取题目

    public void Path_change(View view) {
        TextView t = findViewById(R.id.path_c);

        try {
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/Recite/path.txt");
            fileWriter.write(t.getText().toString());
            fileWriter.close();
            filepath = Environment.getExternalStorageDirectory().getPath() + t.getText().toString();
            System.out.println("filepath:" + filepath);
            Toast.makeText(this, "更改完成", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        solution = null;
    }

    public void answer(View view) {
        TextView answer = findViewById(R.id.answers_real);
        answer.setText(solution);
    }

    //获取随机题目和答案的方法
    public HashMap random(boolean put) {

        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
        System.out.println(Thread.currentThread().getStackTrace()[3].getLineNumber());

        List<String> l = new ArrayList();

        if(put){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getPath() + "/" + getpath()));
                String s = null;

                while ((s = bufferedReader.readLine()) != null) {
                    l.add(s);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Random r = new Random();

            if (l.size() > 0) {
                String line = l.get(r.nextInt(l.size()));
                if (line.contains(" ")) {
                    questions = line.substring(0, line.indexOf(" "));//截取问题
                    solution = line.substring(line.indexOf(" "));//截取答案
                    System.out.println("赋值");
                } else {
                    questions = line;
                    solution = null;
                }

                hashMap.put("questions", questions);
                hashMap.put("solution", solution);

                System.out.println("q" + hashMap.get("questions"));
                System.out.println("s" + hashMap.get("solution"));

                return hashMap;
        }else{

                System.out.println("q" + hashMap.get("questions"));
                System.out.println("s" + hashMap.get("solution"));

                return hashMap;
            }

        }
        return null;

    }
}