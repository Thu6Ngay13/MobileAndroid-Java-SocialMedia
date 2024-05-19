package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ProfileModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFullName, etDescription, etCompany, etLocation;
    private ImageButton ibUpdate, ibBack;
    private RadioGroup rgGender, rgRelationship;
    private RadioButton rbMale, rbFemale, rbInARelationship, rbSingle;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialize();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AccountCardModel accountModel = new AccountCardModel();
        if (bundle != null){
            accountModel.setFullname(bundle.getString("MY_FULLNAME"));
            accountModel.setUsername(bundle.getString("MY_USERNAME"));
            accountModel.setGender(bundle.getString("MY_GENDER"));
            accountModel.setDescription(bundle.getString("MY_DESC"));
            accountModel.setCompany(bundle.getString("MY_COMPANY"));
            accountModel.setLocation(bundle.getString("MY_LOCATION"));
            accountModel.setSingle(bundle.getBoolean("MY_RELATIONSHIP"));
        }
        etFullName.setText(accountModel.getFullname());
        etDescription.setText(accountModel.getDescription());
        etCompany.setText(accountModel.getCompany());
        etLocation.setText(accountModel.getLocation());
        if (accountModel.getGender() != null){
            if (accountModel.getGender().equals("male")){
                rbMale.setChecked(true);
            }
            else{
                rbFemale.setChecked(true);
            }
        }

        if (accountModel.isSingle()){
            rbSingle.setChecked(true);
        }else {
            rbInARelationship.setChecked(true);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.updateProfile(etFullName.getText().toString(), rbMale.isChecked() ? "male":"female", etDescription.getText().toString(), etCompany.getText().toString(), etLocation.getText().toString(), rbSingle.isChecked()? true:false, PrefManager.getUsername())
                .enqueue(new Callback<SimpleResponse<String>>() {
                    @Override
                    public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                        if (response.isSuccessful()){
                            if (response.body().isSuccess()){
                                Toast.makeText(EditProfileActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, MyPersonalPageActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(EditProfileActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initialize(){
        etFullName = findViewById(R.id.etFullName);
        etDescription = findViewById(R.id.etDescription);
        etCompany = findViewById(R.id.etCompany);
        etLocation = findViewById(R.id.etLocation);
        rgGender = findViewById(R.id.rgGender);
        rgRelationship = findViewById(R.id.rgRelationship);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbInARelationship = findViewById(R.id.rbInARelationship);
        rbSingle = findViewById(R.id.rbSingle);
        ibUpdate = findViewById(R.id.ibUpdate);
        ibBack = findViewById(R.id.ibBack);
    }
}