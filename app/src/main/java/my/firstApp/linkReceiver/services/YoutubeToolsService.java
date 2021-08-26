/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package my.firstApp.linkReceiver.services;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import my.firstApp.linkReceiver.exceptions.ParseYTVideoIdException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YoutubeToolsService implements LinkToolsService {
    /**
     * Try to get a direct link to stream the Youtube video
     */
    public Uri tryGetDirectURI(Uri uri) {
        OkHttpClient client = new OkHttpClient();

        String videoId;
        try {
            videoId = parseYoutubeVideoId(uri);
        } catch (ParseYTVideoIdException e) {
            e.printStackTrace();
            return uri;
        }
        final String url = LINK_SHARE_TOOLS_API_URI + "/youtube/get-video-info?id=" + videoId;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonRoot = new JSONObject(responseBody);
                String streamUrlRaw = LINK_SHARE_TOOLS_API_URI
                        + jsonRoot.get("stream_hls_url").toString();
                return Uri.parse(streamUrlRaw);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return uri;
    }

    /**
     *
     * Parse video ID from URI of one of the formats:
     *
     *      youtu.be/<video_id>
     *      m.youtube.com/watch?v=<video_id>
     *      youtube.com/watch?v=<video_id>
     *      www.youtube.com/watch?v=<video_id>
     */
    private String parseYoutubeVideoId(Uri uri) throws ParseYTVideoIdException {
        List<String> uriPathSegments = uri.getPathSegments();
        if (uriPathSegments.size() == 0) {
            throw new ParseYTVideoIdException();
        }

        String firstPathSegment = uriPathSegments.get(0);
        return firstPathSegment.equals("watch")
                ? uri.getQueryParameter("v")
                : firstPathSegment;
    }
}
