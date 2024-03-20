package deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DoubleLinkedListTest {

    DoubleLinkedList<Integer> dll;

    @BeforeEach
    public void setup()
    {
        dll = new DoubleLinkedList<Integer>();
    }


}
