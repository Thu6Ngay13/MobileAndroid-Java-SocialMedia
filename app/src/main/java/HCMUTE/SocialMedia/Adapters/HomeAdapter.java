package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.HomeHolder;
import HCMUTE.SocialMedia.Models.HomeModel;
import HCMUTE.SocialMedia.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeHolder> {

    private Context context;
    private List<HomeModel> homes;

    public HomeAdapter(Context context, List<HomeModel> homes) {
        this.context = context;
        this.homes = homes;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(context).inflate(R.layout.home_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        HomeModel homeModel = homes.get(position);

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvPostArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new PostAdapter(context.getApplicationContext(), homeModel.getPostModels()));
    }

    @Override
    public int getItemCount() {
        if (homes != null)
            return homes.size();
        return 0;
    }
}
