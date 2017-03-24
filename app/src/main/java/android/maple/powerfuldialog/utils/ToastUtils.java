package android.maple.powerfuldialog.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hzh on 2016/9/29.
 * 快速连续点击了五次按钮，Toast就触发了五次。
 * 这样的体验其实是不好的，因为也许用户是手抖了一下多点了几次，导致Toast就长时间关闭不掉了。
 * 又或者我们其实已在进行其他操作了，应该弹出新的Toast提示，而上一个Toast却还没显示结束。
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showShortToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showLongToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

}
