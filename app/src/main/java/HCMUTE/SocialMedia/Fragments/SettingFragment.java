package HCMUTE.SocialMedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.bumptech.glide.Glide;

import HCMUTE.SocialMedia.Activities.GroupActivity;
import HCMUTE.SocialMedia.Activities.LoginActivity;
import HCMUTE.SocialMedia.Activities.MainActivity;
import HCMUTE.SocialMedia.Activities.MyPersonalPageActivity;
import HCMUTE.SocialMedia.Activities.PoliciesActivity;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;

public class SettingFragment extends Fragment {
    private Context context;

    public SettingFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(context).load(PrefManager.getAvatarURL()).into(((ImageView) view.findViewById(R.id.civAvatar)));
        TextView tvFullname = view.findViewById(R.id.tvFullname);
        tvFullname.setText(PrefManager.getFullname());
        tvFullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyPersonalPageActivity.class);
                startActivity(intent);
            }
        });

        GridLayout glGroup = view.findViewById(R.id.glGroup);
        glGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupActivity.class);
                startActivity(intent);
            }
        });

        GridLayout glPolicy = view.findViewById(R.id.glPolicy);
        glPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PoliciesActivity.class);
                startActivity(intent);
            }
        });

        GridLayout glLogout = view.findViewById(R.id.glLogout);
        glLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.getInstance(context).logout();
                Intent intent = new Intent(context, LoginActivity.class);
                closeActivity();
                startActivity(intent);
            }
        });
    }
    private void closeActivity() {
        if (context instanceof MainActivity) {
            ((MainActivity) context).finish();
        }
    }
}