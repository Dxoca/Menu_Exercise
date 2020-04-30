package cn.dxoca.menu_exercise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    final Queue<Hitokoto> queueHit = new LinkedList<>();
    String[] str = new String[100];
    int i = 0;
    final char[] x = new char[1];//初始化a  setting设置
    TextView text_sentence;//句子类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏 一定在 下面这句之前
        setContentView(R.layout.activity_main);

        //取屏幕宽度
        final WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        final int width = wm.getDefaultDisplay().getWidth();

        final TextView text_hitokoto, text_from, text_temp;
        setTitle("Hitokoto");
        text_hitokoto = findViewById(R.id.text_hitokoto);
        text_from = findViewById(R.id.text_from);
        text_temp = findViewById(R.id.text_temp);
        text_sentence = findViewById(R.id.text_sentence);

        text_temp.setVisibility(View.INVISIBLE);//隐藏


        //状态栏透明 https://www.jianshu.com/p/d32c6a0a4310
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //菜单按钮绑定
        final Button menu ;
        menu=findViewById(R.id.button3);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(menu);
            }
        });

        //为history 准备 队列bug暂未解决
        queueHit.add(new Hitokoto(text_hitokoto, text_from, text_temp, x[0]));//有问题 并没有产生add 改用直接拿标签

//        str[i++]= text_hitokoto.getText().toString()+" ["+text_from.getText()+"]"; 这次是取最开始的 所以 不拿
//        System.out.println(str[i-1]);

        text_hitokoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一言 句子 来源 临时 把握宽度
                queueHit.add(new Hitokoto(text_hitokoto, text_from, text_temp, x[0]));
                System.out.println(queueHit.peek().getId());
                str[i++] = text_hitokoto.getText().toString() + "" + text_from.getText() + "";
                System.out.println(str[i - 1]);
            }
        });


//线程BUG 还未解决！！！
        text_temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

//                //调整宽度
//                int temp = text_temp.getWidth();
//                if (temp > width * 0.8) {
//                    text_hitokoto.setWidth((int) (width * 0.8));
//                } else {
//                    text_hitokoto.setWidth(temp);
//                }
                text_hitokoto.setWidth((int) (width * 0.8));//固定 边框！ 动态边框bug暂未修复 如上代码
            }
        });
    }

    /**
     * 数据回传
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {//setting
            x[0] = data.getCharExtra("x", 'a');
            text_sentence.setText("句子类型："+x[0]);
        }
    }

    /**
     * View 当前PopupMenu显示的相对View的位置
     * @param view 菜单按钮
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局绑定
        popupMenu.getMenuInflater().inflate(R.menu.bar_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.setting:
                        intent.setClass(MainActivity.this, Setting.class);
                        startActivityForResult(intent, 101);
                        break;
                    case R.id.history:
        //                int i = 0;
        //                String[] res = new String[queueHit.size()];
        //                Hitokoto temp;
                                //队列中的数据 去出到String  直接传对象遇到问题

        //                while (!queueHit.isEmpty()) {
        //                    temp = queueHit.poll();
        //                    res[i++] = temp.getHitokoto() + " [" + temp.getFrom() + "]";
        //                    System.out.println("+++++" + res[i - 1]);
        //                }
                        intent.setClass(MainActivity.this, History.class);
                        intent.putExtra("hitokoto", str);
                        startActivity(intent);
                        break;
                    case R.id.update:
                        intent.setClass(MainActivity.this, Update.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }
}


