package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.util.IntentUtil;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfilePublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);
        ListView listView = findViewById(R.id.profile_detail_list);
        ProfilePublishItemAdapter adapter = new ProfilePublishItemAdapter(this, getPublishList());
        listView.setAdapter(adapter);
    }

    //Get from backend service
    private List<PublishItem> getPublishList(){
        List<PublishItem> list = new ArrayList<PublishItem>();
        PublishItem item = new PublishItem();
        item.setId(1);
        item.setTitle("Cool Kid");
        item.setPrice(100.0);
        item.setReplyCount(5);
        item.setViewCount(200);
        item.setProductType("Second Hand");
        item.setImage("https://s3.amazonaws.com/vmarketplace/product/bag.png");
        list.add(item);
        item = new PublishItem();
        item.setId(2);
        item.setTitle("Bao");
        item.setPrice(200.0);
        item.setReplyCount(3);
        item.setViewCount(100);
        item.setProductType("Ride");
        list.add(item);

        item = new PublishItem();
        item.setId(3);
        item.setTitle("Bao");
        item.setPrice(200.0);
        item.setReplyCount(3);
        item.setViewCount(100);
        item.setProductType("Sublease");
        item.setImage("https://s3.amazonaws.com/vmarketplace/product/Apartment.png");
        list.add(item);


        return list;
    }

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not using the Support Library
       return IntentUtil.jumpWithTabRecorded(AppConstant.TAB_PROFILE, this, MainActivity.class);
    }
}