package android.maple.powerfuldialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.maple.powerfuldialog.R;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;



/**
 * 主要功能:Dialog属性控制类（用于绑定事件、设置属性）
 * Created by wz on 2017/3/22
 * 修订历史:
 */
class AlertController {

    private AppAlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper helper;

    public AlertController(AppAlertDialog appAlertDialog, Window window) {
        this.mDialog= appAlertDialog;
        this.mWindow=window;
    }

    public void setHelper(DialogViewHelper helper) {
        this.helper = helper;
    }

    //获取dialog
    public AppAlertDialog getmDialog() {
        return mDialog;
    }
    //获取dialog的window
    public Window getmWindow() {
        return mWindow;
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        helper.setText(viewId,text);
    }

    /**
     * 设置文本颜色
     * @param viewId
     * @param colorRes
     */
    public void setTextColor(Context context, int viewId, int colorRes) {
        helper.setTextColor(context,viewId,colorRes);
    }

    /**
     * 设置图片
     * @param viewId
     * @param imgRes
     */
    public void setImage(int viewId, int imgRes) {
        helper.setImage(viewId,imgRes);
    }

    /**
     * 设置指定view显示状况
     * @param viewId
     * @param visibilityMode
     */
    public void setVisiable(int viewId, int visibilityMode) {
       helper.setVisiable(viewId,visibilityMode);

    }

    /**
     * 优化findViewById
     * @param viewId
     * @param <T>
     * @return
     */
    public  <T extends View> T getView(int viewId) {
        return helper.getView(viewId);
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param onClickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {
        helper.setOnclickListener(viewId,onClickListener);
    }

    /**
     * 存放可配置项的参数
     */
    public static class AlertParams{
        public Context mContext;
        public int mThemeResId;
        public boolean mIsPopSoftKey=false;//是否弹出软键盘（默认不弹出）
        public boolean mCancelable=true;//点击外部是否可取消(默认点击外部可取消)
        public DialogInterface.OnCancelListener mOnCancelListener;//dialog cancel监听
        public DialogInterface.OnDismissListener mOnDismissListener;//dialog消失监听
        public DialogInterface.OnKeyListener mOnKeyListener;//dialog按键监听
        public View mView;//dialog显示的布局view
        public int mViewLayoutResId;//dialog显示的布局id
        public SparseArray<CharSequence> mTextArray=new SparseArray<>();//存放文字
        public SparseArray<Integer> mTextColorArray=new SparseArray<>();//存放文字颜色
        public SparseArray<Integer> mImageArray=new SparseArray<>();//存放图片id
        public SparseArray<View.OnClickListener> mClickArray=new SparseArray<>();//存放点击事件
        public SparseArray<Integer> mVisibilityArray=new SparseArray<>();//存放是否显示的布局
        public int mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;//默认宽度自适应
        public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;//默认宽度自适应
        public int mAnimation= R.style.normalDialogAnim;//设置的默认动画效果
        public int mGravity= Gravity.CENTER;//设置默认的Dialog位置
//        public SparseArray<WeakReference<View.OnClickListener>> mClickArray=new SparseArray<>();//存放点击事件


        public AlertParams(Context context, int themeResId) {
            this.mContext=context;
            this.mThemeResId=themeResId;
        }

        /**
         * 绑定和设置参数
         * @param mAlert 
         * */
        public void apply(AlertController mAlert) {
            DialogViewHelper helper=null;
            //设置dialog布局
            if(mViewLayoutResId!=0){
                helper=new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if(mView!=null){
                helper=new DialogViewHelper();
                helper.setDialogView(mView);
            }
            if(helper==null){
                throw new IllegalArgumentException("请设置布局id");
            }
            //dialog设置布局
            mAlert.getmDialog().setContentView(helper.getDialogView());

            //设置controller的辅助类
            mAlert.setHelper(helper);

            //设置文本（针对于TextView）
            int textArraySize=mTextArray.size();
            for (int i=0;i<textArraySize;i++){
                mAlert.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }
            //设置文本（针对于TextView）
            int textColorArraySize=mTextColorArray.size();
            for (int i=0;i<textColorArraySize;i++){
                mAlert.setTextColor(mContext,mTextColorArray.keyAt(i),mTextColorArray.valueAt(i));
            }
            //设置图片（针对于ImageView）
            int imgArraySize=mImageArray.size();
            for (int i=0;i<imgArraySize;i++){
                mAlert.setImage(mImageArray.keyAt(i),mImageArray.valueAt(i));
            }
            //设置点击
            int clickArraySize=mClickArray.size();
            for (int i=0;i<clickArraySize;i++){
                mAlert.setOnclickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }
            int visibilityArraySize=mVisibilityArray.size();
            for (int i=0;i<visibilityArraySize;i++){
                mAlert.setVisiable(mVisibilityArray.keyAt(i),mVisibilityArray.valueAt(i));
            }
            //配置自定义效果（全屏 底部弹出 默认动画）

            //获取dialog window对象
            Window window=mAlert.getmWindow();
            if(mIsPopSoftKey){//弹出软键盘
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            window.setWindowAnimations(mAnimation);
            //设置宽高
            WindowManager.LayoutParams params= window.getAttributes();
            params.width=mWidth;
            params.height=mHeight;
            window.setAttributes(params);
        }
    }

}
