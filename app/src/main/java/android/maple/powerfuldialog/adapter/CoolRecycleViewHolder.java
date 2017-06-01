package android.maple.powerfuldialog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wz on 2017/5/23.
 */
public class CoolRecycleViewHolder extends RecyclerView.ViewHolder {

    protected final SparseArray<View> mViews;
    protected View mConvertView;
    private int circleRootId;

    public CoolRecycleViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public CoolRecycleViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param resoure
     * @return
     */
    public CoolRecycleViewHolder setTextColor(int viewId, int resoure) {
        TextView view = getView(viewId);
        view.setTextColor(resoure);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CoolRecycleViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public CoolRecycleViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }


    /**
     * 为View是否可见
     *
     * @param viewId
     * @return
     */
    public CoolRecycleViewHolder setViewVisiable(boolean isvisiable, int... viewId) {
        for (int v : viewId) {
            getView(v).setVisibility(isvisiable ? View.VISIBLE : View.GONE);
        }
        return this;
    }
    /**
     * 为View是否可见
     *
     * @param viewId
     * @return
     */
    public CoolRecycleViewHolder setViewVisiable(int visableModel, int... viewId) {
        for (int v : viewId) {
            getView(v).setVisibility(visableModel);
        }
        return this;
    }
    /**
     * 关于事件的
     */
    public CoolRecycleViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}

