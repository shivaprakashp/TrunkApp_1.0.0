package com.opera.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.CalendarDateRangeView;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.utils.OperaUtils;

public class CalendarActivity extends BaseActivity {

    private CalendarDateRangeView dateRangeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initView();
    }

    private void initView(){

        dateRangeView = (CalendarDateRangeView) findViewById(R.id.calView);
        findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
        TextViewWithFont txtToolbarName = (TextViewWithFont) findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(OperaUtils.getCurrentyearMonthDate());
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
