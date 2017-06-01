package android.maple.powerfuldialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wz on 2017/5/23.
 */
public abstract class CoolCommonRecycleviewAdapter<T> extends RecyclerView.Adapter<CoolRecycleViewHolder> {
    protected List<T> mLists;
    protected Context mContext;
    protected int layoutID;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setmLists(List<T> mLists) {
        this.mLists = mLists;
    }

    public CoolCommonRecycleviewAdapter(List<T> mLists, Context mContext, int layoutID) {
        this.mLists = mLists == null ? new ArrayList<T>() : mLists;
        this.mContext = mContext;
        this.layoutID = layoutID;
    }

    public List<T> getmLists() {
        return mLists;
    }

    @Override
    public CoolRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        CoolRecycleViewHolder holder = new CoolRecycleViewHolder(LayoutInflater.from(mContext).inflate(layoutID, null));
        CoolRecycleViewHolder holder = new CoolRecycleViewHolder(LayoutInflater.from(mContext).inflate(layoutID, parent, false));//解决部分在ScrollView嵌套下宽度显示不全的情况
        return holder;
    }

    @Override
    public void onBindViewHolder(final CoolRecycleViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.getmConvertView().setClickable(true);
            holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
        onBindView(holder, position);
    }

    protected abstract void onBindView(CoolRecycleViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public void addData(int position, T t) {
        mLists.add(position, t);
        notifyItemInserted(position);
        // 加入如下代码保证position的位置正确性
        if (position != mLists.size() - 1) {
            notifyItemRangeChanged(position, mLists.size() - position);
        }
    }

    public void removeData(int position) {
        mLists.remove(position);
        notifyItemRemoved(position);
        if (position != mLists.size() - 1) {
            notifyItemRangeChanged(position, mLists.size() - position);
        }
    }

    public void addAll(List<T> elem) {
        if (mLists != null) {
            mLists.addAll(elem);
        }
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mLists.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mLists.remove(index);
        notifyDataSetChanged();
    }

    public void clear() {
        mLists.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<T> elem) {
        if (mLists != null && elem != null) {
            mLists.clear();
            mLists.addAll(elem);
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<T> elem) {
        if (null != elem) {
            mLists = elem;
            notifyDataSetChanged();
        }
    }
}
