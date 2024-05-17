package HCMUTE.SocialMedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Activities.LoginActivity;
import HCMUTE.SocialMedia.Adapters.SettingCardAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.SettingCardModel;
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

        Glide.with(getContext()).load(Const.AVATAR).into(((ImageView) view.findViewById(R.id.ibAvatar)));
        ((TextView) view.findViewById(R.id.tvFullname)).setText(Const.FULLNAME);

        if (Const.ROLE.equals("ADMIN")) {
            List<SettingCardModel> settingCardModels = new ArrayList<>();
            settingCardModels.add(new SettingCardModel(R.mipmap.ic_admin_72_dark, "Admin"));

            RecyclerView recyclerView = view.findViewById(R.id.rvSettingArea);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new SettingCardAdapter(getContext(), settingCardModels));
            TextView tvLogout = view.findViewById(R.id.tvLogout);
            tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefManager.getInstance(context).logout();
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
