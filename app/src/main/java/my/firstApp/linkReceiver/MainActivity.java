/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.HashSet;

import my.firstApp.linkReceiver.handlers.MessageHandler;
import my.firstApp.linkReceiver.services.LinkToolsService;
import my.firstApp.linkReceiver.services.YoutubeToolsService;
import my.firstApp.linkReceiver.threads.Server;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends FullscreenActivity {
    private final Server server = Server.getInstance(new UrlHandler());

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main business logic
        showDeviceIP();
        server.start();
    }

    /**
     * Save Youtube_URI_unique_handler_switch status on click
     */
    public void onYoutubeSwitchClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(YOUTUBE_UNIQUE_HANDLER_ENABLE, mYoutubeUniqueHandlerSwitch.isChecked());
        editor.apply();
    }

    /**
     * Class for handle messages from Sockets over MessageBroker
     */
    class UrlHandler implements MessageHandler {
        private final LinkToolsService youtubeToolsService = new YoutubeToolsService();

        public void handle(String url) {
            try {
                Uri uri = Uri.parse(url);
                openURI(uri);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        protected void openURI(Uri uri) {
            if (mYoutubeUniqueHandlerSwitch.isChecked()
                    && isYoutubeHost(uri.getHost())) {
                viewURI(youtubeToolsService.tryGetDirectURI(uri));
                return;
            }
            viewURI(uri);
        }

        private void viewURI(Uri uri) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        private boolean isYoutubeHost(String host) {
            /*
             *  Hosts list from Youtube Android app settings
             */
            return new HashSet<String>() {{
                add("youtu.be");
                add("m.youtube.com");
                add("youtube.com");
                add("www.youtube.com");
            }}.contains(host.toLowerCase());
        }
    }

    /**
     * Show the first IP address of this device (for connecting sender devices)
     * in the center of the tap
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void showDeviceIP() {
        TextView textView = findViewById(R.id.fullscreen_content);
        String ipAddress = "";
        try {
            ipAddress = server.getNetworkService().getFirstAddress();
        } catch (Exception exception) {
            ipAddress = "unknown";
            System.out.println(exception.getMessage());
        } finally {
            textView.setText("Receiver IP Address: " + ipAddress);
        }
    }
}
