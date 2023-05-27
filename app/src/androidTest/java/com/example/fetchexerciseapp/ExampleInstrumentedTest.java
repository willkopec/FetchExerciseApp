package com.example.fetchexerciseapp;

import android.content.Context;

import androidx.constraintlayout.widget.Group;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fetchexerciseapp", appContext.getPackageName());
    }

    @Test
    public void CustomAdapter() {
        //Check child count for adapter
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ChildInfo thisData1 = new ChildInfo();
        thisData1.setId("1");
        thisData1.setName("Item 1");
        thisData1.setlistId("1");

        ChildInfo thisData2 = new ChildInfo();
        thisData1.setId("15");
        thisData1.setName("Item 15");
        thisData1.setlistId("1");

        ChildInfo thisData3 = new ChildInfo();
        thisData1.setId("17");
        thisData1.setName("Item 17");
        thisData1.setlistId("2");

        ChildInfo thisData4 = new ChildInfo();
        thisData1.setId("25");
        thisData1.setName("Item 25");
        thisData1.setlistId("3");

        ArrayList<ChildInfo> myData = new ArrayList<>();

        myData.add(thisData1);
        myData.add(thisData2);
        myData.add(thisData3);
        myData.add(thisData4);

        GroupInfo theProductData = new GroupInfo();
        theProductData.setProductList(myData);

        ArrayList<GroupInfo> theProducts = new ArrayList<>();
        theProducts.add(theProductData);

        CustomAdapter myAdapter = new CustomAdapter(appContext, theProducts);

        assertEquals(4, myAdapter.getChildrenCount(0));
    }
}