package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Enums.TypeMessageEnum;
import HCMUTE.SocialMedia.Holders.MessageHolder;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

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
        if(viewType == TypeMessageEnum.SENDER_MESSAGE.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_card_view, parent, false));
        else if(viewType == TypeMessageEnum.SENDER_MEDIA.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_media_card_view, parent, false));
        else if(viewType == TypeMessageEnum.RECEIVER_MESSAGE.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_card_view, parent, false));
        else
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_media_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        MessageModel messageCardModel = messages.get(position);
        holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());

        if(messageCardModel.getViewType() == TypeMessageEnum.SENDER_MESSAGE){
            holder.textMessage.setText(messageCardModel.getText());
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.SENDER_MEDIA){
            holder.mediaMessage.setImageBitmap(messageCardModel.getMedia());
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.RECEIVER_MESSAGE){
            holder.textMessage.setText(messageCardModel.getText());
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.RECEIVER_MEDIA){
            holder.mediaMessage.setImageBitmap(messageCardModel.getMedia());
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
