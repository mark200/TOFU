package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class BanIpRequest implements Serializable {
    String userIp;

    public BanIpRequest(String userIp) {
        this.userIp = userIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Override
    public String toString() {
        return "BanIpRequest{" +
                "userIp='" + userIp + '\'' +
                '}';
    }
}
