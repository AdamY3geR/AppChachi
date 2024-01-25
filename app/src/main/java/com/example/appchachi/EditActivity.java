package com.example.appchachi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_role, et_FullName, et_email, et_phone,et_id;
    Button btn_save_lv, btn_cancel_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        et_email=(EditText) findViewById(R.id.et_email);
        et_role=(EditText) findViewById(R.id.et_Role);
        et_id=(EditText) findViewById(R.id.et_Id);
        et_phone=(EditText) findViewById(R.id.et_Phone);
        et_FullName=(EditText) findViewById(R.id.et_FullName);
        btn_cancel_lv=(Button) findViewById(R.id.btn_cancel_lv);
        btn_save_lv=(Button) findViewById(R.id.btn_save_lv);
        //btn_save_lv.setOnClickListener(this);
        //btn_cancel_lv.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            String id = intent.getExtras().getString("id");
            String FullName = intent.getExtras().getString("FullName");
            String email = intent.getExtras().getString("email");
            String phone = intent.getExtras().getString("phone");
            String role = intent.getExtras().getString("role");
            et_phone.setText(phone);
            et_role.setText(role);
            et_id.setText(id);
            et_FullName.setText(FullName);
            et_email.setText(email);
        }

    }

    @Override
    public void onClick(View v) {
        if (btn_save_lv==v){
            if(et_email.getText().toString().length()>0&&et_role.getText().toString().length()>0&&et_phone.toString().length()>0&&et_id.toString().length()>0&&et_FullName.toString().length()>0){
                Intent intent = new Intent();
                intent.putExtra("email",et_email.getText().toString());
                intent.putExtra("role",et_role.getText().toString());
                intent.putExtra("FullName",et_FullName.getText().toString());
                intent.putExtra("id",et_id.getText().toString());
                intent.putExtra("phone",et_phone.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
            else
                Toast.makeText(this,"please fill all fields", Toast.LENGTH_LONG).show();
        }
    }
}