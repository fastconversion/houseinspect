package com.houseinspect.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lalit on 1/6/2017.
 */
public class Notification {

    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("payload_type")
    @Expose
    private String payloadType;
    @SerializedName("payload")
    @Expose
    private Payload payload;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(String payloadType) {
        this.payloadType = payloadType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}