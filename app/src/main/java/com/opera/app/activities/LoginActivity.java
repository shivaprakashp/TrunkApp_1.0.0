package com.opera.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.ErrorDialogue;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.preferences.SessionManager;
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

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.btnSend)
    ButtonWithFont btnSend;

    BottomSheetBehavior sheetBehavior;

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            if (response.body() != null) {
                loginSession((LoginResponse) response.body());
            } else if (response.errorBody() != null) {
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
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

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        ((MainApplication) getApplication()).getNetComponent().inject(LoginActivity.this);
        api = retrofit.create(Api.class);

        //edittext
        username = (EditTextWithFont) login_username.findViewById(R.id.edt);
        username.setHint(getString(R.string.username));
        username.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        password = (EditTextWithFont) login_password.findViewById(R.id.edt);
        password.setHint(getString(R.string.password));
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @OnClick({R.id.tv_forgotPassword, R.id.btnRegister, R.id.textView_continue_as_guest,
            R.id.btnLogin, R.id.btnSend})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgotPassword:
                //showDialog();
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;

            case R.id.btnSend:
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;

            case R.id.btnRegister:
                openActivity(mActivity, RegisterActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivity(mActivity, MainActivity.class);
                break;

            case R.id.btnLogin:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (checkValidation()) sendPost(
                            username.getText().toString(),
                            password.getText().toString());
                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkValidation() {
        //Removing previous validations
        username.setError(null);
        password.setError(null);
        //validation of input field
        if (TextUtils.isEmpty(username.getText().toString().trim()) && TextUtils.isEmpty(password.getText().toString().trim())) {
            username.setError(getString(R.string.errorUserName));
            password.setError(getString(R.string.errorPassword));
            return false;
        } else if (TextUtils.isEmpty(username.getText().toString().trim())) {
            username.setError(getString(R.string.errorUserName));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString().trim()).matches()) {
            username.setError(getString(R.string.errorUserEmail));
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError(getString(R.string.errorPassword));
            return false;
        }

        return true;
    }

    private void sendPost(String emailId, String pwd) {

        MainController controller = new MainController(LoginActivity.this);
        controller.loginPost(taskComplete, api, new PostLogin(emailId, pwd));
    }

    //maintain login session
    private void loginSession(LoginResponse loginResponse) {
        try {
            SessionManager sessionManager = new SessionManager(mActivity);
            sessionManager.createLoginSession(loginResponse);
            if (sessionManager.isUserLoggedIn()) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
