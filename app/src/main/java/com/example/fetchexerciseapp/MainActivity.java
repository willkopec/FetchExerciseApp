package com.example.fetchexerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ArrayList<record> allData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve the data from the link, put it into the ArrayList, and set the sortedMap in ListActivity to the sorted data
        getData();

        //Get Display List button and set onClick to show list page
        Button displayListBtn = (Button) findViewById(R.id.button);
        displayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData(){
        /*
        Query the data from the link
        */
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,"https://fetch-hiring.s3.amazonaws.com/hiring.json",null,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //go through the data and add the data to the arraylist
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        allData.add(new record(Integer.parseInt(jsonObject.getString("id")),Integer.parseInt(jsonObject.getString("listId")),jsonObject.getString("name")));
                    }

                    //Filter out and sort all of the data as needed
                    Map<Object, List<record>> recordsGrouped = allData.stream().filter((record) -> record.name != "null") //filter out all names with null
                            .filter((record) -> record.name.length() >= 1)                                                  //filter out all the names with no text
                            .sorted(Comparator.comparing(record::getListId))                                                //first sorting ArrayList by listId
                            .sorted(Comparator.comparing(record::getName))                                                  //second sorting ArrayList by record names
                            .collect(Collectors.groupingBy(w -> w.listId));                                                 //group the lists by listIds and set list Id groups to the value of the listId key in the map

                    //Set the ListActivity's sorted map to the grouped map created above
                    ListActivity.sortedMap = recordsGrouped;

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("my-api","Something Went Wrong! :(");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }



}
/*

--------- Class: Record ----------------
A class created to store the API info. Includes a Constructor, getters, and setters.

*/
class record{
    int id;
    int listId;
    String name;

    public record(int id, int listId, String name){
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName(){
        return name;
    }

}