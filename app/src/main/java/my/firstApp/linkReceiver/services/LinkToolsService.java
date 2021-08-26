/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package my.firstApp.linkReceiver.services;

import android.net.Uri;

/**
 * The service ensures the operation of links tools
 */
public interface LinkToolsService {
    String LINK_SHARE_TOOLS_API_URI = "https://lst-api.herokuapp.com";

    /**
     * Try to get a direct link to make it easier to open
     */
    Uri tryGetDirectURI(Uri uri);
}
