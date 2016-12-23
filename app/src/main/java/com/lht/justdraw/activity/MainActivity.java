package com.lht.justdraw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lht.justdraw.R;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup mRadioGroup;
    RadioButton mRadio1000, mRadio5000, mRadio10000;

    int mCount = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = (RadioGroup)findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);

        mRadio1000 = (RadioButton) findViewById(R.id.radio_1000);
        mRadio5000 = (RadioButton) findViewById(R.id.radio_5000);
        mRadio10000 = (RadioButton) findViewById(R.id.radio_10000);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_1000:
                mCount = 1000;
                break;
            case R.id.radio_5000:
                mCount = 5000;
                break;
            case R.id.radio_10000:
                mCount = 10000;
                break;
        }
    }

    public void toNative(View view) {
        Intent intent = new Intent();
        intent.putExtra("count", mCount);
        intent.setClass(this, NativeActivity.class);
        startActivity(intent);
    }

    public void toNativePath(View view) {
        Intent intent = new Intent();
        intent.putExtra("count", mCount);
        intent.setClass(this, NativePathActivity.class);
        startActivity(intent);
    }

    public void toNativePro(View view) {
        Intent intent = new Intent();
        intent.putExtra("count", mCount);
        intent.setClass(this, NativeProActivity.class);
        startActivity(intent);
    }

    public void toWeb(View view) {
        Intent intent = new Intent();
        intent.putExtra("count", mCount);
        intent.setClass(this, WebActivity.class);
        startActivity(intent);
    }

    public void toV8(View view) {
        Intent intent = new Intent();
        intent.putExtra("count", mCount);
        intent.setClass(this, V8Activity.class);
        startActivity(intent);
    }

    public void toJC(View view) {
        Intent intent = new Intent();
        intent.setClass(this, JCDemoActivity.class);
        startActivity(intent);
    }
}
