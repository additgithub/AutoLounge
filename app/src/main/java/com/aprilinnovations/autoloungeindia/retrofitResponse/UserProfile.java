package com.aprilinnovations.autoloungeindia.retrofitResponse;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class UserProfile implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1857882638592751065L;

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

        private UserDetails userDetails;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = -6414504011698713625L;

        public UserDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class UserDetails implements Serializable {

            private String userId;
            private String authToken;
            private String profileImage;
            private String clientId;
            private String userName;
            private String emailId;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = 1877569995620929614L;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAuthToken() {
                return authToken;
            }

            public void setAuthToken(String authToken) {
                this.authToken = authToken;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getEmailId() {
                return emailId;
            }

            public void setEmailId(String emailId) {
                this.emailId = emailId;
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
