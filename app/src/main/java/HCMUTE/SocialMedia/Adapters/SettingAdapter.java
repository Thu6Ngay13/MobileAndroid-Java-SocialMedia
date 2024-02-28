package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.SettingHolder;
import HCMUTE.SocialMedia.Models.NotifyModel;
import HCMUTE.SocialMedia.Models.SettingModel;
import HCMUTE.SocialMedia.R;

public class SettingAdapter extends RecyclerView.Adapter<SettingHolder>{

    private Context context;
    private List<SettingModel> settings;

    public SettingAdapter(Context context, List<SettingModel> settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public SettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingHolder(LayoutInflater.from(context).inflate(R.layout.setting_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingHolder holder, int position) {
        SettingModel settingModel = settings.get(position);
        holder.avatar.setImageResource(settingModel.getAvatar());
        holder.fullname.setText(settingModel.getFullName());

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvSettingArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SettingCardAdapter(context.getApplicationContext(), settingModel.getSettingCardModels()));
    }

    @Override
    public int getItemCount() {
        if (settings != null)
            return settings.size();
        return 0;
    }
}
