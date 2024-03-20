package deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.function.Executable;

public class DoubleLinkedListTest {

    DoubleLinkedList<Integer> dll;

    @BeforeEach
    @DisplayName("Inicialización de la DLL")
    public void setup()
    {
        dll = new DoubleLinkedList<Integer>();
    }

    @Nested
    @DisplayName("Prepend Tests")
    class PrependTests {

        @Test
        @DisplayName("Añadir al inicio de la lista vacía un nodo hará que este sea el único que la lista presenta")
        public void PrependNodeWhenListIsEmpty()
        {
            // Arrange 
            Integer value = 33;
            Integer output1, output2;

            // Act
            dll.prepend(value);
            output1 = dll.first();
            output2 = dll.last();

            // Assert
            assertEquals(value, output1);
            assertEquals(value, output2);
        }

        @Test
        @DisplayName("Añadir varios nodos a la lista utilizando solo prepend, lo que significa que el orden de los nodos es inverso a como se añadieron")
        public void PrependVariousNodesInList()
        {
            // Arrange
            Integer value1 = 1, 
                    value2 = 2, 
                    value3 = 3;
            Integer output1, output3;

            // Act
            dll.prepend(value1);
            dll.prepend(value2);
            dll.prepend(value3);
            output3 = dll.first();
            output1 = dll.last();

            // Assert
            assertEquals(value1, output1);
            assertEquals(value3, output3);
            
        }
    }


    @Nested
    @DisplayName("Append Tests")
    class AppendTests {

        @Test
        @DisplayName("Añadir al final de la lista vacía un nodo hará que este sea el único que la lista presenta")
        public void AppendNodeWhenListIsEmpty()
        {
            // Arrange 
            Integer value = 33;
            Integer output1, output2;

            // Act
            dll.append(value);
            output1 = dll.first();
            output2 = dll.last();

            // Assert
            assertEquals(value, output1);
            assertEquals(value, output2);
        }

        @Test
        @DisplayName("Añadir varios nodos a la lista utilizando solo append, lo que significa que el orden de los nodos es igual a como se añadieron")
        public void AppendVariousNodesInList()
        {
            // Arrange
            Integer value1 = 1, 
                    value2 = 2, 
                    value3 = 3;
            Integer output1, output3;

            // Act
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            output1 = dll.first();
            output3 = dll.last();

            // Assert
            assertEquals(value1, output1);
            assertEquals(value3, output3);
            
        }
    }

}
