package com.example.fetchexerciseapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListActivity extends AppCompatActivity {

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    public static Map<Object, List<record>> sortedMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Iterate through the sorted listIds (Key) and List<record> value in each key
        sortedMap.forEach((key, value) ->
                //Access the list in each key and add the lists of each listId to the Extendable ListView
                value.forEach((v) ->
                        addProduct(key.toString(), v.name, Integer.toString(v.id))
                )
        );

        //Get the extendable list view
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        //Create the custom adapter for the extendable list
        listAdapter = new CustomAdapter(ListActivity.this, deptList);
        //Set the adapter for the extendable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //Uncomment if you want to expand all groups immediately
        //expandAll();

        //SetOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo = headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Tapped Item " + detailInfo.getName() + " , In ListID: " + headerInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        //A listener for ListID heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the ListID Header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Tapped ListIds: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();

                return false;
            }
        });


    }

    //A method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.expandGroup(i);
        }
    }

    //A method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.collapseGroup(i);
        }
    }


    //A method to add each item into the extendable list view
    private int addProduct(String listId, String id, String name) {

        int groupPosition = 0;

        //Check the map if the group already exists
        GroupInfo headerInfo = subjects.get(listId);
        //Add the group if doesn't exist
        if (headerInfo == null) {
            headerInfo = new GroupInfo();
            headerInfo.setName(listId);
            subjects.put(listId, headerInfo);
            deptList.add(headerInfo);
        }

        ArrayList<ChildInfo> productList = headerInfo.getProductList();

        //Create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setlistId(id);
        detailInfo.setName(name);
        detailInfo.setId(listId);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
}