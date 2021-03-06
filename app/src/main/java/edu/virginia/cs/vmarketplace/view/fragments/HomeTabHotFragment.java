package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeTabHotFragment extends AbstractFragment {

    private HomePostListAdapter homePostListAdapter;

    public HomeTabHotFragment() {
        super("hot", R.drawable.hot_icon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab, container, false);
        ListView listView = rootView.findViewById(R.id.home_tab_list);
        List<ProductItemsDO> list = new ArrayList<>();
        list.add(new ProductItemsDO());

        homePostListAdapter = new HomePostListAdapter(getActivity(),
                new ArrayList<>());
        listView.setAdapter(homePostListAdapter);
        getLoaderManager().restartLoader(0, null, new CommonLoaderCallback<Void, ProductItemsDO>(
                homePostListAdapter, ProductItemService.getInstance()::findTop100HotPostsInOneWeek
        )).forceLoad();
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductItemsDO itemsDO = homePostListAdapter.getItem(i);
                Intent intent = new Intent(HomeTabHotFragment.this.getActivity(), PublishDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.HOME_PAGE);
                AppContextManager.getContextManager().getAppContext().setItemsDO(itemsDO);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        System.out.println("HomeTabHotFragment called");
        return rootView;
    }
}
