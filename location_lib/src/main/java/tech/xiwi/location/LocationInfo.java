package tech.xiwi.location;

import java.io.Serializable;

/**
 * Created by xiwi on 2017/12/19.
 */

public class LocationInfo implements Serializable {
    public double lat;
    public double lon;
    public String time;
    public float radius;
    public int satelites;

    public String city;

    @Override
    public String toString() {
        return "LocationInfo{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", time='" + time + '\'' +
                ", radius=" + radius +
                ", satelites=" + satelites +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationInfo that = (LocationInfo) o;

        if (Double.compare(that.lat, lat) != 0) return false;
        if (Double.compare(that.lon, lon) != 0) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

}
