package android.maple.powerfuldialog;

import android.app.Activity;
import android.content.Context;
import android.maple.powerfuldialog.dialog.custom.CommonDialog;
import android.maple.powerfuldialog.utils.ToastUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by wz on 2017/5/24.
 */

public class ConfigActivity extends Activity implements View.OnClickListener{

    private Button bt_normal_style1,bt_normal_style2,bt_normal_style3;
    private CommonDialog commonDialog;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        findViewById(R.id.bt_normal_style1).setOnClickListener(this);
        findViewById(R.id.bt_normal_style2).setOnClickListener(this);
        findViewById(R.id.bt_normal_style3).setOnClickListener(this);
        mContext=this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_normal_style1:
                commonDialog=new CommonDialog(mContext).getDialogBuilder().setDialogTitle("登录提示").setDialogContent("您还未登录，是否跳转到登录界面？").setButtonModel(CommonDialog
                        .DIALOG_DOUBLE_MODE, new CommonDialog.IDoubleButtonClickListener() {
                    @Override
                    public void clickLeft() {
                        ToastUtils.showShortToast(mContext,"随便看看");
                        commonDialog.destory();
                    }

                    @Override
                    public void clickRight() {
                        ToastUtils.showShortToast(mContext,"好的，这就去");
                        commonDialog.destory();
                    }
                }).setDoubleButtonText("随便看看","好的，这就去");
                commonDialog.show();
                break;
            case R.id.bt_normal_style2:
                commonDialog=new CommonDialog(mContext).getDialogBuilder().setDialogTitle("审核结果").setDialogContent("你的审核已经通过").setButtonModel(CommonDialog.DIALOG_SINGLE_MODE, new CommonDialog.ISingleButtonClickListener() {
                    @Override
                    public void clickcenter() {
                        ToastUtils.showShortToast(mContext,"点击了确定");
                        commonDialog.destory();
                    }
                }).setSingleText("确定");
                commonDialog.show();
                break;
            case R.id.bt_normal_style3:
                commonDialog=new CommonDialog(mContext).getDialogBuilder().setDialogContent("您还未登录，是否跳转到登录界面？").setButtonModel(CommonDialog
                        .DIALOG_DOUBLE_MODE, new CommonDialog.IDoubleButtonClickListener() {
                    @Override
                    public void clickLeft() {
                        ToastUtils.showShortToast(mContext,"随便看看");
                        commonDialog.destory();
                    }

                    @Override
                    public void clickRight() {
                        ToastUtils.showShortToast(mContext,"好的，这就去");
                        commonDialog.destory();
                    }
                }).setDoubleButtonText("随便看看","好的，这就去");
                commonDialog.show();
                break;
        }
    }
}
