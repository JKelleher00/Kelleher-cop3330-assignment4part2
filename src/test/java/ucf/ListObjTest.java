package ucf;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListObjTest {
    @org.junit.jupiter.api.Test
    void a(){
        ListObj lo = new ListObj("1996/9/16","warn the world of 9/11",true);
        assertEquals("1996/9/16",lo.getDueDate());
        assertEquals("warn the world of 9/11",lo.getDescription());
        assertEquals("Y",lo.getCh());
    }
}