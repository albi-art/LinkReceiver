/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.services;

import android.os.Build;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class IPv4Service implements NetworkService {
    private final String DEFAULT_ADDRESS = "127.0.0.1";
    private final String EXCLUSION_FILTER_REGEXP = "(0.0.0.0)|(127.0.0.1)|(.*:.*)";

    public String getFirstAddress() {
        try {
            return getAddressList().get(0);
        } catch (SocketException | IndexOutOfBoundsException e) {
            return DEFAULT_ADDRESS;
        }
    }

    public ArrayList<String> getAddressList() throws SocketException {
        ArrayList<NetworkInterface> interfaces = Collections
                .list(NetworkInterface.getNetworkInterfaces());

        return collectIpList(interfaces);
    }

    public ArrayList<String> collectIpList(ArrayList<NetworkInterface> interfaces) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //By Stream API (for SDK ver >= 24)
            return (ArrayList<String>) interfaces.stream()
                    .flatMap(i -> Collections.list(i.getInetAddresses()).stream()
                            .map(InetAddress::getHostAddress))
                    .filter(ip -> !ip.matches(EXCLUSION_FILTER_REGEXP))
                    .collect(Collectors.toList());
        }

        ArrayList<String> ipList = new ArrayList<>();
        for (NetworkInterface networkInterface : interfaces) {
            ArrayList<InetAddress> addressList = Collections
                    .list(networkInterface.getInetAddresses());
            for (InetAddress inetAddress : addressList) {
                String ip = inetAddress.getHostAddress();
                if (ip.matches(EXCLUSION_FILTER_REGEXP)) continue;

                ipList.add(inetAddress.getHostAddress());
            }
        }

        return ipList;
    }
}
