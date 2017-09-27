package android.maple.powerfuldialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    //#...........................PC_company提交的数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_normal).setOnClickListener(this);
        findViewById(R.id.bt_high).setOnClickListener(this);
        findViewById(R.id.bt_config).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent();
        Class c=null;
        switch (v.getId()){
            case R.id.bt_normal:
                c=NormalActivity.class;
                break;
            case R.id.bt_high:
                c=HightActivity.class;
                break;
            case R.id.bt_config:
                c=ConfigActivity.class;
                break;
        }
        i.setClass(MainActivity.this,c);
        startActivity(i);
    }
}
