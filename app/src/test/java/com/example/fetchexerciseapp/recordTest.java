package com.example.fetchexerciseapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions.*;

class recordTest {

    @org.junit.jupiter.api.Test
    void getId() {
        record data1 = new record(1,9,"Banana");
        record data2 = new record(6, 3, "Apple");
        assertEquals(1, data1.getId());
        assertEquals(6, data2.getId());
    }

    @org.junit.jupiter.api.Test
    void getListId() {
        record data1 = new record(7,1,"Orange");
        record data2 = new record(3, 4, "Pineapple");
        assertEquals(1, data1.getListId());
        assertEquals(4, data2.getListId());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        record data1 = new record(5,5,"Peach");
        record data2 = new record(4, 6, "Pear");
        assertEquals("Pear", data2.getName());
        assertEquals("Peach", data1.getName());
    }
}