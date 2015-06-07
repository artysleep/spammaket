package com.sleep.spamfilter.blacklist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BlackList")
public class PhoneNumber {

    @Id
    @Column(name = "number")
    private long number;

    public PhoneNumber() {}

    public PhoneNumber(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
