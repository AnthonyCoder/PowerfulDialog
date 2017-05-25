package android.maple.powerfuldialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.maple.powerfuldialog.dialog.PowerfulDialog;
import android.maple.powerfuldialog.utils.ToastUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener{

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
