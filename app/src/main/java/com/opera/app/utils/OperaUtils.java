package com.opera.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;

import com.opera.app.R;

/**
 * Created by 1000779 on 2/3/2018.
 */

public class OperaUtils {

    private static OperaUtils mOperaUtils = null;

    public static String FONT_MONSTERRAT_LIGHT = "Montserrat-Light.ttf";

    public static String FONT_MONSTERRAT_MEDIUM = "Montserrat-Medium.ttf";

    public static String FONT_MONSTERRAT_REGULAR = "Montserrat-Regular.ttf";

    public static String FONT_MONSTERRAT_BOLD = "Montserrat-Bold.ttf";

    public static String OPERA_TWITTER_URL = "https://twitter.com/dubaiopera";

    public static String OPERA_INSTAGRAM_URL = "https://www.instagram.com/dubaiopera/";

    public static String OPERA_FACEBOOK_URL = "https://www.facebook.com/dubaiopera";

    //restricted user to create instance
    private OperaUtils() {
    }

    public static OperaUtils createInstance() {

        //initialize if instance is null
        //or else return instance
        if (mOperaUtils == null) {
            mOperaUtils = new OperaUtils();
        }

        return mOperaUtils;
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
}
