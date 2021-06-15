package com.aprilinnovations.autoloungeindia.retrofitResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserServiceChatResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2759287114018631571L;

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public class Data implements Serializable {

        private List<UserServiceChatDetail> userServiceChatDetails = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = 3383376822885818581L;

        public List<UserServiceChatDetail> getUserServiceChatDetails() {
            return userServiceChatDetails;
        }

        public void setUserServiceChatDetails(List<UserServiceChatDetail> userServiceChatDetails) {
            this.userServiceChatDetails = userServiceChatDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


        public class UserServiceChatDetail implements Serializable {

            private String chatDataId;
            private String message;
            private String preference;
            private String profileImage;
            private String createTime;
            private String userName;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = 5800589032884099975L;

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

            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }

            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }

        }

    }

}