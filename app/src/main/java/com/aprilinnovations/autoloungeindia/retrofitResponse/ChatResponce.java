package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChatResponce implements Serializable
{


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetChatResponce.Data data;
    private final static long serialVersionUID = 3577730788892368318L;

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

    public GetChatResponce.Data getData() {
        return data;
    }

    public void setData(GetChatResponce.Data data) {
        this.data = data;
    }

    public class Data implements Serializable
    {

        @SerializedName("userServiceChatDetails")
        @Expose
        private List<GetChatResponce.Data.UserServiceChatDetail> userServiceChatDetails = null;
        private final static long serialVersionUID = -7446603874422792696L;

        public List<GetChatResponce.Data.UserServiceChatDetail> getUserServiceChatDetails() {
            return userServiceChatDetails;
        }

        public void setUserServiceChatDetails(List<GetChatResponce.Data.UserServiceChatDetail> userServiceChatDetails) {
            this.userServiceChatDetails = userServiceChatDetails;
        }

        public class UserServiceChatDetail implements Serializable
        {

            @SerializedName("chatDataId")
            @Expose
            private String chatDataId;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("preference")
            @Expose
            private String preference;
            @SerializedName("profileImage")
            @Expose
            private String profileImage;
            @SerializedName("createTime")
            @Expose
            private String createTime;
            @SerializedName("userName")
            @Expose
            private String userName;
            @SerializedName("chatImageUrl")
            @Expose
            private String chatImageUrl;
            private final static long serialVersionUID = -2447724763962221970L;

            public String getChatDataId() {
                return chatDataId;
            }

            public void setChatDataId(String chatDataId) {
                this.chatDataId = chatDataId;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getPreference() {
                return preference;
            }

            public void setPreference(String preference) {
                this.preference = preference;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getChatImageUrl() {
                return chatImageUrl;
            }

            public void setChatImageUrl(String chatImageUrl) {
                this.chatImageUrl = chatImageUrl;
            }

        }
    }

    /*@SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = -581068442595931693L;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data implements Serializable
    {

        @SerializedName("chatDetails")
        @Expose
        private ChatDetails chatDetails;
        private final static long serialVersionUID = -4084103818368355260L;

        public ChatDetails getChatDetails() {
            return chatDetails;
        }

        public void setChatDetails(ChatDetails chatDetails) {
            this.chatDetails = chatDetails;
        }

        public class ChatDetails implements Serializable
        {

            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("createTime")
            @Expose
            private String createTime;
            @SerializedName("userName")
            @Expose
            private String userName;
            @SerializedName("preference")
            @Expose
            private String preference;
            @SerializedName("chatDataId")
            @Expose
            private String chatDataId;
            @SerializedName("userServiceChatId")
            @Expose
            private String userServiceChatId;
            @SerializedName("profileImage")
            @Expose
            private String profileImage;
            private final static long serialVersionUID = -4413276169184755083L;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPreference() {
                return preference;
            }

            public void setPreference(String preference) {
                this.preference = preference;
            }

            public String getChatDataId() {
                return chatDataId;
            }

            public void setChatDataId(String chatDataId) {
                this.chatDataId = chatDataId;
            }

            public String getUserServiceChatId() {
                return userServiceChatId;
            }

            public void setUserServiceChatId(String userServiceChatId) {
                this.userServiceChatId = userServiceChatId;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

        }

    }*/

}


