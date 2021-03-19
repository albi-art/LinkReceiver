/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver/LinkReceiver
 */

package my.firstApp.linkReceiver.services;

import java.net.SocketException;
import java.util.ArrayList;

public interface NetworkService {
    String getFirstAddress();

    ArrayList<String> getAddressList() throws SocketException;
}
