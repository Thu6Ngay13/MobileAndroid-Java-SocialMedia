package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import HCMUTE.SocialMedia.Adapters.EditProfileAdapter;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ProfileModel;
import HCMUTE.SocialMedia.R;

public class EditProfileActivity extends AppCompatActivity {

    private ProfileModel user;
    private EditProfileAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        AccountCardModel accountModel = new AccountCardModel();
        if (intent != null){
            accountModel
        }
//        listView = (ListView) findViewById(R.id.lvProfile);
//        user = new ProfileModel(accountModel.getUsername(), accountModel.getFullname(), accountModel.getGender(), accountModel.getDescription(), accountModel.getCompany(), accountModel.getLocation(), accountModel.isSingle());
//        List<ProfileModel> listUser = new ArrayList<>();
//        listUser.add(user);
//        adapter = new EditProfileAdapter(getApplicationContext(), listUser);
//        listView.setAdapter(adapter);
    }
}