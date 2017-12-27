package tech.xiwi.location;

/**
 * Created by xiwi on 2017/12/19.
 */
public interface XLogger {
    static final String TAG = "XLogger";

    public void setOpen(boolean open);

    public int i(String tag, String msg);

    public int d(String tag, String msg);

    public int w(String tag, String msg);

    public int e(String tag, String msg);
}
