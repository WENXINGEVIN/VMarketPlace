package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;

import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.util.IntentUtil;
import edu.virginia.cs.vmarketplace.view.loader.PublishItemLoader;


/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfilePublishActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PublishItem>> {
    private SwipeRefreshLayout refreshLayout;
    private ProfilePublishItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.published_by_me);

        final ListView listView = findViewById(R.id.profile_detail_list);
        adapter = new ProfilePublishItemAdapter(this, new ArrayList<PublishItem>());
        listView.setAdapter(adapter);

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().restartLoader(0, null, ProfilePublishActivity.this).forceLoad();
            }
        });

        refreshLayout.setRefreshing(true);
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
       return IntentUtil.jumpWithTabRecorded(AppConstant.TAB_PROFILE, this, MainActivity.class);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PublishItemLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<PublishItem>> loader, List<PublishItem> data) {
        adapter.clear();
        adapter.addAll(data);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }
}
