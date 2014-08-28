package aga.rssparser;

/**
 * @author artem
 * Created on 16.08.14.
 */
public class RSSReadException extends Exception {
    public RSSReadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
