package HCMUTE.SocialMedia.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import HCMUTE.SocialMedia.Fragments.FriendFragment;
import HCMUTE.SocialMedia.Fragments.HomeFragment;
import HCMUTE.SocialMedia.Fragments.NotifyFragment;
import HCMUTE.SocialMedia.Fragments.SettingFragment;

public class MainPager2Adapter extends FragmentStateAdapter {

    private Context context;
    public MainPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new FriendFragment();
        }
        else if (position == 2) {
            return new NotifyFragment();
        }
        else if (position == 3) {
            return new SettingFragment(context);
        }
        else {
            return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
