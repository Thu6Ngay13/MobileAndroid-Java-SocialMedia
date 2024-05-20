package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import HCMUTE.SocialMedia.R;

public class PoliciesActivity extends AppCompatActivity {

    Button btnDuLieuCaNhan, btnBaiDang, btnThayDoiChinhSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> finish());

        btnDuLieuCaNhan = (Button) findViewById(R.id.btnDuLieuCaNhan);
        btnBaiDang = (Button) findViewById((R.id.btnBaiDang));
        btnThayDoiChinhSach = (Button) findViewById(R.id.btnThayDoiChinhSach);
        btnDuLieuCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliciesActivity.this, PolicyDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("policy", "dulieucanhan");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnBaiDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliciesActivity.this, PolicyDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("policy", "baidang");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnThayDoiChinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliciesActivity.this, PolicyDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("policy", "thaydoichinhsach");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}