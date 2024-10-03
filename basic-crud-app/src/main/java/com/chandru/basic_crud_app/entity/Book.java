package com.chandru.basic_crud_app.entity;

import com.chandru.basic_crud_app.interfaces.Interleavable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book implements Comparable<Book>, Interleavable {

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Id
    @Column(name = "pnrId")
    private String pnrId;

    @Column(name = "count")
    private int count;

    @Column(name = "initialCount")
    private final int initialCount;

    public Book (String pnrId, int initialCount, String source, String destination) {
        this.pnrId = pnrId;
        this.initialCount = initialCount;
        this.count = initialCount;
        this.source = source;
        this.destination = destination;
    }

    public Book (String source, String destination) {
        this.source = source;
        this.destination = destination;
        this.count = this.initialCount = 0;
        this.pnrId = "0";
    }

    public Book () {
        this.initialCount = 0;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPnrId() {
        return pnrId;
    }

    public void setPnrId(String pnrId) {
        this.pnrId = pnrId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getInitialCount() {
        return initialCount;
    }

//    @Override
//    public String toString() {
//        return "Book{" +
//                "source='" + source + '\'' +
//                ", destination='" + destination + '\'' +
//                ", pnrId='" + pnrId + '\'' +
//                ", count=" + count +
//                ", initialCount=" + initialCount +
//                '}';
//    }

    @Override
    public String toString() {
        return "Book{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public int compareTo(Book that) {
        return this.start() >= that.end() ? 1 : (this.end() <= that.start() ? -1 : 0);
    }

    @Override
    public int start() {
        return this.source.charAt(0) - 'A';
    }

    @Override
    public int end() {
        return this.destination.charAt(0) - 'A';
    }
}
