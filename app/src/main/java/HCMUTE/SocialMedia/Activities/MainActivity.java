package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import HCMUTE.SocialMedia.Adapters.MainPager2Adapter;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.RealTime.SocketIO;
import HCMUTE.SocialMedia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private MainPager2Adapter mainPager2Adapter;
    private ImageButton ibMessage;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketIO.connectToServer();
        setupTabs();
        ibMessage = (ImageButton) findViewById(R.id.ibMessage);
        ibMessage.setOnClickListener(view -> onClickMessage());

    }

    private void setupTabs() {
        ActivityMainBinding inflate = ActivityMainBinding.inflate(getLayoutInflater());
        this.activityMainBinding = inflate;
        setContentView(inflate.getRoot());

        TabLayout.Tab homeTab = this.activityMainBinding.customTabLayout.newTab();
        TabLayout.Tab friendTab = this.activityMainBinding.customTabLayout.newTab();
        TabLayout.Tab notifyTab = this.activityMainBinding.customTabLayout.newTab();
        TabLayout.Tab settingTab = this.activityMainBinding.customTabLayout.newTab();

        homeTab.setIcon(R.mipmap.ic_home_72_line);
        friendTab.setIcon(R.mipmap.ic_friend_72_line);
        notifyTab.setIcon(R.mipmap.ic_bell_72_line);
        settingTab.setIcon(R.mipmap.ic_setting_72_light);

        this.activityMainBinding.customTabLayout.addTab(homeTab);
        this.activityMainBinding.customTabLayout.addTab(friendTab);
        this.activityMainBinding.customTabLayout.addTab(notifyTab);
        this.activityMainBinding.customTabLayout.addTab(settingTab);

        FragmentManager fragmentManager = getSupportFragmentManager();
        this.mainPager2Adapter = new MainPager2Adapter(fragmentManager, getLifecycle());
        this.activityMainBinding.viewPager2.setAdapter(this.mainPager2Adapter);
        this.activityMainBinding.customTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: HCMUTE.SocialMedia.Activities.MainActivity.1
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.this.activityMainBinding.viewPager2.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(MainActivity.this.getApplicationContext(), R.color.green_dark);
                ((Drawable) Objects.requireNonNull(tab.getIcon())).setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this.getApplicationContext(), R.color.black);
                ((Drawable) Objects.requireNonNull(tab.getIcon())).setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.activityMainBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                MainActivity.this.activityMainBinding.customTabLayout.selectTab(MainActivity.this.activityMainBinding.customTabLayout.getTabAt(position));
            }
        });

        this.activityMainBinding.customTabLayout.selectTab(this.activityMainBinding.customTabLayout.getTabAt(0));
        ((Drawable) Objects.requireNonNull(friendTab.getIcon())).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_IN);
        ((Drawable) Objects.requireNonNull(notifyTab.getIcon())).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_IN);
        ((Drawable) Objects.requireNonNull(settingTab.getIcon())).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_IN);
    }


    private void onClickMessage() {
        Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
        startActivity(intent);
    }
}