package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import HCMUTE.SocialMedia.R;

public class CreatePostAdapter extends BaseAdapter {
    Context context;
    int modeImage[];
    String[] modeName;
    LayoutInflater inflter;

    public CreatePostAdapter(Context applicationContext, int[] modeImage, String[] modeName) {
        this.context = applicationContext;
        this.modeName = modeName;
        this.modeImage = modeImage;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return modeImage.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.ivMode);
        TextView names = (TextView) view.findViewById(R.id.tvModeName);
        icon.setImageResource(modeImage[i]);
        names.setText(modeName[i]);
        return view;
    }
}
