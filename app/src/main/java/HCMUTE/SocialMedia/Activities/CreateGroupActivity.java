package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private EditText etGroupName, etDescription;
    private ImageButton ibCreate;
    private RadioButton rbPublic, rbPrivate;
    private APIService apiService;
    GroupModel groupModel = new GroupModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        initialize();



        rbPublic.setChecked(true);
        rbPrivate.setChecked(false);

        groupModel.setHolderUsername(PrefManager.getUsername());
        groupModel.setGroupName(etGroupName.getText().toString());
        groupModel.setModeId(rbPublic.isChecked() ? 1:2);
        groupModel.setDescription(etDescription.getText().toString());
        ibCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createGroup() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.createGroup(PrefManager.getUsername(), etGroupName.getText().toString(), rbPublic.isChecked() ? 1:2, etDescription.getText().toString()).enqueue(new Callback<SimpleResponse<GroupModel>>() {
            @Override
            public void onResponse(Call<SimpleResponse<GroupModel>> call, Response<SimpleResponse<GroupModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        Toast.makeText(CreateGroupActivity.this, "Create success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateGroupActivity.this, GroupActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
                else {
                    int statusCode = response.code();

                    Toast.makeText(CreateGroupActivity.this, "Đã có nhóm trùng trên trước đó!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<GroupModel>> call, Throwable t) {
                Toast.makeText(CreateGroupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialize(){
        ibBack = findViewById(R.id.ibBack);
        etGroupName = findViewById(R.id.etGroupName);
        etDescription = findViewById(R.id.etDescription);
        rbPublic = findViewById(R.id.rbPublic);
        rbPrivate = findViewById(R.id.rbPrivate);
        ibCreate = findViewById(R.id.ibCreate);
    }
}