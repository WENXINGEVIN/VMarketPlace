package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;

import com.amazonaws.mobile.client.AWSMobileClient;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.service.AnalyticService;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PlaceFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PublishFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ViewPagerAdapter;

import static edu.virginia.cs.vmarketplace.model.AppConstant.SWITCH_TAB;

/**
 * Created by cutehuazai on 11/21/17.
 */

public class MainActivity extends AppCompatActivity{
    public static String PACKAGE_NAME;

    private AbstractFragment[] fragments;

    private int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticService.getInstance().logNormalUsage(Application.getPinpointManager(getApplicationContext()));
        PACKAGE_NAME = getApplicationContext().getPackageName();
        position = 0;
        initFragments();
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < fragments.length;i++){
            tabLayout.getTabAt(i).setIcon(fragments[i].getIconResourceId());
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 2){
                    Intent startPublishActivityIntent = new Intent(MainActivity.this, PublishActivity.class);
                    startPublishActivityIntent.putExtra(AppConstant.PREVIOUS_TAB, position);
                    startActivity(startPublishActivityIntent);
                }
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(SWITCH_TAB)) {
            int tabIndex = intent.getExtras().getInt(SWITCH_TAB);
            tabLayout.getTabAt(tabIndex).select();
        }
    }

    private void initFragments(){
        fragments = new AbstractFragment[5];
        fragments[0] = new HomeFragment();
        fragments[1] = new PlaceFragment();
        fragments[2] = new PublishFragment();
        fragments[3] = new MessageFragment();
        fragments[4] = new ProfileFragment();
    }
}
