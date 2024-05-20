package HCMUTE.SocialMedia.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileGroupActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 100;
    public static String[] storage_permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] storage_permissions_33 = {"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO"};
    private String fullName = PrefManager.getFullname();
    private String avatar = PrefManager.getAvatarURL();
    private String username = PrefManager.getUsername();
    private ImageButton ibBack;
    private EditText etGroupName, etDescription;
    private ImageButton ibCreate;
    private RadioButton rbPublic, rbPrivate;
    private APIService apiService;
    private Button btnChosseImage;
    private ImageView ivGroupImage;

    private String groupImage = "";

    GroupModel groupModel = new GroupModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_group);
        initialize();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            groupModel.setGroupId(bundle.getLong("GROUP_GROUPID"));
            groupModel.setGroupName(bundle.getString("GROUP_GROUPNAME"));
            groupModel.setAvatarURL(bundle.getString("GROUP_AVATARURL"));
            groupModel.setDescription(bundle.getString("GROUP_DESCRIPSION"));
            groupModel.setModeId(bundle.getLong("GROUP_MODEID"));
            groupModel.setCreationTimeAt(bundle.getString("GROUP_TIME"));
            groupModel.setHolderUsername(bundle.getString("GROUP_USERNAME"));
            groupModel.setHolderFullName(bundle.getString("GROUP_FULLNAME"));
            Log.d("", groupModel.toString());
        }

        groupImage = groupModel.getAvatarURL();
        if (groupImage == "") {
            ivGroupImage.setVisibility(View.GONE);
        } else {
            ivGroupImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(groupImage).into(ivGroupImage);
        }


        etGroupName.setText(groupModel.getGroupName());
        if (groupModel.getModeId() == 1)
        {
            rbPublic.setChecked(true);
            rbPrivate.setChecked(false);
        } else {
            rbPublic.setChecked(false);
            rbPrivate.setChecked(true);
        }
        etDescription.setText(groupModel.getDescription());
        ibCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGroup();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btnChosseImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(permissions(), MY_REQUEST_CODE);
                openGallery();
            }
        }
    }
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null && o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getData() != null) {
                try {
                    Uri selectedFileUri = o.getData().getData();
                    mediaPost(selectedFileUri);
                } catch (Exception e) {
                    Log.d("Activity", "Failed in startForResult" + e.getMessage());
                }
            }
        }
    });

    private void mediaPost(Uri selectedFileUri) {
        if (selectedFileUri == null) {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
        } else {
            // Lấy đường dẫn thực tế của file
            String IMAGE_PATH = RealPathUtil.getRealPath(getApplicationContext(), selectedFileUri);
            File file = new File(IMAGE_PATH);
            String fileName = file.getName();
            // Tạo RequestBody cho phần multipart
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipart = MultipartBody.Part.createFormData("media", fileName, requestBody);
            apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.mediaPost(multipart).enqueue(new Callback<SimpleResponse<String>>() {
                @Override
                public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse<String> responseModel = response.body();
                        groupImage = responseModel.getResult();
                        if (groupImage == "") {
                            ivGroupImage.setVisibility(View.GONE);
                        } else {
                            ivGroupImage.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(groupImage).into(ivGroupImage);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.d("Activity", "Request error: " + statusCode);
                        // Handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                    Log.d("Activity", "Request failed: " + t.getMessage());
                }
            });
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startForResult.launch(Intent.createChooser(intent, "Select Picture"));
    }
    private void updateGroup() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        Log.d("", ""+String.valueOf(groupModel.getGroupId())+ PrefManager.getUsername() +"//"+ etGroupName.getText().toString() +"//"+ String.valueOf(rbPublic.isChecked() ? 1:2 ) +"//"+etDescription.getText().toString());
        apiService.updateGroup((groupModel.getGroupId()),String.valueOf(PrefManager.getUsername()), String.valueOf(etGroupName.getText()), rbPublic.isChecked() ? 1:2, etDescription.getText().toString(), groupImage).enqueue(new Callback<SimpleResponse<GroupModel>>() {
            @Override
            public void onResponse(Call<SimpleResponse<GroupModel>> call, Response<SimpleResponse<GroupModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        Toast.makeText(EditProfileGroupActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileGroupActivity.this, GroupActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
                else {
                    int statusCode = response.code();

                    Toast.makeText(EditProfileGroupActivity.this, "Đã có nhóm trùng trên trước đó!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<GroupModel>> call, Throwable t) {
                Toast.makeText(EditProfileGroupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        btnChosseImage = findViewById(R.id.btnChosseImage);
        ivGroupImage = findViewById(R.id.ivGroupImage);
    }
}