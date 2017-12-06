package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.view.loader.HomeHotTabLoader;
import edu.virginia.cs.vmarketplace.view.loader.ProductItemDOLoader;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class HomeFragment extends AbstractFragment implements
        LoaderManager.LoaderCallbacks<List<ProductItemsDO>>{

    private AbstractFragment[] fragments;
    private HomePostListAdapter homePostListAdapter;

    public HomeFragment() {
        super("home", R.drawable.home_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        Toolbar toolbar =
                rootView.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);

        // set up list items
        ListView listView = rootView.findViewById(R.id.home_list);
        homePostListAdapter = new HomePostListAdapter(getActivity(),
                new ArrayList<ProductItemsDO>());
        listView.setAdapter(homePostListAdapter);
        getLoaderManager().restartLoader(0, null, this).forceLoad();
        System.out.println("HomeTabHotFragment called");

        // setup view pager for tabs
//        initFragments();
//        ViewPager viewPager = rootView.findViewById(R.id.home_tab_viewpager);
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
//        viewPager.setAdapter(viewPagerAdapter);
//        TabLayout tabLayout = rootView.findViewById(R.id.tab);
//        tabLayout.setupWithViewPager(viewPager);
//
//        for(int i = 0; i < fragments.length;i++){
//            tabLayout.getTabAt(i).setIcon(fragments[i].getIconResourceId());
//        }

        // set up tab fragments
        Fragment newFragment = new HomeTabNewFragment();
        Fragment hotFragment = new HomeTabHotFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.tab_container1, newFragment).commit();
        getChildFragmentManager().beginTransaction()
                .add(R.id.tab_container2, hotFragment).commit();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initFragments() {
        fragments = new AbstractFragment[2];
        fragments[0] = new HomeTabNewFragment();
        fragments[1] = new HomeTabHotFragment();
    }

    @Override
    public Loader<List<ProductItemsDO>> onCreateLoader(int id, Bundle args) {
        return new ProductItemDOLoader(getContext(), new HomeHotTabLoader());
    }

    @Override
    public void onLoadFinished(Loader<List<ProductItemsDO>> loader, List<ProductItemsDO> data) {
        homePostListAdapter.clear();
        homePostListAdapter.addAll(data);
        System.out.println("onLoadFinished called in " + HomeFragment.class);
    }

    @Override
    public void onLoaderReset(Loader<List<ProductItemsDO>> loader) {
        homePostListAdapter.clear();
    }
}
