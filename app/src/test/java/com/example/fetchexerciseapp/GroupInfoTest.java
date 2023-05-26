package com.example.fetchexerciseapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GroupInfoTest {
    //Test for both get and set name
    @Test
    void getName() {
        ChildInfo subsection1 = new ChildInfo();
        subsection1.setName("Item 152");
        subsection1.setId("152");
        subsection1.setlistId("10");

        assertEquals("Item 152", subsection1.getName());

    }

    //Test for both get and set name
    @Test
    void getId() {
        ChildInfo subsection1 = new ChildInfo();
        subsection1.setName("Item 152");
        subsection1.setId("152");
        subsection1.setlistId("10");

        assertEquals("152", subsection1.getId());

    }

    //Test for both get and set name
    @Test
    void getListId() {
        ChildInfo subsection1 = new ChildInfo();
        subsection1.setName("Item 152");
        subsection1.setId("152");
        subsection1.setlistId("10");

        assertEquals("10", subsection1.getlistId());

    }
}