package com.promact.recyclerviewheaderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(getHeader(),getListItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public  Header getHeader()
    {
        Header header = new Header();
        header.setHeader("I'm header");
        header.setHeader("HEllo");
        return header;
    }

    public List<Object> getListItems()
    {
        List<Object> listItems = new ArrayList<>();
        for (int i = 0; i<10; i++) {
            ListItem item = new ListItem();
            item.setName("image" + i);
            if (i % 2 == 0)
                item.setId(R.drawable.shipment);
            else
                item.setId(R.drawable.shipment3);
            listItems.add(item);
        }
        return listItems;
    }
}
