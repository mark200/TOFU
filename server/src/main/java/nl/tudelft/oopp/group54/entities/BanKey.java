package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BanKey implements Serializable, Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_ip", nullable = false)
    private String banned_ip;

    @Column(name = "lecture_id", nullable = false)
    private int lecture_id;

    public BanKey(String banned_ip, int lecture_id) {
        this.banned_ip = banned_ip;
        this.lecture_id = lecture_id;
    }

    public BanKey() {}

    public String getBanned_ip() {
        return banned_ip;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setBanned_ip(String banned_ip) {
        this.banned_ip = banned_ip;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    //TODO: implement this compare correctly.
    @Override
    public int compareTo(Object o) {
        BanKey that = (BanKey) o;
        return Integer.valueOf(banned_ip).compareTo(Integer.valueOf(that.getBanned_ip())) &
                Integer.valueOf(lecture_id).compareTo(Integer.valueOf(that.getLecture_id()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanKey banKey = (BanKey) o;
        return getLecture_id() == banKey.getLecture_id() &&
                Objects.equals(getBanned_ip(), banKey.getBanned_ip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBanned_ip(), getLecture_id());
    }
}
