package nl.tudelft.oopp.group54.models.requestentities;

public class BanIpRequest extends AbstractRequest {
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
        return "BanIpRequest{"
                + "userIp='" + userIp + '\''
                + '}';
    }
}
