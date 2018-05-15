package com.opera.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.opera.app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 1000779 on 2/3/2018.
 */

public class OperaUtils {

    private static String blockCharacterSet = "~#^|$%&*!1234567890@({)}:;?/.,'][-<>`~+=_";

    private static OperaUtils mOperaUtils = null;

    public static String FONT_MONSTERRAT_LIGHT = "Montserrat-Light.ttf";

    public static String FONT_MONSTERRAT_MEDIUM = "Montserrat-Medium.ttf";

    public static String FONT_MONSTERRAT_REGULAR = "Montserrat-Regular.ttf";

    public static String FONT_MONSTERRAT_BOLD = "Montserrat-Bold.ttf";

    public static String OPERA_TWITTER_URL = "https://twitter.com/dubaiopera";

    public static String OPERA_INSTAGRAM_URL = "https://www.instagram.com/dubaiopera/";

    public static String OPERA_FACEBOOK_URL = "https://www.facebook.com/dubaiopera";
    private Target loadtarget;

    //restricted user to create instance
    public OperaUtils() {
    }

    public static OperaUtils createInstance() {

        //initialize if instance is null
        //or else return instance
        if (mOperaUtils == null) {
            mOperaUtils = new OperaUtils();
        }

        return mOperaUtils;
    }

    public static void ShareEventDetails(Context mContext, String mBody, String mImage) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mBody);
        shareIntent.setType("image");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImage);
        mContext.startActivity(Intent.createChooser(shareIntent, "Select App to Share Text and Image"));
    }

    public static void CloseSoftKeyboard(Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void forceRTLIfSupported(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    //related to home bottom navigation icons
    public static Drawable setDrawableImage(Context context, int normal, int selected, int position) {

        StateListDrawable drawable = new StateListDrawable();

        Drawable state_normal = ContextCompat.getDrawable(context, normal);
        Drawable state_pressed = null;

        switch (position) {

            case 0:
                state_pressed = ContextCompat.getDrawable(context, R.drawable.ic_home_hover);
                break;

            case 1:
                state_pressed = ContextCompat.getDrawable(context, R.drawable.ic_events_hover);
                break;

            case 2:
                state_pressed = ContextCompat.getDrawable(context, R.drawable.ic_dining_hover);
                break;

            case 3:
                state_pressed = ContextCompat.getDrawable(context, R.drawable.ic_listern_hover);
                break;

            case 4:
                state_pressed = ContextCompat.getDrawable(context, R.drawable.ic_menu_hover);
                break;

            default:
        }

        drawable.addState(new int[]{android.R.attr.state_selected},
                state_pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                state_normal);

        return drawable;
    }

    public static void SelectGalleryImage(Activity mActivity, int PICK_IMAGE) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public static void SelectCameraImage(Activity mActivity, int CAMERA_REQUEST) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public static boolean CheckMarshmallowOrNot() {
        boolean IsMarshmallow;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Marshmallow+
            IsMarshmallow = true;
        } else {
            //below Marshmallow
            IsMarshmallow = false;
        }
        return IsMarshmallow;
    }

    public static void printException(Exception e) {
        e.printStackTrace();
        Log.e("Exception", " " + e.toString());
    }

    //stopped white space in edit text for password
    /*public static InputFilter filterSpaceExceptFirst = new InputFilter() {
        boolean canEnterSpace = false;

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if(editText.getText().toString().equals(""))
            {
                canEnterSpace = false;
            }
            //StringBuilder builder = new StringBuilder();
            for (int i = start; i < end; i++) {
                char currentChar = source.charAt(i);

                if (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
                    //builder.append(currentChar);
                    canEnterSpace = true;
                }
                if(Character.isWhitespace(currentChar) && canEnterSpace) {
                    //builder.append(currentChar);
                }
            }
            return null;
        }
    };*/

    //stopped white space in edit text for password
    public static InputFilter filterSpace = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (Character.isSpaceChar(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    //stopped white space in edit text for password
    public static InputFilter filterSpaceExceptFirst = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    if (dstart == 0)
                        return "";
                }
            }
            return null;
        }
    };

    public static InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    //get current date
    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c);
    }

    //get current date
    public static String getCurrentDate2() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c);
    }

    public static String[] splitDate() {
        return getCurrentDate().split("/");
    }

    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
