package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import HCMUTE.SocialMedia.Models.ProfileModel;
import HCMUTE.SocialMedia.R;

public class ViewProfileActivity extends AppCompatActivity {

    ProfileModel user;
    TextView tvFullname;
    EditText etBirthday, etDescription, etCompany, etLocation;
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

        Calendar birthday = Calendar.getInstance();
        birthday.set(2003, 2, 16);
        user = new ProfileModel("ThuyCao816", "Cao Thị Thu Thủy", "female","", "HCMUTE", "Tp. HCM", true);
        tvFullname.setText(user.getFullname().toString());
        etDescription.setText(user.getDescription());
        etCompany.setText(user.getCompany());
        etLocation.setText(user.getLocation());
        if (user.getGender() == "male"){
            rgGender.check(R.id.rbMale);
        } else {
            rgGender.check(R.id.rbFemale);
        }
        if (user.isSingle()){
            rgRelationship.check(R.id.rbSingle);
        } else {
            rgRelationship.check(R.id.rbInARelationship);
        }
    }
}