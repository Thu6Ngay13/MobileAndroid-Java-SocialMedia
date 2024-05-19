package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;

public class AdminActivity extends AppCompatActivity {
    private Button btnLogOut, btnBanAccount, btnProcessReport, btnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initialize();

        btnBanAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminBanAccountActivity.class);
                startActivity(intent);
            }
        });

        btnProcessReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminProcessReportActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.getInstance(getApplicationContext()).logout();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        btnBanAccount = findViewById(R.id.btnBanAccount);
        btnProcessReport = findViewById(R.id.btnProcessReport);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnLogOut = findViewById(R.id.btnLogout);
    }
}