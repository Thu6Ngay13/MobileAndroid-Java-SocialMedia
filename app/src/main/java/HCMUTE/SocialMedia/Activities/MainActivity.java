package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import HCMUTE.SocialMedia.Adapters.MainPager2Adapter;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 999;
    private ActivityMainBinding activityMainBinding;
    private MainPager2Adapter mainPager2Adapter;
    private ImageButton ibMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.customTabLayout.addTab(activityMainBinding.customTabLayout.newTab().setIcon(R.mipmap.ic_home_72_line));
        activityMainBinding.customTabLayout.addTab(activityMainBinding.customTabLayout.newTab().setIcon(R.mipmap.ic_friend_72_line));
        activityMainBinding.customTabLayout.addTab(activityMainBinding.customTabLayout.newTab().setIcon(R.mipmap.ic_bell_72_line));
        activityMainBinding.customTabLayout.addTab(activityMainBinding.customTabLayout.newTab().setIcon(R.mipmap.ic_setting_72_light));

        FragmentManager fragmentManager = getSupportFragmentManager();
        mainPager2Adapter = new MainPager2Adapter(fragmentManager, getLifecycle());
        activityMainBinding.viewPager2.setAdapter(mainPager2Adapter);

        activityMainBinding.customTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activityMainBinding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        activityMainBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                activityMainBinding.customTabLayout.selectTab(activityMainBinding.customTabLayout.getTabAt(position));
            }
        });

        ibMessage = findViewById(R.id.ibMessage);
        ibMessage.setOnClickListener(v -> onClickMessage());
    }

    private void onClickMessage(){
        Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
        startActivity(intent);
    }
}