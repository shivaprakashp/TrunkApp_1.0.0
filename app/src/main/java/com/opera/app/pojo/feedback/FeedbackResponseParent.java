package com.opera.app.pojo.feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackResponseParent {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("notification")
    @Expose
    private ArrayList<FeedbackResponse> mFeedbackResponses;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<FeedbackResponse> getmFeedbackResponses() {
        return mFeedbackResponses;
    }

    public void setmFeedbackResponses(ArrayList<FeedbackResponse> mFeedbackResponses) {
        this.mFeedbackResponses = mFeedbackResponses;
    }
}
