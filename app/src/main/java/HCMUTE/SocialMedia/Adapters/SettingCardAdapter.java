package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.SettingCardHolder;
import HCMUTE.SocialMedia.Models.SettingCardModel;
import HCMUTE.SocialMedia.R;

public class SettingCardAdapter extends RecyclerView.Adapter<SettingCardHolder> {

    private Context context;
    private List<SettingCardModel> settingCards;

    public SettingCardAdapter(Context context, List<SettingCardModel> settingCards) {
        this.context = context;
        this.settingCards = settingCards;
    }

    @NonNull
    @Override
    public SettingCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingCardHolder(LayoutInflater.from(context).inflate(R.layout.setting_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingCardHolder holder, int position) {
        SettingCardModel settingCardModel = settingCards.get(position);
        holder.imageFunction.setImageResource(settingCardModel.getImageFunction());
        holder.functionName.setText(settingCardModel.getFuntionName());
    }

    @Override
    public int getItemCount() {
        if (settingCards != null)
            return settingCards.size();
        return 0;
    }
}
