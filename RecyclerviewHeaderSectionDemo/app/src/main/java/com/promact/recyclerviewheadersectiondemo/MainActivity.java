package com.promact.recyclerviewheadersectiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView sectionHeader;
    private SectionedRecyclerViewAdapter sectionSectionsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionHeader = (RecyclerView)findViewById(R.id.add_header);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        sectionHeader.setLayoutManager(linearLayoutManager);
        sectionHeader.setHasFixedSize(true);

        HeaderRecyclerViewSection firstSection = new HeaderRecyclerViewSection("First Section", getDataSource());
        HeaderRecyclerViewSection secondSection = new HeaderRecyclerViewSection("Second Section", getDataSource());
        HeaderRecyclerViewSection thirdSection = new HeaderRecyclerViewSection("Third Section", getDataSource());
        sectionSectionsAdapter = new SectionedRecyclerViewAdapter();
        sectionSectionsAdapter.addSection(firstSection);
        sectionSectionsAdapter.addSection(secondSection);
        sectionSectionsAdapter.addSection(thirdSection);
        sectionHeader.setAdapter(sectionSectionsAdapter);
    }
    private List<ItemObject> getDataSource(){
        List<ItemObject> data = new ArrayList<>();
        data.add(new ItemObject("This is the item content in the first position"));
        data.add(new ItemObject("This is the item content in the second position"));
        return data;
    }
}