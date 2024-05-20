package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileGroupActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private TextView etGroupName, etDescription;
    private RadioButton rbPublic, rbPrivate;

    private ImageView ivGroupImage;

    GroupModel groupModel = new GroupModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_group);
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

        if (groupModel.getAvatarURL() == "") {
            ivGroupImage.setVisibility(View.GONE);
        } else {
            ivGroupImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(groupModel.getAvatarURL()).into(ivGroupImage);
        }
        etDescription.setText(groupModel.getDescription());
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initialize(){
        ibBack = findViewById(R.id.ibBack);
        etGroupName = findViewById(R.id.etGroupName);
        etDescription = findViewById(R.id.etDescription);
        rbPublic = findViewById(R.id.rbPublic);
        rbPrivate = findViewById(R.id.rbPrivate);
        ivGroupImage = findViewById(R.id.ivGroupImage);
    }
}