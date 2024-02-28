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

import java.util.List;

import HCMUTE.SocialMedia.Holders.NotifyCardHolder;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.R;

public class NotifyCardAdapter extends RecyclerView.Adapter<NotifyCardHolder> {

    private Context context;
    private List<NotifyCardModel> notifyCards;

    public NotifyCardAdapter(Context context, List<NotifyCardModel> notifyCards) {
        this.context = context;
        this.notifyCards = notifyCards;
    }

    @NonNull
    @Override
    public NotifyCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotifyCardHolder(LayoutInflater.from(context).inflate(R.layout.notify_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyCardHolder holder, int position) {
        NotifyCardModel notifyCardModel = notifyCards.get(position);
        holder.avatar.setImageResource(notifyCardModel.getAvatar());


        String fullName = notifyCardModel.getFullName();
        String content = notifyCardModel.getContent();
        SpannableString fullNameAndContent = new SpannableString(fullName + " " + content);
//      FullName
        fullNameAndContent.setSpan(new StyleSpan(Typeface.BOLD), 0, fullName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        fullNameAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_fullname), 0, fullName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//      Content
        fullNameAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_content), fullName.length() + 1, fullNameAndContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.fullNameAndContent.setText(fullNameAndContent);
        holder.notifyTimeAt.setText(notifyCardModel.getNotifyTimeAt());
    }

    @Override
    public int getItemCount() {
        if (notifyCards != null)
            return notifyCards.size();
        return 0;
    }
}
