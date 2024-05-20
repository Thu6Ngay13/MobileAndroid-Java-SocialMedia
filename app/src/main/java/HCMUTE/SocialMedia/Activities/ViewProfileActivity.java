package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ProfileModel;
import HCMUTE.SocialMedia.R;

public class ViewProfileActivity extends AppCompatActivity {
    TextView tvFullname;
    ImageButton ibBack;
    EditText etDescription, etCompany, etLocation;
    RadioGroup rgGender, rgRelationship;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        tvFullname = (TextView) findViewById(R.id.tvFullName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etCompany = (EditText) findViewById(R.id.etCompany);
        etLocation = (EditText) findViewById(R.id.etLocation);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgRelationship = (RadioGroup) findViewById(R.id.rgRelationship);
        ibBack = findViewById(R.id.ibBack);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AccountCardModel accountModel = new AccountCardModel();
        if (bundle != null){
            accountModel.setFullname(bundle.getString("FRIEND_FULLNAME"));
            accountModel.setUsername(bundle.getString("FRIEND_USERNAME"));
            accountModel.setGender(bundle.getString("FRIEND_GENDER"));
            accountModel.setDescription(bundle.getString("FRIEND_DESC"));
            accountModel.setCompany(bundle.getString("FRIEND_COMPANY"));
            accountModel.setLocation(bundle.getString("FRIEND_LOCATION"));
            accountModel.setSingle(bundle.getBoolean("FRIEND_RELATIONSHIP"));
        }
        tvFullname.setText(accountModel.getFullname().toString());
        etDescription.setText(accountModel.getDescription());
        etCompany.setText(accountModel.getCompany());
        etLocation.setText(accountModel.getLocation());
        if (accountModel.getGender() != null){
            if (accountModel.getGender().equals("male")){
                rgGender.check(R.id.rbMale);
            } else {
                rgGender.check(R.id.rbFemale);
            }
        }
        if (accountModel.isSingle()){
            rgRelationship.check(R.id.rbSingle);
        } else {
            rgRelationship.check(R.id.rbInARelationship);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}