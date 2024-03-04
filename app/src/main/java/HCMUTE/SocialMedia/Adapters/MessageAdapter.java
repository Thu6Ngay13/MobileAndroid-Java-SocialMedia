package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Enums.TypeReceiveMessageEnum;
import HCMUTE.SocialMedia.Holders.MessageHolder;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Utils.ImageUtil;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private Context context;
    private List<MessageModel> messages;

    public MessageAdapter(Context context, List<MessageModel> messageCards) {
        this.context = context;
        this.messages = messageCards;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TypeReceiveMessageEnum.YOU.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_card_view, parent, false));
        else
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        MessageModel messageCardModel = messages.get(position);
        if(messageCardModel.getViewType() == TypeReceiveMessageEnum.YOU){
            holder.avatar.setImageResource(messageCardModel.getAvatar());
            holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());
            holder.textMessage.setText(messageCardModel.getText());
        }
        else if (messageCardModel.getViewType() == TypeReceiveMessageEnum.FRIEND){
            holder.avatar.setImageResource(messageCardModel.getAvatar());
            holder.fullname.setText(messageCardModel.getFullname());
            holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());
            holder.textMessage.setText(messageCardModel.getText());
        }
        else {
            return;
        }

        if(messageCardModel.getMedia() > 0) {
            holder.mediaMessage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    holder.mediaMessage.getViewTreeObserver().removeOnPreDrawListener(this);

                    Resources resources = context.getResources();
                    int imageResourceId = messageCardModel.getMedia();
                    int[] dimensions = ImageUtil.getImageDimensions(resources, imageResourceId);

                    int img_width = dimensions[0];
                    int img_height = dimensions[1];

                    int width = holder.mediaMessage.getWidth();
                    int height = width * img_height / img_width;

                    holder.mediaMessage.setImageResource(messageCardModel.getMedia());
                    holder.mediaMessage.getLayoutParams().height = height;
                    holder.mediaMessage.requestLayout();

                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position){
        return messages.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        return 0;
    }
}
