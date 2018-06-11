package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.registration.RegistrationResponse;
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
    private SessionManager mSessionManager;

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

    private EditTextWithFont forgotPassword;
    private EventListingDB mEventListingDB;
    private BottomSheetDialog dialog;
    //injecting retrofit
    @Inject
    Retrofit retrofit;
    String emailPattern = "[\\u0621-\\u064A\\u0660-\\u0669a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[\\u0621-\\u064A\\u0660-\\u0669a-zA-Z0-9][\\u0621-\\u064A\\u0660-\\u0669a-zA-Z0-9\\-]{0,64}(\\.[\\u0621-\\u064A\\u0660-\\u0669a-zA-Z0-9][\\u0621-\\u064A\\u0660-\\u0669a-zA-Z0-9\\-]{0,25})+";

    private Api api;
    private CustomToast customToast;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            ErrorDialogue dialogue;
            if (mRequestKey.equals(AppConstants.LOGIN.LOGIN)) {
                if (response.body() != null) {
                    mEventListingDB.open();
                    mSessionManager.DeleteAllTables(mActivity);
//                    mEventListingDB.deleteCompleteTable(mEventListingDB.TABLE_EVENT_LISTING);
                    loginSession((LoginResponse) response.body());
                } else if (response.errorBody() != null) {
                    try {
                        dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        customToast.showErrorToast(e.getMessage());
                    }
                }
            } else if (mRequestKey.equals(AppConstants.FORGOTPASSWORD.FORGOTPASSWORD)) {
                if (response.body() != null) {
                    RegistrationResponse mPostChangePassword = (RegistrationResponse) response.body();
                    if (mPostChangePassword.getStatus().equalsIgnoreCase("success")) {
                        /*SessionManager sessionManager = new SessionManager(mActivity);
                        sessionManager.clearLoginSession();*/
                        SuccessDialogue dialog = new SuccessDialogue(mActivity, getResources().getString(R.string.ForgotPsswordSuccessMsg), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "forgot_password");
                        dialog.show();
                    } else {
                        dialogue = new ErrorDialogue(mActivity, mPostChangePassword.getMessage());
                        dialogue.show();
                    }

                } else if (response.errorBody() != null) {
                    try {
                        dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        customToast.showErrorToast(e.getMessage());
                    }
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
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
        initBottomSlideDown();
    }

    private void initView() {
        ((MainApplication) getApplication()).getNetComponent().inject(LoginActivity.this);
        api = retrofit.create(Api.class);
        mSessionManager=new SessionManager(mActivity);
        customToast = new CustomToast(mActivity);
        mEventListingDB = new EventListingDB(mActivity);
        //edittext
        username = (EditTextWithFont) login_username.findViewById(R.id.edt);
        username.setHint(getString(R.string.email2));
        username.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        username.setSingleLine(true);
        username.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(50)});
        username.requestFocus();

        password = (EditTextWithFont) login_password.findViewById(R.id.edt);
        password.setHint(getString(R.string.password));
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        password.setImeOptions(EditorInfo.IME_ACTION_DONE);

    }

    private void initBottomSlideDown() {
        dialog = new BottomSheetDialog(mActivity);
        View view = getLayoutInflater().inflate(R.layout.popup_forgotpassword, null);
        dialog.setContentView(view);

        forgotPassword = (EditTextWithFont) view.findViewById(R.id.edtForgotEmail);

        forgotPassword.requestFocus();
        forgotPassword.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(50)});
        forgotPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);

        view.findViewById(R.id.imgClose).setOnClickListener(imageClose);
        view.findViewById(R.id.btnSend).setOnClickListener(clickEmail);
    }

    private View.OnClickListener imageClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener clickEmail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Connections.isConnectionAlive(mActivity)) {
                if (checkValidationForgotPassword()) {
                    sendForgotPassword(forgotPassword.getText().toString().trim());
                    forgotPassword.setText("");
                }
            } else {
                //Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
            }
        }
    };

    @OnClick({R.id.tv_forgotPassword, R.id.btnRegister, R.id.textView_continue_as_guest,
            R.id.btnLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgotPassword:
                dialog.show();
                break;

            case R.id.btnRegister:
                openActivity(mActivity, RegisterActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
                break;

            case R.id.btnLogin:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (checkValidation()) sendPost(
                            username.getText().toString(),
                            password.getText().toString());
                } else {
                    //Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                }
                break;
            case R.id.imgClose:
                dialog.dismiss();
                break;
        }
    }

    private boolean checkValidationForgotPassword() {
        if (TextUtils.isEmpty(forgotPassword.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!forgotPassword.getText().toString().matches(emailPattern)) {
            customToast.showErrorToast(getString(R.string.errorEmailInvalid));
            return false;
        }

        return true;
    }

    private boolean checkValidation() {
        //Removing previous validations
        username.setError(null);
        password.setError(null);
        //validation of input field
        if (TextUtils.isEmpty(username.getText().toString().trim()) && TextUtils.isEmpty(password.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorLoginError));
            return false;
        } else if (TextUtils.isEmpty(username.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorUserName));
            return false;
        } else if (!username.getText().toString().matches(emailPattern)) {
            customToast.showErrorToast(getString(R.string.errorUserEmail));
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorPassword));
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
                openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendForgotPassword(String mEmail) {
        MainController controller = new MainController(mActivity);
        controller.forgotPassword(taskComplete, api, new ForgotPasswordPojo(mEmail));
    }
}
