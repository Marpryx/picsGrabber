package com.marynaprykhodko.picsGrabber.Data;

public class DomainName {

    private String domain; //
    private String url;
    private String path;

    public DomainName(String domain, String url, String path) {
        this.domain = domain;
        this.url = url;
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }
}
