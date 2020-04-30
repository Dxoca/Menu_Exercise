package cn.dxoca.menu_exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");
//        RadioButton a, b, c, d, e, f, g, h, i, j, k, l, m; 不需要定义RB
        RadioGroup item;
        Button bt;
        bt = findViewById(R.id.button);
        item = findViewById(R.id.item);
        final Intent intent = new Intent();

        final char[] x = new char[1];
        item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        x[0] = 'a';
                        break;
                    case R.id.radioButton2:
                        x[0] = 'b';
                        break;
                    case R.id.radioButton3:
                        x[0] = 'c';
                        break;
                    case R.id.radioButton4:
                        x[0] = 'd';
                        break;
                    case R.id.radioButton5:
                        x[0] = 'e';
                        break;
                    case R.id.radioButton6:
                        x[0] = 'f';
                        break;
                    case R.id.radioButton7:
                        x[0] = 'g';
                        break;
                    case R.id.radioButton8:
                        x[0] = 'h';
                        break;
                    case R.id.radioButton9:
                        x[0] = 'i';
                        break;
                    case R.id.radioButton10:
                        x[0] = 'j';
                        break;
                    case R.id.radioButton11:
                        x[0] = 'k';
                        break;
                    case R.id.radioButton12:
                        x[0] = 'l';
                        break;
                    case R.id.radioButton13:
                        x[0] = 'a';//默认a
                        break;
                }
                System.out.println("句子：" + x[0]);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(Setting.this, MainActivity.class);
                intent.putExtra("x", x[0]);
                final int REQUEST_CODE = 101;
//                startActivityForResult(intent, REQUEST_CODE);//回传activity
                setResult(101,intent);
                finish();
            }
        });

    }
}
