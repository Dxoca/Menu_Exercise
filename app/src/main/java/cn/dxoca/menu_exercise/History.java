package cn.dxoca.menu_exercise;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {
    ListView list;
    Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
//        Queue<Hitokoto> hitokoto = (Queue<Hitokoto>) getIntent().getSerializableExtra("hitokoto");
        setTitle("History");
        String[] str = intent.getStringArrayExtra("hitokoto");
        int count = 0;//计数
        for (int i = 0; i < str.length; i++) {
            if (str[i] == null) break;
            count++;
            System.out.println(str[i]);
            System.out.println("+++");
        }
        String[] res=new String[count];
        for (int i = 0; i < res.length; i++) {
            res[i]=str[i];
        }
        list = findViewById(R.id.list);
        //定义数组适配器
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, res);
        list.setAdapter(myAdapter);
        //列表项目 单机 监听器
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                if (myToast != null) {
                    myToast.cancel();
                }
                myToast = Toast.makeText(History.this, id + ":" + str, Toast.LENGTH_LONG);
                myToast.show();
            }
        }));
        //列表项目 长按 监听器【对话框】
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder dialog=new AlertDialog.Builder(History.this);
                dialog.setTitle("复制提醒");
                dialog.setMessage("是否复制到剪切板")	;
                dialog.setPositiveButton("确定",null);
                dialog.setNegativeButton("取消",null);
                dialog.create();
                dialog.show();

                return false;
            }
        });


    }
}
