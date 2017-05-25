package android.maple.powerfuldialog.dialog.custom;

/**
 * Created by guest on 2017/5/24.
 */

import android.content.Context;
import android.maple.powerfuldialog.R;
import android.maple.powerfuldialog.dialog.PowerfulDialog;
import android.view.View;

/**
 * 主要功能:用于公有高频出现的弹框样式的二次封装
 * Created by wz on 2017/5/4
 * 修订历史:
 */
public class CommonDialog extends PowerfulDialog.Builder{
    public static int DIALOG_NONE_MODE=0;//没有按钮的显示样式
    public static int DIALOG_SINGLE_MODE=1;//有一个按钮的显示样式
    public static int DIALOG_DOUBLE_MODE=2;//有两个按钮的显示样式
    public static int DIALOG_SINGLE_MODE_DEFAULT=3;//有一个按钮的显示样式，默认取消
    private static CommonDialog commonDialog;
    private static Context context;


    public CommonDialog(Context context) {
        super(context);
        this.context=context;
    }

    /**
     * 设置共有属性
     * @return
     */
    public final static CommonDialog getDialogBuilder(){
        commonDialog=new CommonDialog(context);
        commonDialog
                .setDialogView(R.layout.dialog_common)
                .setViewVisiable(R.id.tv_common_title, View.INVISIBLE);
        return commonDialog;
    }

    /**
     * 设置弹出框按钮样式和点击事件
     * @param model 按钮样式（三种）
     * @param listener 点击事件 如果是无按钮 直接传null即可
     * @param <I>
     * @return
     */
    public static <I extends IDialogOnclickListener>CommonDialog setButtonModel(int model,I listener){
        if(model==DIALOG_NONE_MODE){
            commonDialog.setViewVisiable(R.id.ll_common_bottom,View.GONE);
        }else if(model==DIALOG_SINGLE_MODE){
            setSingleOnclick((ISingleButtonClickListener) listener);
        }else if(model==DIALOG_DOUBLE_MODE){
            setDoubleOnclick((IDoubleButtonClickListener) listener);
        }else if(model==DIALOG_SINGLE_MODE_DEFAULT){
            setSingleOnclick(new ISingleButtonClickListener() {
                @Override
                public void clickcenter() {
                    commonDialog.dismiss();
                }
            });
        }
        return commonDialog;
    }

    /**
     * 设置显示内容
     * @param content
     * @return
     */
    public static CommonDialog setDialogContent(String content){
        commonDialog.setText(R.id.tv_common_content,content);
        return commonDialog;
    }
    /**
     * 设置显示标题
     * @param title
     * @return
     */
    public static CommonDialog setDialogTitle(String title){
        commonDialog.setText(R.id.tv_common_title,title).setViewVisiable(R.id.tv_common_title,View.VISIBLE);
        return commonDialog;
    }
    /**
     * 设置单个按钮
     * @param center
     * @return
     */
    public static CommonDialog setSingleText(String center){
        commonDialog.setText(R.id.tv_verify_submit,center);
        return commonDialog;
    }

    /**
     * 设置时候可以点击外部退出
     * @param isCancle
     * @return
     */
    public static CommonDialog setCanCancle(boolean isCancle){
        commonDialog.setCancelable(isCancle);
        return commonDialog;
    }
    /**
     * 设置按钮内容
     * @param left
     * @param right
     * @return
     */
    public static CommonDialog setDoubleButtonText(String left,String right){
        commonDialog.setText(R.id.tv_verify_cancle,left);
        commonDialog.setText(R.id.tv_verify_submit,right);
        return commonDialog;
    }
    /**
     * 设置双按钮监听
     * @param iDoubleButtonClickListener
     */
    private static void setDoubleOnclick( final IDoubleButtonClickListener iDoubleButtonClickListener){
        if(commonDialog==null) {
            return;
        }
        commonDialog.setOnclickListener(R.id.tv_verify_cancle, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDoubleButtonClickListener.clickLeft();
            }
        }).setOnclickListener(R.id.tv_verify_submit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDoubleButtonClickListener.clickRight();
            }
        });
    }

    /**
     * 设置单按钮监听
     * @param iSingleButtonClickListener
     */
    private static void setSingleOnclick(final ISingleButtonClickListener iSingleButtonClickListener){
        if(commonDialog==null){
            return;
        }
        commonDialog.setViewVisiable(R.id.tv_common_line,View.GONE)
                .setViewVisiable(R.id.tv_verify_cancle,View.GONE).setOnclickListener(R.id.tv_verify_submit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSingleButtonClickListener.clickcenter();
            }
        });
    }


    //    public AppAlertDialog dismiss() {
//        mDialog.dismiss();
//        return mDialog;
//    }
    public interface IDoubleButtonClickListener extends IDialogOnclickListener{
        void clickLeft();
        void clickRight();
    }
    public interface ISingleButtonClickListener extends IDialogOnclickListener{
        void clickcenter();
    }

    public interface IDialogOnclickListener{}

}