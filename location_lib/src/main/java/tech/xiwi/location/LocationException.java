package tech.xiwi.location;

/**
 * Created by xiwi on 2017/12/19.
 */
public class LocationException extends RuntimeException {
    public LocationException(String message) {
        super(message);
    }

    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
