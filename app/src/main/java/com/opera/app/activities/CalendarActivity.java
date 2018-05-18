package com.opera.app.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.CalendarRecyclerView;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.CurrentDateCalender;
import com.opera.app.utils.OperaUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class CalendarActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.recyclerCalendar)
    RecyclerView recyclerCalendar;

    @BindView(R.id.linLayDates)
    LinearLayout dateLinLayout;

    Context context;

    @BindView(R.id.imgVNavRight)
    ImageView nextMonthIv;

    @BindView(R.id.imgVNavLeft)
    ImageView lastMonthIv;

    @BindView(R.id.txtMonthTitle)
    TextView monthTv;

    @BindView(R.id.hrsScroll)
    HorizontalScrollView scrollView;

    int currentMonth = 0,
            currentYear = 2018,
            todayDate =1,
            lastDateOfMonth=30,
            finalCompareTodayDate = 0,
            finalCompareTodayMonth = 0,
            scrWidth ;

    //*** variable for number of dates in view
    int totalNumberElement = 9,
            tileWidth,
            margin;

    private EventListingDB mEventDetailsDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private CalendarRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        context = this;

        initToolBar();
        initView();
        initCalendar();
        initDB();
    }

    private void initView(){
        scrWidth = getResources().getDisplayMetrics().widthPixels;
        nextMonthIv.setOnClickListener(this);
        lastMonthIv.setOnClickListener(this);
    }

    private void initCalendar(){
        // get current year、month and day

        currentYear = OperaUtils.getCurrentMonth().get(Calendar.YEAR);
        currentMonth = OperaUtils.getCurrentMonth().get(Calendar.MONTH);
        todayDate = OperaUtils.getCurrentMonth().get(Calendar.DATE);

        finalCompareTodayDate = todayDate;
        finalCompareTodayMonth = currentMonth;

        monthTv = (TextView)findViewById(R.id.txtMonthTitle);
        monthTv.setText(CurrentDateCalender.currentMonth(currentMonth)+" "+currentYear);

        //*** code for getting maximum day of month ***
        lastDateOfMonth  = Integer.parseInt(getDate(currentMonth,currentYear));

        // pass the total numbers of element you want to show in date view
        totalNumberElement = 9;
        calculateValues(scrWidth,totalNumberElement);

        updateValues(lastDateOfMonth,todayDate,currentMonth);
        scrollBarToDate();
    }

    private void initToolBar(){
        findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
        TextViewWithFont txtToolbarName = (TextViewWithFont) findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(OperaUtils.getCurrentyearMonthDate());
    }

    private void initDB(){
        mEventDetailsDB = new EventListingDB(context);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        adapter = new CalendarRecyclerView(mEventListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCalendar.setLayoutManager(mLayoutManager);
        recyclerCalendar.setItemAnimator(new DefaultItemAnimator());
        recyclerCalendar.setAdapter(adapter);
    }

    private void scrollBarToDate(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                int selectedDateX = (tileWidth)*(todayDate-1);
                // Scroll to the button.
                scrollView.scrollTo(selectedDateX, 0);
            }
        });

    }

    private void calculateValues(int screenWidth, int totalEle) {
        tileWidth = screenWidth/totalEle;
        margin = tileWidth/totalEle;
        tileWidth = tileWidth - margin*2;
    }

    @Override
    public void onClick(View v) {

        if (v == nextMonthIv){

            if (currentMonth == 11){

                currentMonth = 0;
                currentYear = currentYear+1;
                monthTv.setText(CurrentDateCalender.currentMonth(currentMonth)+" "+currentYear);

                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);


            }else {

                currentMonth++;
                monthTv.setText(CurrentDateCalender.currentMonth(currentMonth)+" "+currentYear);
                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);


            }

        }else if (v == lastMonthIv){

            if (currentMonth == 0) {

                currentYear = currentYear-1;
                currentMonth = 11;
                monthTv.setText(CurrentDateCalender.currentMonth(currentMonth)+" "+currentYear);

                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);


            }else {

                currentMonth--;
                monthTv.setText(CurrentDateCalender.currentMonth(currentMonth)+" "+currentYear);
                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);

            }

        }else{
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setColor(Color.WHITE);

            if (v instanceof TextView){

                removeOtherSelectedOnClick();
                v.setBackgroundDrawable(drawable);
                ((TextView) v).setTextColor(Color.BLACK);
                int selectedDate = (int) v.getTag(R.string.TAG_TEXT);
                Toast.makeText(getApplicationContext(),"Date Selected "+selectedDate,Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void removeOtherSelectedOnClick(){

        for (int i=0;i<dateLinLayout.getChildCount();i++){

            ((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0).setBackgroundDrawable(null);
            ((TextView)((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0)).setTextColor(Color.WHITE);

        }

    }

    private void updateValues(int lastDateOfMonth, int todayDate, int currentMonth){

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.WHITE);

        dateLinLayout.removeAllViews();

        for (int i=1;i<=lastDateOfMonth; i++){

            LinearLayout textBackLay = new LinearLayout(context);
            LinearLayout.LayoutParams textBackLayParam = new LinearLayout.LayoutParams(tileWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            textBackLayParam.setMargins(margin,0,margin,0);
            textBackLay.setLayoutParams(textBackLayParam);
            textBackLay.setGravity(Gravity.CENTER);
            textBackLay.setOrientation(LinearLayout.VERTICAL);
            dateLinLayout.addView(textBackLay);

            TextViewWithFont txtMonthDays = new TextViewWithFont(context);
            LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(scrWidth/12,scrWidth/12);
            txtMonthDays.setLayoutParams(textViewParam);
            txtMonthDays.setGravity(Gravity.CENTER);
            txtMonthDays.setText(""+i);
            txtMonthDays.setBackground(getResources().getDrawable(R.drawable.oval_corner));
            txtMonthDays.setTextAppearance(context, R.style.label_white_medium);
            textBackLay.addView(txtMonthDays);
            txtMonthDays.setTag(R.string.TAG_TEXT,i);
            txtMonthDays.setOnClickListener(this);

            LinearLayout dotLinLayout = new LinearLayout(context);
            LinearLayout.LayoutParams dotLinLayoutParam = new LinearLayout.LayoutParams(tileWidth/10,tileWidth/10);
            dotLinLayoutParam.setMargins(0,15,0,5);
            dotLinLayout.setLayoutParams(dotLinLayoutParam);
            dotLinLayout.setGravity(Gravity.CENTER);
            dotLinLayout.setOrientation(LinearLayout.VERTICAL);
            dotLinLayout.setBackgroundColor(Color.WHITE);
            textBackLay.addView(dotLinLayout);

            if (finalCompareTodayDate== todayDate && finalCompareTodayMonth == currentMonth && i == todayDate){
                txtMonthDays.setBackground(getResources().getDrawable(R.drawable.oval_full_white_corner));
                txtMonthDays.setTextAppearance(context, R.style.label_burgendy_medium);

                scrollBarToDate();
            }
        }

    }

    private static String getDate(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        calendar.set(year, month, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("dd");
        return DATE_FORMAT.format(date);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
