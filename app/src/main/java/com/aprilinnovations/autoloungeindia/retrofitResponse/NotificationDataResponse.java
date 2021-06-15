package com.aprilinnovations.autoloungeindia.retrofitResponse;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NotificationDataResponse implements Serializable {

    private String status;
    private String message;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3910080428088314312L;

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

        private NotificationData notificationData;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private final static long serialVersionUID = 6060783598429369765L;

        public NotificationData getNotificationData() {
            return notificationData;
        }

        public void setNotificationData(NotificationData notificationData) {
            this.notificationData = notificationData;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class NotificationData implements Serializable {

            private String notificationDataId;
            private String createTime;
            private String compressedImage;
            private String description;
            private String title;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
            private final static long serialVersionUID = 5581971403463179663L;

            public String getNotificationDataId() {
                return notificationDataId;
            }

            public void setNotificationDataId(String notificationDataId) {
                this.notificationDataId = notificationDataId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCompressedImage() {
                return compressedImage;
            }

            public void setCompressedImage(String compressedImage) {
                this.compressedImage = compressedImage;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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