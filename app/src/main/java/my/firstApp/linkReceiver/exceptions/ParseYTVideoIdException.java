/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package my.firstApp.linkReceiver.exceptions;

public class ParseYTVideoIdException extends Exception {
    public ParseYTVideoIdException() {
        super("Can't find Youtube video ID in URI");
    }
}
