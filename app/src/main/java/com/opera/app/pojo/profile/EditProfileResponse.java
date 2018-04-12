package com.opera.app.pojo.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.login.LoginResponseData;

public class EditProfileResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("data")
        @Expose
        private LoginResponseData data;

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

        public LoginResponseData getData() {
        return data;
    }

        public void setData(LoginResponseData data) {
        this.data = data;
    }
}
