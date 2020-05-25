package com.example.showpost;

public class modelc1 {
    private String topicname;
    private String topicinfo;


    public modelc1(String nameOftopic, String topicinfo1) {
        this.topicname = nameOftopic;
        this.topicinfo=  topicinfo1;

    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname1) {
        this.topicname =topicname1;
    }

    public String  getTopicinfo() {
        return topicinfo;
    }

    public void setTopicinfo(String topicinfo) {
        this.topicinfo = topicinfo;
    }

}
