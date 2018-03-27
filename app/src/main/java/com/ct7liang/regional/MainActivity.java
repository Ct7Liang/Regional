package com.ct7liang.regional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.ct7liang.address.AddressUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressUtils.getInstance().showSelectWindow(findViewById(R.id.btn), MainActivity.this, new AddressUtils.OnAddressSelected() {
                    @Override
                    public void onSelected(String code, String name) {
                        Toast.makeText(MainActivity.this, code + " -- " + name, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
