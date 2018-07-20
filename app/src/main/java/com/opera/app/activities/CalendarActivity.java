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

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.CalendarRecyclerView;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.CurrentDateCalender;
import com.opera.app.utils.OperaUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private CalendarRecyclerView adapter;
    private List<String> eventDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        context = this;

        initToolBar();
        initView();
        initDB();
        initCalendar();

    }

    private void initView(){
        scrWidth = getResources().getDisplayMetrics().widthPixels;
        nextMonthIv.setOnClickListener(this);
        lastMonthIv.setOnClickListener(this);

        eventDates = new ArrayList<>();
    }

    private void initCalendar(){
        // get current year、month and day
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        todayDate = calendar.get(Calendar.DATE);

        finalCompareTodayDate = todayDate;
        finalCompareTodayMonth = currentMonth;

        monthTv = findViewById(R.id.txtMonthTitle);
        monthTv.setText(new StringBuilder().append(CurrentDateCalender.currentMonth(currentMonth)).append(" ").append(currentYear).toString());

        //*** code for getting maximum day of month ***
        lastDateOfMonth  = Integer.parseInt(getDate(currentMonth,currentYear));

        // pass the total numbers of element you want to show in date view
        totalNumberElement = 9;
        calculateValues(scrWidth,totalNumberElement);

        updateValues(lastDateOfMonth,todayDate,currentMonth);
        scrollBarToDate();

        initRecycle(updateDate(todayDate, currentMonth, currentYear));
    }

    private void initToolBar(){
        findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
        TextViewWithFont txtToolbarName = findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(OperaUtils.getCurrentyearMonthDate());
    }

    private void initDB(){
        EventListingDB mEventDetailsDB = new EventListingDB(context);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        updateEventDates(mEventListingData);
    }

    private void updateEventDates(List<Events> eventsList){
        for (int i = 0 ; i < eventsList.size() ; i++){

            for (int k = 0 ; k < eventsList.get(i).getEventTime().size() ; k++ ){
                eventDates.add(eventsList.get(i).getEventTime().get(k).getFromTime().split("T")[0]);
            }
        }
    }

    private void initRecycle(String currentDay){
        List<Events> eventsList = new ArrayList<>();
        for ( int i = 0 ; i < mEventListingData.size() ; i++ ){
            for (int k = 0 ; k < mEventListingData.get(i).getEventTime().size() ; k++ ){
                if (currentDay.equalsIgnoreCase(mEventListingData.get(i).getEventTime().get(k).getFromTime().split("T")[0])){
                    eventsList.add(mEventListingData.get(i));
                }
            }
        }

        adapter = new CalendarRecyclerView(eventsList, currentDay);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCalendar.setLayoutManager(mLayoutManager);
        recyclerCalendar.setItemAnimator(new DefaultItemAnimator());
        recyclerCalendar.setAdapter(adapter);

       if (eventsList.size() != 0){
           adapter.notifyDataSetChanged();
       }else {
           adapter.notifyDataSetChanged();
       //    adapter = new CalendarRecyclerView(mEventListingData);
       }
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
                monthTv.setText(new StringBuilder().append(CurrentDateCalender.currentMonth(currentMonth)).append(" ").append(currentYear).toString());

                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);

                adapter.notifyDataSetChanged();
            }else {

                currentMonth++;
                monthTv.setText(new StringBuilder().append(CurrentDateCalender.currentMonth(currentMonth)).append(" ").append(currentYear).toString());
                lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                scrollView.fullScroll(View.FOCUS_LEFT);

                adapter.notifyDataSetChanged();
            }

        }else if (v == lastMonthIv){
            if (currentYear >= Calendar.getInstance().get(Calendar.YEAR)){
                if (currentMonth >= (Calendar.getInstance().get(Calendar.MONTH)) + 1){
                    if (currentMonth == 0) {

                        currentYear = currentYear-1;
                        currentMonth = 11;
                        monthTv.setText(new StringBuilder().append(CurrentDateCalender.currentMonth(currentMonth)).append(" ").append(currentYear).toString());

                        lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                        updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                        scrollView.fullScroll(View.FOCUS_LEFT);

                        adapter.notifyDataSetChanged();
                    }else {

                        currentMonth--;
                        monthTv.setText(new StringBuilder().append(CurrentDateCalender.currentMonth(currentMonth)).append(" ").append(currentYear).toString());
                        lastDateOfMonth = Integer.parseInt(getDate(currentMonth,currentYear));
                        updateValues(lastDateOfMonth,finalCompareTodayDate,currentMonth);
                        scrollView.fullScroll(View.FOCUS_LEFT);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        }else{
            if (v instanceof TextView){
                if (currentYear > Calendar.getInstance().get(Calendar.YEAR)){
                    updateSelectedDate(v);
                }else
                if (currentMonth > Calendar.getInstance().get(Calendar.MONTH)){
                    updateSelectedDate(v);
                }else
                if (Integer.valueOf(((TextView) v).getText().toString()) >= Calendar.getInstance().get(Calendar.DATE)){
                    updateSelectedDate(v);
                }
            }
        }
    }

    private void updateSelectedDate(View v){

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.WHITE);

        removeOtherSelectedOnClick();
        v.setBackground(drawable);
        ((TextView) v).setTextColor(Color.BLACK);
        int selectedDate = (int) v.getTag(R.string.TAG_TEXT);
        initRecycle(updateDate(selectedDate, currentMonth, currentYear));
    }

    private String updateDate( int date, int month, int year){

        String appendCalendar;

        if (String.valueOf(month).length()==1){
            appendCalendar = String.valueOf(year)+"0"+String.valueOf(month+1);
        }else {
            appendCalendar = String.valueOf(year)+String.valueOf(month+1);
        }


        if (String.valueOf(date).length()==1){
            appendCalendar = appendCalendar+"0"+String.valueOf(date);
        }else {
            appendCalendar = appendCalendar+String.valueOf(date);
        }

        return appendCalendar;
    }

    private void removeOtherSelectedOnClick(){

        for (int i=0;i<dateLinLayout.getChildCount();i++){
            ((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.oval_corner));
            ((TextView)((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0)).setTextAppearance(context, R.style.label_white_medium);
        }
    }

    private void updateValues(int lastDateOfMonth, int todayDate, int currentMonth){

        dateLinLayout.removeAllViews();

        for ( int i=1 ; i<=lastDateOfMonth; i++ ){

            LinearLayout textBackLay = new LinearLayout(context);
            LinearLayout.LayoutParams textBackLayParam = new LinearLayout.LayoutParams(tileWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            textBackLayParam.setMargins(margin,10,margin,0);
            textBackLay.setLayoutParams(textBackLayParam);
            textBackLay.setGravity(Gravity.CENTER);
            textBackLay.setOrientation(LinearLayout.VERTICAL);
            dateLinLayout.addView(textBackLay);

            TextViewWithFont txtMonthDays = new TextViewWithFont(context);
            LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(scrWidth/12,scrWidth/12);
            txtMonthDays.setLayoutParams(textViewParam);
            txtMonthDays.setGravity(Gravity.CENTER);
            txtMonthDays.setText(new StringBuilder().append("").append(i).toString());
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

            String appendData;
            if (String.valueOf(currentMonth).length()==1){
                appendData = currentYear + "0" + String.valueOf(currentMonth+1);
            }else{
                appendData = currentYear + String.valueOf(currentMonth+1);
            }
            if (String.valueOf(i).length()==1){
                appendData = appendData + "0" + String.valueOf(i);
            }else{
                appendData = appendData + String.valueOf(i);
            }
            Log.i("appendData", appendData);

            if (eventDates.contains(appendData)){
                dotLinLayout.setBackgroundColor(Color.WHITE);
            }else{
                dotLinLayout.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
            }
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
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.getActualMaximum(Calendar.DATE);
        return String.valueOf(calendar.getActualMaximum(Calendar.DATE));
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
