package com.ban.teacher;

public class ListResource {
    String a1_resource_title, a2_resource_link;

    public ListResource() {
    }

    public ListResource(String a1_resource_title, String a2_resource_link) {
        this.a1_resource_title = a1_resource_title;
        this.a2_resource_link = a2_resource_link;
    }

    public String getA1_resource_title() {
        return a1_resource_title;
    }

    public void setA1_resource_title(String a1_resource_title) {
        this.a1_resource_title = a1_resource_title;
    }

    public String getA2_resource_link() {
        return a2_resource_link;
    }

    public void setA2_resource_link(String a2_resource_link) {
        this.a2_resource_link = a2_resource_link;
    }
}
