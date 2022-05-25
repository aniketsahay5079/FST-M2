import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

    private static ArrayList<String> list;

    @BeforeEach
    public void setup() {
        list = new ArrayList<>();
        list.add("apple");
        list.add("orange");
    }

    @Test
    public void insertTest() {
        assertEquals(2, list.size(), "Wrong size");

        list.add("pineapple");
        assertEquals(3, list.size(), "Wrong size");

        assertEquals("apple", list.get(0), "Wrong element");
        assertEquals("orange", list.get(1), "Wrong element");
        assertEquals("pineapple", list.get(2), "Wrong element");
    }

    @Test
    public void replaceTest() {
        assertEquals(2, list.size(), "Wrong size");

        list.add("mango");
        assertEquals(3, list.size(), "Wrong size");

        list.set(1, "banana");

        assertEquals("apple", list.get(0), "Wrong element");
        assertEquals("banana", list.get(1), "Wrong element");
        assertEquals("mango", list.get(2), "Wrong element");
    }
}
