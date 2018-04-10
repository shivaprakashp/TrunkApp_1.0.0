package com.opera.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by 1000779 on 3/22/2018.
 */

public class BaseFragment extends Fragment {

    FragmentNavigation mFragmentNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

    public void openActivity(Activity activity, Class<?> startClass) {
        Intent intent = new Intent(activity, startClass);
        activity.startActivity(intent);
        //finish();
    }
}
