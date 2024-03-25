package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
        listView = (ListView) findViewById(R.id.lvProfile);

        Calendar birthday = Calendar.getInstance();
        birthday.set(2003, 2, 16);
        user = new ProfileModel("ThuyCao816", "Cao Thị Thu Thủy", "female", birthday,"", "HCMUTE", "Tp. HCM", true);
        List<ProfileModel> listUser = new ArrayList<>();
        listUser.add(user);
        adapter = new EditProfileAdapter(getApplicationContext(), listUser);
        listView.setAdapter(adapter);
    }
    public void editBirthday(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateEditTextBirthday(calendar);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void updateEditTextBirthday(Calendar calendar) {
        View itemView = listView.getChildAt(0);
        if (itemView != null) {
            EditText etBirthday = itemView.findViewById(R.id.etBirthday);
            if (etBirthday != null) {
                String dateFormat = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                etBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            } else {
                Log.e("Error", "EditText not found in item view");
            }
        }

    }
}