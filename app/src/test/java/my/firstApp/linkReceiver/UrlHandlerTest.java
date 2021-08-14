package my.firstApp.linkReceiver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UrlHandlerTest {
    MainActivity.UrlHandler urlHandler = new MainActivity() {
        public UrlHandler getUrlHandler() {
            return new UrlHandler();
        }
    }.getUrlHandler();

    @Test
    public void detectYoutubeHost_isCorrect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method privateMethod = MainActivity.UrlHandler.class
                .getDeclaredMethod("isYoutubeHost", String.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(urlHandler, "youtu.be"));
        assertTrue((boolean) privateMethod.invoke(urlHandler, "m.youtube.com"));
        assertTrue((boolean) privateMethod.invoke(urlHandler, "youtube.com"));
        assertTrue((boolean) privateMethod.invoke(urlHandler, "www.youtube.com"));

        assertFalse((boolean) privateMethod.invoke(urlHandler, "www.google.com"));
        assertFalse((boolean) privateMethod.invoke(urlHandler, "you.tu"));
        assertFalse((boolean) privateMethod.invoke(urlHandler, "you.tube"));
        assertFalse((boolean) privateMethod.invoke(urlHandler, "yout.ube"));

    }
}
