package com.example.sharemyspent.Model;

public class GroupUsers {
    String groupName,groupstatus;

    public GroupUsers(String groupName, String groupstatus) {
        this.groupName = groupName;
        this.groupstatus = groupstatus;
    }

    public GroupUsers(String groupName) {
        this.groupName = groupName;
    }

    public GroupUsers() {
    }

    public String getGroupstatus() {
        return groupstatus;
    }

    public void setGroupstatus(String groupstatus) {
        this.groupstatus = groupstatus;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
