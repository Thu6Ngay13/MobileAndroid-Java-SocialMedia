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
    private List<GroupCardHolder> groupCards;

    public GroupCardAdapter(Context context, List<GroupCardHolder> groupCards) {
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
        GroupCardHolder groupCardModel = groupCards.get(position);
        Glide.with(context)
                .load(groupCardModel.get())
                .into(holder.avatar);

        String fullName = notifyCardModel.getFullName();
        String content = notifyCardModel.getText();
        SpannableString fullNameAndContent = new SpannableString(fullName + " " + content);

        //      FullName
        fullNameAndContent.setSpan(new StyleSpan(Typeface.BOLD), 0, fullName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        fullNameAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_fullname), 0, fullName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //      Content
        fullNameAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_content), fullName.length() + 1, fullNameAndContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.fullNameAndContent.setText(fullNameAndContent);

        String timeInput = notifyCardModel.getNotifyTimeAt();
        List<String> timeResult = ProcessTime.getTimeFromString(timeInput);
        String timeShow = timeResult.get(0) + "-" + timeResult.get(1) + "-" + timeResult.get(2) + " " + timeResult.get(3) + ":" + timeResult.get(3);
        holder.notifyTimeAt.setText(timeShow);
    }

    @Override
    public int getItemCount() {
        if (notifyCards != null)
            return notifyCards.size();
        return 0;
    }
}
