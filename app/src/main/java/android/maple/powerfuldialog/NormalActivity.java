package android.maple.powerfuldialog;

import android.app.Activity;
import android.content.Context;
import android.maple.powerfuldialog.dialog.PowerfulDialog;
import android.maple.powerfuldialog.utils.ToastUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by guest on 2017/5/24.
 *
 *  普通用法
 */

public class NormalActivity extends Activity implements View.OnClickListener{
    private PowerfulDialog alertDialog;
    private Context context;
    private int temp=0;//临时变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        context=this;
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_normal)
                        .setText(R.id.bt_left,"取消")
                        .setText(R.id.bt_right,"确定")
                        .setTextColor(R.id.bt_left,R.color.dialog_warn_color)
                        .setViewVisiable(R.id.tv_title,View.GONE)
                        .setOnclickListener(R.id.bt_left, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"点击取消");
                                alertDialog.dismiss();
                            }
                        })
                        .setOnclickListener(R.id.bt_right, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"点击确定");
                                alertDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.button2:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_normal)
                        .setText(R.id.bt_left,"取消")
                        .setText(R.id.tv_title,"温馨提示")
                        .setCancelable(false)
                        .setOnclickListener(R.id.bt_left, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"取消成功");
                                alertDialog.dismiss();
                            }
                        })
                        .setViewVisiable(R.id.bt_right,View.GONE)
                        .setFullWidth()
                        .show();
                break;
            case R.id.button3:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_result)
                        .setText(R.id.bt_center,"提交")
                        .setCancelable(false)
                        .setOnclickListener(R.id.bt_center, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"点击提交");
                                alertDialog.dismiss();
                            }
                        })
                        .setViewVisiable(R.id.bt_right,View.GONE)
                        .show();
                break;
            case R.id.button4:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_radio_select)
                        .setWidthAndHeight(600,300)
                        .setOnclickListener(R.id.bt_getheight, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"填写的身高是："+((EditText)alertDialog.getView(R.id.et_height)).getText());
                                alertDialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .setIsPopSoftKey(true)
                        .show();
                break;
            case R.id.button5:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_chose_photo)
                        .setOnclickListener(R.id.dialog_tv_take_photo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"拍照操作");
                                alertDialog.dismiss();
                            }
                        })
                        .setOnclickListener(R.id.dialog_tv_image, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShortToast(context,"相册选取操作");
                                alertDialog.dismiss();
                            }
                        })
                        .setOnclickListener(R.id.dialog_tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        })
                        .setFromBottom(true)
                        .setCancelable(true)
                        .show();
                break;
            case R.id.button6:
                alertDialog=new PowerfulDialog.Builder(context)
                        .setDialogView(R.layout.dialog_result)
                        .setText(R.id.bt_center,"切换图片")
                        .setImage(R.id.iv_icon,R.mipmap.icon_form)
                        .setText(R.id.tv_warning,"注：此弹框演示的是图片的可配置")
                        .setWidthAndHeightForPercent(0.6f,0.3f)
                        .setAnimations(R.style.CenterDialogStyle)
                        .setCancelable(true)
                        .setOnclickListener(R.id.bt_center, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((ImageView)alertDialog.getView(R.id.iv_icon)).setImageResource(temp==0?R.mipmap.default_head:R.mipmap.icon_form);
                                temp=(temp==0?1:0);
                            }
                        })
                        .setViewVisiable(R.id.bt_right,View.GONE)
                        .show();
                break;
        }
    }
}
