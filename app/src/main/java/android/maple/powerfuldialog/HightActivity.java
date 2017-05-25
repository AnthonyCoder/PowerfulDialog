package android.maple.powerfuldialog;

import android.app.Activity;
import android.content.Context;
import android.maple.powerfuldialog.adapter.CoolCommonRecycleviewAdapter;
import android.maple.powerfuldialog.adapter.CoolRecycleViewHolder;
import android.maple.powerfuldialog.adapter.OnItemClickListener;
import android.maple.powerfuldialog.dialog.PowerfulDialog;
import android.maple.powerfuldialog.model.StudentModel;
import android.maple.powerfuldialog.utils.ToastUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guest on 2017/5/24.
 */

public class HightActivity extends Activity implements View.OnClickListener{
    
    private PowerfulDialog powerfulDialog;
    private List<StudentModel> studentModelList;
    private List<StudentModel> selectStudentModelList;
    private CoolCommonRecycleviewAdapter<StudentModel> coolCommonRecycleviewAdapter;
    private RecyclerView rvStudentList;
    private Context mContext;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high);
        findViewById(R.id.bt_list).setOnClickListener(this);
        findViewById(R.id.bt_complex).setOnClickListener(this);
        mContext=this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_list:
                showListDialog();
                break;
            case R.id.bt_complex:
                break;
        }
    }
    
    public void showListDialog(){
        if (coolCommonRecycleviewAdapter!=null){
            coolCommonRecycleviewAdapter.clear();
        }
        powerfulDialog=new PowerfulDialog.Builder(mContext)
                .setDialogView(R.layout.dialog_student_list)
                .setWidthAndHeightForPercent(0.8f,0.4f)
                .setIsPopSoftKey(true)
                .setOnclickListener(R.id.tv_add_student, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name=((EditText)powerfulDialog.getView(R.id.et_student_name)).getText().toString();
                        if(name.equals("")){
                            ToastUtils.showShortToast(mContext,"请输入学生姓名");
                            return;
                        }
                        coolCommonRecycleviewAdapter.addData(0,new StudentModel(name,"测试班级01",99));
                        rvStudentList.scrollToPosition(0);
                        selectStudentModelList.clear();
                    }
                })
                .setOnclickListener(R.id.tv_cancle, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        powerfulDialog.dismiss();
                    }
                })
                .setOnclickListener(R.id.tv_see_msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectStudentModelList.size()>0){
                            StringBuffer sb=new StringBuffer();
                            for (StudentModel s:selectStudentModelList) {
                                sb.append(s.getAge()+"岁的"+s.getName()+"在"+s.getClassName()+"班\n");
                            }
                            ToastUtils.showLongToast(mContext,"所选人员信息:\n"+sb.toString());
                        }else{
                            ToastUtils.showLongToast(mContext,"您未选择任何学生");
                        }
                        powerfulDialog.dismiss();
                    }
                })
                .create();
        rvStudentList=powerfulDialog.getView(R.id.rv_student_list);
        rvStudentList.setLayoutManager(new LinearLayoutManager(mContext));
        initListAdapter();
        powerfulDialog.show();
        
    }
    public void initListAdapter(){
        selectStudentModelList=new ArrayList<>();
        studentModelList=new ArrayList<>();
        coolCommonRecycleviewAdapter=new CoolCommonRecycleviewAdapter<StudentModel>(studentModelList,mContext,R.layout.list_item_student) {
            @Override
            protected void onBindView(final CoolRecycleViewHolder holder, int position) {
                final StudentModel studentModel=studentModelList.get(position);
                holder.setText(R.id.tv_item_name,studentModel.getName());
                holder.setText(R.id.tv_item_classname,studentModel.getClassName());
                holder.setText(R.id.tv_item_age,studentModel.getAge()+"");
                holder.setOnClickListener(R.id.ll_item_student, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//由于测试数据 暂未加id 没有唯一标示 所以 暂不考虑去重复增加和再次点击取消
                        holder.getView(R.id.ll_item_student).setBackgroundColor(getResources().getColor(R.color.app_color));
                        selectStudentModelList.add( studentModel);
                    }
                });
            }
        };
        rvStudentList.setAdapter(coolCommonRecycleviewAdapter);
        coolCommonRecycleviewAdapter.addAll(initStudentData());
    }
    public List<StudentModel> initStudentData(){
        List<StudentModel> defaultList=new ArrayList<>();
        defaultList.add(new StudentModel("多利羊子","T250",18));
        defaultList.add(new StudentModel("卢达良","T360",23));
        defaultList.add(new StudentModel("张小银","T220",19));
        defaultList.add(new StudentModel("曾二层","T325",13));
        return defaultList;
    }
}
