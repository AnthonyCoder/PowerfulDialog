package android.maple.powerfuldialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.maple.powerfuldialog.R;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;



/**
 * 主要功能:根据项目自定义的可配置Dialog
 * Created by wz on 2017/3/22
 * 修订历史:
 */
public class PowerfulDialog extends Dialog {

    private PowerfulController mAlert;

    public PowerfulDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert=new PowerfulController(this,getWindow());
    }
    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId,text);

    }

    /**
     * 优化findViewById
     * 可以获取dialogView上所有的控件，并作出操作（如listView、RecycleView、RadioGroup.....）
     * @param viewId
     * @param <T>
     * @return
     */
    public  <T extends View> T getView(int viewId) {
       return mAlert.getView(viewId);
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param onClickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {
        mAlert.setOnclickListener(viewId,onClickListener);
    }

    public static class Builder{
        private final PowerfulController.AlertParams P;

        private Context mContext;
        private PowerfulDialog mDialog;

        public Builder(Context context){
            this(context, R.style.NormalDialogStyle);
        }

        /**
         * 初始化配置参数
         * @param context 上下文
         * @param themeResId dialog显示样式
         */
        public Builder(Context context, int themeResId){
            this.mContext=context;
            this.P = new PowerfulController.AlertParams(context,themeResId);
        }

        /**
         * 创建dialog
         * @return
         */
        public PowerfulDialog create(){
            PowerfulDialog dialog = new PowerfulDialog(this.P.mContext, P.mThemeResId);
            this.P.apply(dialog.mAlert);
            dialog.setCancelable(this.P.mCancelable);

            if(this.P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            dialog.setOnCancelListener(this.P.mOnCancelListener);
            dialog.setOnDismissListener(this.P.mOnDismissListener);

            if(this.P.mOnKeyListener != null) {
                dialog.setOnKeyListener(this.P.mOnKeyListener);
            }

            return dialog;
        }
        public PowerfulDialog show() {
            PowerfulDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
        public PowerfulDialog dismiss() {
            mDialog.dismiss();
            return mDialog;
        }

        public PowerfulDialog destory() {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
            return mDialog;
        }

        //配置参数

        /**
         *  dialog填充屏幕宽度
         * @return
         */
        public PowerfulDialog.Builder setFullWidth() {
            this.P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }
        /**
         *  dialog填充屏幕高度
         * @return
         */
        public PowerfulDialog.Builder setFullHeight() {
            this.P.mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画效果
         * @return
         */
        public PowerfulDialog.Builder setFromBottom(boolean isAnimation){
            if(isAnimation){
                this.P.mAnimation=R.style.fromBottomDialogStyle;
            }
            this.P.mGravity= Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置dialog的宽高
         * @param width
         * @param height
         * @return
         */
        public PowerfulDialog.Builder setWidthAndHeight(int width, int height) {
            this.P.mWidth=width;
            this.P.mHeight =height;
            return this;
        }

        /**
         * 设置dialog的百分比宽高
         * @param pWidth
         * @param pHeight
         * @return
         */
        public PowerfulDialog.Builder setWidthAndHeightForPercent(float pWidth,float pHeight){
            this.P.pWidth=pWidth;
            this.P.pHeight=pHeight;
            return this;
        }
        /**
         * 添加默认动画
         * @return
         */
        public PowerfulDialog.Builder addDefaultAnimation() {
            this.P.mAnimation=R.style.normalDialogAnim;
            return this;
        }
        /**
         * 设置自定义动画
         * @return
         */
        public PowerfulDialog.Builder setAnimations(int animStyle) {
            this.P.mAnimation=animStyle;
            return this;
        }

        /**
         * 设置显示的dialog界面
         * @param view
         * @return
         */
        public PowerfulDialog.Builder setDialogView(View view) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            return this;
        }
        public PowerfulDialog.Builder setDialogView(int layoutResId) {
            this.P.mView = null;
            this.P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置是否可点击外部消失
         * @param cancelable
         * @return
         */
        public PowerfulDialog.Builder setCancelable(boolean cancelable) {
            this.P.mCancelable = cancelable;
            return this;
        }
        /**
         * 设置是否相显示dialog的同时弹出软键盘
         * @param ispopsoftkey
         * @return
         */
        public PowerfulDialog.Builder setIsPopSoftKey(boolean ispopsoftkey) {//设置是否弹出软键盘
            this.P.mIsPopSoftKey = ispopsoftkey;
            return this;
        }

        /**
         * 设置dialog返回事件
         * @param onCancelListener
         * @return
         */
        public PowerfulDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }
        /**
         * 设置dialog消失事件
         * @param onDismissListener
         * @return
         */
        public PowerfulDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 设置dialog的按键监听
         * @param onKeyListener
         * @return
         */
        public PowerfulDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置指定view的文本
         * @param viewId
         * @param text
         * @return
         */
        public PowerfulDialog.Builder setText(int viewId, CharSequence text){//设置文本
            this.P.mTextArray.put(viewId,text);
            return this;
        }
        /**
         * 设置指定textview的文本颜色
         * @param viewId
         * @param colorRes
         * @return
         */
        public PowerfulDialog.Builder setTextColor(int viewId, int colorRes){//设置文本
            this.P.mTextColorArray.put(viewId,colorRes);
            return this;
        }
        /**
         * 设置指定view的图片res
         * @param viewId
         * @param imgRes
         * @return
         */
        public PowerfulDialog.Builder setImage(int viewId, int imgRes){//设置文本
            this.P.mImageArray.put(viewId,imgRes);
            return this;
        }

        /**
         * 设置指定view的显示隐藏功能
         * @param viewId
         * @param visibilityMode
         * @return
         */
        public PowerfulDialog.Builder setViewVisiable(int viewId, int visibilityMode){//设置显示隐藏功能
            this.P.mVisibilityArray.put(viewId,visibilityMode);
            return this;
        }

        /**
         * 设置指定view的点击事件
         * @param viewId
         * @param listener
         * @return
         */
        public PowerfulDialog.Builder setOnclickListener(int viewId, View.OnClickListener listener){//设置点击事件
            this.P.mClickArray.put(viewId,listener);
            return this;
        }
    }
}
