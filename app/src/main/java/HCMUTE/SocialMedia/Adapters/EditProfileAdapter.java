package HCMUTE.SocialMedia.Adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import HCMUTE.SocialMedia.Models.ProfileModel;
import HCMUTE.SocialMedia.R;

public class EditProfileAdapter extends BaseAdapter {
    private Context context;
    private List<ProfileModel> userList;
    private int position;

    public EditProfileAdapter(Context context, List<ProfileModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_profile_view, parent, false);
        //anh xa.
        EditText etFullName = (EditText) convertView.findViewById(R.id.etFullName);
        EditText etBirthday = (EditText) convertView.findViewById(R.id.etBirthday);
        RadioGroup rgGender = (RadioGroup) convertView.findViewById(R.id.rgGender);
        EditText etDescription = (EditText) convertView.findViewById(R.id.etDescription);
        EditText etCompany = (EditText) convertView.findViewById(R.id.etCompany);
        EditText etLocation = (EditText) convertView.findViewById(R.id.etLocation);
        RadioGroup rgRelationship = (RadioGroup) convertView.findViewById(R.id.rgRelationship);
        //set data.
        ProfileModel user = userList.get(position);
        this.position = position;
        etFullName.setText(user.getFullname());
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        etBirthday.setText(simpleDateFormat.format(user.getDateOfBirth().getTime()));
        if (user.getGender() == "male"){
            rgGender.check(R.id.rbMale);
        } else{
            rgGender.check(R.id.rbFemale);
        }
        etDescription.setText(user.getDescription());
        etCompany.setText(user.getCompany());
        etLocation.setText(user.getLocation());
        if (user.isSingle()){
            rgRelationship.check(R.id.rbSingle);
        } else {
            rgRelationship.check(R.id.rbInARelationship);
        }

        return convertView;
    }
}
