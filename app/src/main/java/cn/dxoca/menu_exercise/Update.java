package cn.dxoca.menu_exercise;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setTitle("Update");
        final TextView t2, t3;
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        Button bt;
        bt = findViewById(R.id.button2);

        final ProgressBar pb;
        pb = findViewById(R.id.progressBar);
        final int[] status = {0};//记录PB进度
        t2.setText("By:寒光 dxoca.cn");
        pb.setVisibility(View.INVISIBLE);//隐藏



        //创建一个更新进度的Handler
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //表面该消息是有该程序发送的
                if (msg.what == 0x111) {
                    pb.setProgress(status[0]);
                    if (status[0] == 100) {
                        t2.setText("已经是最新版本  By:寒光 dxoca.cn");
                        t3.setText("DO 1.0.0 Beta");
                        pb.setVisibility(View.INVISIBLE);//隐藏
                    }
                }
            }
        };
        bt.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setText("正在检查更新...");
                pb.setVisibility(View.VISIBLE);//显示
                status[0]=0;
                pb.setProgress(status[0]);
                //启动线程执行任务
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (status[0] < 0x64) {
                            status[0]++;

                            doWork();//事件
                            //发送消息
                            handler.sendEmptyMessage(0X111);
                        }

                    }
                }.start();
            }
            //线程任务
            private void doWork() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
