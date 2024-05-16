package HCMUTE.SocialMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.SettingCardAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.SettingCardModel;
import HCMUTE.SocialMedia.R;

public class SettingFragment extends Fragment {

    public SettingFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ImageView) view.findViewById(R.id.ibAvatar)).setImageResource(R.mipmap.ic_user_72_dark);
        ((TextView) view.findViewById(R.id.tvFullname)).setText("Jonhny Deep");

        if(Const.ROLE.equals("ADMIN")){
            List<SettingCardModel> settingCardModels = new ArrayList<>();
            settingCardModels.add(new SettingCardModel(R.mipmap.ic_admin_72_dark, "Admin"));

            RecyclerView recyclerView = view.findViewById(R.id.rvSettingArea);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new SettingCardAdapter(getContext(), settingCardModels));
        }
    }
}