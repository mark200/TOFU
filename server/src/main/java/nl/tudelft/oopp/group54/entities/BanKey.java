package nl.tudelft.oopp.group54.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class BanKey implements Serializable, Comparable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_ip", nullable = false)
    private String bannedIP;

    @Column(name = "lecture_id", nullable = false)
    private int lectureID;

    public BanKey(String bannedIP, int lectureID) {
        this.bannedIP = bannedIP;
        this.lectureID = lectureID;
    }

    public BanKey() {

    }

    public String getBannedIP() {
        return bannedIP;
    }

    public int getLectureID() {
        return lectureID;
    }

    public void setBanned_ip(String bannedIP) {
        this.bannedIP = bannedIP;
    }

    public void setLecture_id(int lectureID) {
        this.lectureID = lectureID;
    }

    @Override
    public int compareTo(Object o) {
        BanKey that = (BanKey) o;
        return String.valueOf(bannedIP).compareTo(String.valueOf(that.getBannedIP()))
                & Integer.valueOf(lectureID).compareTo(Integer.valueOf(that.getLectureID()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BanKey banKey = (BanKey) o;
        return getLectureID() == banKey.getLectureID()
                && Objects.equals(getBannedIP(), banKey.getBannedIP());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBannedIP(), getLectureID());
    }
}
