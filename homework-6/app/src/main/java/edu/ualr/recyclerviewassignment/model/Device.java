package edu.ualr.recyclerviewassignment.model;

import androidx.annotation.NonNull;

import java.util.Date;


public class Device extends Item {
    private String name;
    private DeviceType deviceType;
    private Date lastConnection;
    private DeviceStatus deviceStatus;

    public Device(@NonNull String deviceId) {
        this.lastConnection = null;
        this.name = "";
        this.deviceStatus = DeviceStatus.Linked;
        this.deviceType = DeviceType.Unknown;

        this.section = false;
    }

    public Device (final Device device) {
        name = device.getName();
        deviceStatus = device.getDeviceStatus();
        deviceType = device.getDeviceType();
        Date timeStamp = device.getLastConnection();
        if (timeStamp == null) {
            lastConnection = null;
        } else {
            lastConnection = (Date) timeStamp.clone();
        }
    }


    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Date getLastConnection() {
        return this.lastConnection;
    }

    public void setLastConnection(Date timeStamp) {
        this.lastConnection = timeStamp;
    }

    public enum DeviceType {
        Unknown,
        Desktop,
        Laptop,
        Tablet,
        Smartphone,
        SmartTV,
        GameConsole
    }

    public enum DeviceStatus {
        Connected,
        Ready,
        Linked
    }
}
