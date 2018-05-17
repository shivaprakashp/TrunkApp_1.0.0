package com.opera.app.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.CalendarDateRangeView;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.utils.OperaUtils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CalendarActivity extends BaseActivity {

    @BindView(R.id.txtMonthTitle)
    TextViewWithFont txtMonthTitle;

    @BindView(R.id.imgVNavLeft)
    ImageView imgVNavLeft;

    @BindView(R.id.imgVNavRight)
    ImageView imgVNavRight;

    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;

    private Locale locale;
    private Context context;
    private Calendar currentCalendarMonth;

    HorizontalScrollView scrollView;
    LinearLayout dateLinLayout;

    int scrWidth ;
    //*** variable for number of dates in view
    int totalNumberElement = 9;
    int tileWidth;
    int margin;
    int todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        context = this;
        initView();

        currentCalendarMonth = OperaUtils.getCurrentMonth();
        initCalendar(currentCalendarMonth);
    }

    private void initView(){
        locale = context.getResources().getConfiguration().locale;

        todayDate = OperaUtils.getCurrentMonth().get(Calendar.DAY_OF_MONTH);

        scrWidth = getResources().getDisplayMetrics().widthPixels;
        // pass the total numbers of element you want to show in date view
        totalNumberElement = 9;
        calculateValues(scrWidth,totalNumberElement);

        dateLinLayout = (LinearLayout) findViewById(R.id.linLayDates);
        scrollView = (HorizontalScrollView) findViewById(R.id.hrsScroll);

        findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
        TextViewWithFont txtToolbarName = (TextViewWithFont) findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(OperaUtils.getCurrentyearMonthDate());

        scrollBarToDate(todayDate);
    }

    private void scrollBarToDate(final int todayDate){

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

    private void initCalendar(Calendar calendar){
        String dateText = new DateFormatSymbols(locale).getMonths()[calendar.get(Calendar.MONTH)];
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());
        txtMonthTitle.setText(dateText + " " + calendar.get(Calendar.YEAR));

        for (int i = 0 ; i < calendar.getActualMaximum(Calendar.DATE) ; i++){

            LinearLayout textBackLay = new LinearLayout(context);
            LinearLayout.LayoutParams textBackLayParam = new LinearLayout.LayoutParams(tileWidth,tileWidth);
            textBackLayParam.setMargins(margin,0,margin,0);
            textBackLay.setLayoutParams(textBackLayParam);
            textBackLay.setGravity(Gravity.CENTER);
            textBackLay.setOrientation(LinearLayout.HORIZONTAL);
            dateLinLayout.addView(textBackLay);
            dateLinLayout.setTag(R.string.TAG_TEXT,i);

            TextViewWithFont txtMonthDays = new TextViewWithFont(context);

            LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(scrWidth/12,scrWidth/12);
            txtMonthDays.setLayoutParams(textViewParam);
            txtMonthDays.setGravity(Gravity.CENTER);
            txtMonthDays.setText(""+(i + 1));

            txtMonthDays.setBackground(getResources().getDrawable(R.drawable.oval_corner));
            txtMonthDays.setTextAppearance(context, R.style.label_white_medium);

            if ( (i + 1) == todayDate){
                txtMonthDays.setBackground(getResources().getDrawable(R.drawable.oval_full_white_corner));
                txtMonthDays.setTextAppearance(context, R.style.label_burgendy_medium);

                scrollBarToDate(todayDate);
            }

            textBackLay.addView(txtMonthDays);
            textBackLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeOtherSelectedOnClick();

                    TextViewWithFont textView = (TextViewWithFont) ((LinearLayout) v).getChildAt(0);
                    textView.setBackground(getResources().getDrawable(R.drawable.oval_full_white_corner));
                    textView.setTextAppearance(context, R.style.label_burgendy_medium);
                }
            });
        }
    }

    private void removeOtherSelectedOnClick(){
        for (int i=0;i<dateLinLayout.getChildCount();i++){
            dateLinLayout.getChildAt(i).setBackground(null);
            ((TextView)((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.oval_corner));
            ((TextView)((LinearLayout)dateLinLayout.getChildAt(i)).getChildAt(0)).setTextAppearance(context, R.style.label_white_medium);
        }

    }

    @OnClick({R.id.imgVNavLeft, R.id.imgVNavRight})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgVNavLeft:
                currentCalendarMonth.add(Calendar.MONTH, -1);
                initCalendar(currentCalendarMonth);
                break;

            case R.id.imgVNavRight:
                currentCalendarMonth.add(Calendar.MONTH, 1);
                initCalendar(currentCalendarMonth);
                break;
        }

    }
    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
