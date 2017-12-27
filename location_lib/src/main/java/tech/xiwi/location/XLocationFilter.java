package tech.xiwi.location;

/**
 * Created by xiwi on 2017/12/19.
 */

public interface XLocationFilter {
    boolean isUseful(LocationInfo location);
}
