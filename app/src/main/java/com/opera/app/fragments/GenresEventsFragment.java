package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;
import com.opera.app.utils.LanguageManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenresEventsFragment extends BaseFragment {

    private Activity mActivity;
    private Api api;
    @Inject
    Retrofit retrofit;

    @BindView(R.id.spinnerSelectGenre)
    Spinner mSpinnerSelectGenre;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerGenre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = getActivity();
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_genres_events, container, false);

        InitView(view);
        GetGenres();
        return view;
    }

    private void InitView(View view) {
        ButterKnife.bind(this, view);
    }

    private void GetGenres() {
        MainController controller = new MainController(mActivity);
//        controller.getGenresListing(taskComplete, api);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {

        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
        }
    };
}
