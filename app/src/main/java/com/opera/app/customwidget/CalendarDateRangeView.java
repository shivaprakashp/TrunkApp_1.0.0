package com.opera.app.customwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.opera.app.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CalendarDateRangeView extends LinearLayout {

    private Context mContext;
    private Calendar currentCalendarMonth;
    private ImageView imgVNavLeft, imgVNavRight;
    private Locale locale;
    private TextViewWithFont txtMonthTitle;
    HorizontalScrollView scrollView;
    LinearLayout dateLinLayout;

    int scrWidth ;
    //*** variable for number of dates in view
    int totalNumberElement = 9;
    int tileWidth;
    int margin;

    public CalendarDateRangeView(Context context) {
        super(context);
        initView(context, null);
    }

    public CalendarDateRangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CalendarDateRangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarDateRangeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        mContext = context;
        locale = context.getResources().getConfiguration().locale;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        LinearLayout mainView = (LinearLayout) layoutInflater.inflate(R.layout.layout_calendar_month, this, true);

        scrWidth = getResources().getDisplayMetrics().widthPixels;
        dateLinLayout = mainView.findViewById(R.id.linLayDates);
        scrollView = mainView.findViewById(R.id.hrsScroll);
        txtMonthTitle = mainView.findViewById(R.id.txtMonthTitle);
        imgVNavLeft = mainView.findViewById(R.id.imgVNavLeft);
        imgVNavRight = mainView.findViewById(R.id.imgVNavRight);

        setListeners();

        if (isInEditMode()) {
            return;
        }

        // pass the total numbers of element you want to show in date view
        totalNumberElement = 9;
        calculateValues(scrWidth,totalNumberElement);


        drawCalendarForMonth(getCurrentMonth(Calendar.getInstance()));

    }

    private void calculateValues(int screenWidth, int totalEle) {

        tileWidth = screenWidth/totalEle;
        margin = tileWidth/totalEle;
        tileWidth = tileWidth - margin*2;


    }

    private void setListeners() {

        imgVNavLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalendarMonth.add(Calendar.MONTH, -1);
                Log.v("Hello", "Nav after: " + currentCalendarMonth.getTime().toString());
                drawCalendarForMonth(currentCalendarMonth);
            }
        });
        imgVNavRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalendarMonth.add(Calendar.MONTH, 1);
                Log.v("Hello", "Nav after: " + currentCalendarMonth.getTime().toString());
                drawCalendarForMonth(currentCalendarMonth);
            }
        });
    }

    private void drawCalendarForMonth(Calendar month) {

        Log.v("Hello", "Current cal: " + month.getTime().toString());
        currentCalendarMonth = (Calendar) month.clone();
        int startDay = month.get(Calendar.DAY_OF_WEEK);

        String dateText = new DateFormatSymbols(locale).getMonths()[currentCalendarMonth.get(Calendar.MONTH)];
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());
        txtMonthTitle.setText(dateText + " " + currentCalendarMonth.get(Calendar.YEAR));

        month.add(Calendar.DATE, -startDay + 1);

        dateLinLayout.removeAllViews();
        for (int i = 0 ; i < 30 ; i++){

            LinearLayout textBackLay = new LinearLayout(getContext());
            LinearLayout.LayoutParams textBackLayParam = new LinearLayout.LayoutParams(tileWidth,tileWidth);
            textBackLayParam.setMargins(margin,0,margin,0);
            textBackLay.setLayoutParams(textBackLayParam);
            textBackLay.setGravity(Gravity.CENTER);
            textBackLay.setOrientation(LinearLayout.HORIZONTAL);
            dateLinLayout.addView(textBackLay);
            textBackLay.setTag(R.string.TAG_TEXT,i);
            //textBackLay.setOnClickListener(this);

            TextViewWithFont txtMonthDays = new TextViewWithFont(getContext());
            LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(scrWidth/12,scrWidth/12);
            txtMonthDays.setLayoutParams(textViewParam);
            txtMonthDays.setGravity(Gravity.CENTER);
            txtMonthDays.setText(""+i);
            txtMonthDays.setBackground(getResources().getDrawable(R.drawable.oval_corner));
            txtMonthDays.setTextAppearance(getContext(), R.style.label_white_medium);
            textBackLay.addView(txtMonthDays);

        }

    }

    private Calendar getCurrentMonth(Calendar calendar) {
        Calendar current = (Calendar) calendar.clone();
        current.set(Calendar.DAY_OF_MONTH, 1);
        return current;
    }
}
