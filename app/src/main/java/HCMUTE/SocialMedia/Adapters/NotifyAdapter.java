package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.NotifyCardHolder;
import HCMUTE.SocialMedia.Holders.NotifyHolder;
import HCMUTE.SocialMedia.Models.NotifyModel;
import HCMUTE.SocialMedia.R;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyHolder> {

    private Context context;
    private List<NotifyModel> notifies;

    public NotifyAdapter(Context context, List<NotifyModel> notifies) {
        this.context = context;
        this.notifies = notifies;
    }

    @NonNull
    @Override
    public NotifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotifyHolder(LayoutInflater.from(context).inflate(R.layout.notify_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyHolder holder, int position) {
        NotifyModel notifyModel = notifies.get(position);
        holder.notifyTime.setText(notifyModel.getNotifyTime());

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvNotifyArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new NotifyCardAdapter(context.getApplicationContext(), notifyModel.getNotifyCardModels()));
    }

    @Override
    public int getItemCount() {
        if (notifies != null)
            return notifies.size();
        return 0;
    }
}
