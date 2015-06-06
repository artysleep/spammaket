package com.sleep.spamfilter;


import javax.persistence.*;
import java.io.Serializable;

//  хранимая статистика о количестве всех сообщений
@Entity
@Table(name= "MessagesInfo")
public class MessagesInfo implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name= "spamMessageCount")
    private long spamMessageCount;
    @Column(name= "hamMessageCount")
    private long hamMessageCount;

    public MessagesInfo() {
        this(0,0);
    }


    public MessagesInfo(long spamMessageCount, long hamMessageCount) {
        this.spamMessageCount = spamMessageCount;
        this.hamMessageCount = hamMessageCount;
    }

    public void incCounter(long messageCount, boolean isSpam){
        if (isSpam){
            spamMessageCount += messageCount;
        }
        else {
            hamMessageCount += messageCount;
        }
    }

    public long getSpamMessageCount(){
        return spamMessageCount;
    }
    public long getHamMessageCount(){
        return hamMessageCount;
    }


    @Override
    public String toString() {
        return  spamMessageCount + "/" + hamMessageCount;
    }
}
