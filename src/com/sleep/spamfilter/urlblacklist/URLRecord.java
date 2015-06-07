package com.sleep.spamfilter.urlblacklist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "URL_BlackList")
public class URLRecord {

    @Id
    @Column(name = "url")
    private String url;

    public URLRecord() {}

    public URLRecord(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}