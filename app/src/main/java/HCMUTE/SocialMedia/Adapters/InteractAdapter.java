package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.InteractHolder;
import HCMUTE.SocialMedia.Models.InteractModel;
import HCMUTE.SocialMedia.R;

public class InteractAdapter extends RecyclerView.Adapter<InteractHolder> {
    private Context context;
    private List<InteractModel> interacts;

    public InteractAdapter(Context context, List<InteractModel> interacts) {
        this.context = context;
        this.interacts = interacts;
    }

    @NonNull
    @Override
    public InteractHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InteractHolder(LayoutInflater.from(context).inflate(R.layout.interac_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InteractHolder holder, int position) {
        InteractModel interactModel = interacts.get(position);
        holder.interactIcon.setImageResource(interactModel.getInteractIcon());
        holder.interactDesc.setText(interactModel.getInteractDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (interactModel.getInteractDesc()) {
                    case "Like":
                        if (interactModel.getLiked()) {
                            interactModel.setLiked(false);
                            holder.interactIcon.setImageResource(R.mipmap.ic_like_72_line);
                        } else {
                            interactModel.setLiked(true);
                            holder.interactIcon.setImageResource(R.mipmap.ic_like_72_full);
                        }
                        break;
                    case "Comment":
                        Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
                        break;
                    case "Share":
                        Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (interacts != null)
            return interacts.size();
        return 0;
    }
}
