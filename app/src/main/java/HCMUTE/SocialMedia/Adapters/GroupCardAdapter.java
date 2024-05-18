package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Holders.GroupCardHolder;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Utils.ProcessTime;

public class GroupCardAdapter extends RecyclerView.Adapter<GroupCardHolder> {

    private Context context;
    private List<GroupModel> groupCards;

    public GroupCardAdapter(Context context, List<GroupModel> groupCards) {
        this.context = context;
        this.groupCards = groupCards;
    }

    @NonNull
    @Override
    public GroupCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupCardHolder(LayoutInflater.from(context).inflate(R.layout.group_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupCardHolder holder, int position) {
        GroupModel groupCardModel = groupCards.get(position);
        Glide.with(context)
                .load(groupCardModel.getAvatarURL())
                .into(holder.avatar);

        String holderFullName = groupCardModel.getHolderFullName();
        String groupName = groupCardModel.getGroupName();

        holder.holderFullName.setText(holderFullName);
        holder.groupName.setText(groupName);
    }

    @Override
    public int getItemCount() {
        if (groupCards != null)
            return groupCards.size();
        return 0;
    }
}
