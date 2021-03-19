/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver;

import org.junit.Test;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import my.firstApp.linkReceiver.services.IPv4Service;

import static org.junit.Assert.assertEquals;

public class IPv4ServiceTest {
    IPv4Service service = new IPv4Service();

    @Test
    public void firstIp_isCorrect() throws UnknownHostException {
        String ip = Inet4Address.getLocalHost().getHostAddress();
        assertEquals(ip, service.getFirstAddress());
    }
}
