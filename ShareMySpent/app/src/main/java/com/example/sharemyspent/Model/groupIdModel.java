package com.example.sharemyspent.Model;

public class groupIdModel {
    String groupName,groupId;

    public groupIdModel(String groupName, String groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }
    public groupIdModel(){}

    public groupIdModel(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }



}
