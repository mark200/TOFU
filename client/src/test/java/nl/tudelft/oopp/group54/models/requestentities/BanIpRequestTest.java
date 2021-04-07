package nl.tudelft.oopp.group54.models.requestentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BanIpRequestTest {
  BanIpRequest request;
  BanIpRequest request2;

  @BeforeEach
  void setUp() {
    request = new BanIpRequest("127.0.0.1");
    request2 = new BanIpRequest("192.168.1.1");
    request2.setUserIp("127.0.0.1");
  }

  @Test
  void getUserIp() {
    assertEquals("127.0.0.1", request.getUserIp());
  }

  @Test
  void testToString() {
    assertTrue(request.toString().contains("127.0.0.1"));
    assertEquals(request2.toString(), request.toString());
  }
}