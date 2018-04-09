package com.opera.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LoginActivity extends BaseActivity {

    private Activity mActivity;
    EditTextWithFont username, password;

    @BindView(R.id.tv_forgotPassword)
    TextView mTextForgotPwd;

    @BindView(R.id.btnRegister)
    Button mButtonRegister;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTextContinue_as_guest;

    @BindView(R.id.login_username)
    View login_username;

    @BindView(R.id.login_password)
    View login_password;

    @BindView(R.id.linearParent)
    LinearLayout mLinearParent;

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            Log.i("response", response.body().toString());
        }

        @Override
        public void onTaskError(Call call, Throwable t) {
            Log.e("Error", call.toString());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = LoginActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        ((MainApplication) getApplication()).getNetComponent().inject(LoginActivity.this);

        api = retrofit.create(Api.class);

        //edittext
        username = (EditTextWithFont) login_username.findViewById(R.id.edt);
        username.setHint(getString(R.string.username));

        password = (EditTextWithFont) login_password.findViewById(R.id.edt);
        password.setHint(getString(R.string.password));

    }

    @OnClick({R.id.tv_forgotPassword,R.id.btnRegister,R.id.textView_continue_as_guest,R.id.btnLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgotPassword:

                showDialog();

                break;

            case R.id.btnRegister:
                openActivity(mActivity, RegisterActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivity(mActivity, MainActivity.class);
                break;

            case R.id.btnLogin:
                //openActivity(mActivity, MainActivity.class);

                if(Connections.isConnectionAlive(mActivity)){
                    if (checkValidation()) sendPost("manishramanan15@gmail.com", "q1_(0MnpgTK+*g");
                }else{
                    OperaUtils.getSnackbar(username, getResources().getString(R.string.internet_error_msg)).show();
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!OperaUtils.isEmailAddress(mActivity, username, true, getString(R.string.enter_username))) ret = false;
        else if (!OperaUtils.hasText(password, getString(R.string.enter_password))) ret = false;

        return ret;
    }

    private void sendPost(String emailId, String pwd){

        MainController controller = new MainController(LoginActivity.this);
        controller.loginPost(taskComplete, api, new PostLogin(emailId, pwd));
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        dialog.setContentView(R.layout.popup_forgotpassword);

        TextView tv_forgotPassword = (TextView) dialog.findViewById(R.id.tv_forgotPassword);
        EditText et_username = (EditText) dialog.findViewById(R.id.et_username);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSend);

        dialog.show();

    }


}
