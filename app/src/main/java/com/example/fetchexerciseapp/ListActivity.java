package com.example.fetchexerciseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private LinkedHashMap<String, GroupInfo> productsArray = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> listIdsArray = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    public static Map<Object, List<record>> sortedMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Get Display List button and set onClick to show list page
        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Get Display List button and set onClick to show list page
        Button printButton = (Button) findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortedMap.forEach((key, value) ->
                        //Access the list in each key and add the lists of each listId to the Extendable ListView
                        value.forEach((v) ->
                                System.out.println("ListID: " + key.toString() + " Product Name : " + v.name + " Product ID: " + Integer.toString(v.id))
                        )
                );
            }
        });

        //Iterate through the sorted listIds (Key) and List<record> value in each key.
        //sortedMap was created by getData() function in MainActivity.java.
        //In getData function, we sorted the data appropriately and stored it
        //into Map<Object, List<record>> sortedMap.
        sortedMap.forEach((key, value) ->
                //Access the list in each key and add the lists of each listId to the Extendable ListView
                value.forEach((v) ->
                        addProduct(key.toString(), v.name, Integer.toString(v.id))
                )
        );

        //Get the extendable list view
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        //Create the custom adapter for the extendable list
        listAdapter = new CustomAdapter(ListActivity.this, listIdsArray);
        //Set the adapter for the extendable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //Uncomment if you want to expand all groups immediately
        //expandAll();

        //SetOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = listIdsArray.get(groupPosition);
                //get the child info
                ChildInfo productInfo = headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Tapped Item " + productInfo.getName() + " , In ListID: " + headerInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        //A listener for ListID heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the ListID Header
                GroupInfo headerInfo = listIdsArray.get(groupPosition);
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
        GroupInfo headerInfo = productsArray.get(listId);
        //Add the group if doesn't exist
        if (headerInfo == null) {
            headerInfo = new GroupInfo();
            headerInfo.setName(listId);
            productsArray.put(listId, headerInfo);
            listIdsArray.add(headerInfo);
        }

        ArrayList<ChildInfo> productList = headerInfo.getProductList();

        //Create a new product and add that it's listId group
        ChildInfo productInfo = new ChildInfo();
        productInfo.setlistId(id);
        productInfo.setName(name);
        productInfo.setId(listId);
        productList.add(productInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = listIdsArray.indexOf(headerInfo);
        return groupPosition;
    }
}