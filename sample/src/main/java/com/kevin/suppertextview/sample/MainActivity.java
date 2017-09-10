package com.kevin.suppertextview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kevin.suppertextview.SupperTextView;

public class MainActivity extends AppCompatActivity {

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void click(View view) {

        SupperTextView tv7 = (SupperTextView) this.findViewById(R.id.tv_7);

        tv7.setTextColor(flag ?  getResources().getColor(R.color.color_00ff00) : getResources().getColor(R.color.color_3f51b5));
        tv7.setStrokeColor(flag ?  getResources().getColor(R.color.color_00ff00) : getResources().getColor(R.color.color_3f51b5));
        flag = !flag;
    }
}
