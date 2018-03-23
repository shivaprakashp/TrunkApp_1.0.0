package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;
    private OperaUtils mOperaUtils=new OperaUtils();

    @BindView(R.id.btnEnglish)
    Button mButtonEnglish;

    @BindView(R.id.btnArabic)
    Button mButtonArabic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        InitView();
    }

    private void InitView() {
        mActivity = LanguageActivity.this;
        mButtonEnglish.setOnClickListener(this);
        mButtonArabic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnglish:

                mOperaUtils.StoreInSharedPreference(mActivity,mOperaUtils.mSelectedLanguage,mOperaUtils.mLanguageEnglish);
                in = new Intent(mActivity, PreLoginActivity.class);
                startActivity(in);

                break;

            case R.id.btnArabic:

                mOperaUtils.StoreInSharedPreference(mActivity,mOperaUtils.mSelectedLanguage,mOperaUtils.mLanguageArabic);
                in = new Intent(mActivity, PreLoginActivity.class);
                startActivity(in);

                break;
        }
    }
}
