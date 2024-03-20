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


    @Nested
    @DisplayName("DeleteFirst Tests")
    class DeleteFirstTests {

        @Test
        @DisplayName("Borrar el primer elemento en una lista vacía lanza excepción")
        public void DeleteFirstOnEmptyListThrowsException() 
        {
            // Arrange
            Class<DoubleLinkedQueueException> expected = DoubleLinkedQueueException.class;
            String expectedMsg = "[ERROR]: Empty deque";

            // Act
            Executable input = () -> dll.deleteFirst();

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Borrar el primer elemento de una lista con un solo elemento la deja vacía")
        public void DeleteFirstOnOneSizeListMakesItEmpty() 
        {
            // Arrange
            Integer expectedSize = 0,
                    value = 33,
                    output;

            // Act
            dll.prepend(value);
            dll.deleteFirst();
            output = dll.size();

            // Assert
            assertEquals(expectedSize, output);
        }

        @Test
        @DisplayName("Borrar el primer elemento de una lista con varios elementos causa que el segundo elemento sea el primero")
        public void DeleteFirstOnNotEmptyListMakesSecondNodeFirst()
        {
            // Arrange
            Integer value1 = 1, value2 = 2,
                    expected = 2, output;
            
            // Act
            dll.append(value1);
            dll.append(value2);
            dll.deleteFirst();
            output = dll.first();

            // Assert
            assertEquals(expected, output);
        }
    }


    @Nested
    @DisplayName("DeleteLast Tests")
    class DeleteLastTests {

        @Test
        @DisplayName("Borrar el último elemento en una lista vacía lanza excepción")
        public void DeleteLastOnEmptyListThrowsException() 
        {
            // Arrange
            Class<DoubleLinkedQueueException> expected = DoubleLinkedQueueException.class;
            String expectedMsg = "[ERROR]: Empty deque";

            // Act
            Executable input = () -> dll.deleteLast();

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Borrar el último elemento de una lista con un solo elemento la deja vacía")
        public void DeleteLastOnOneSizeListMakesItEmpty() 
        {
            // Arrange
            Integer expectedSize = 0,
                    value = 33,
                    output;

            // Act
            dll.prepend(value);
            dll.deleteLast();
            output = dll.size();

            // Assert
            assertEquals(expectedSize, output);
        }

        @Test
        @DisplayName("Borrar el último elemento de una lista con varios elementos causa que el penúltimo elemento sea el último")
        public void DeleteLastOnNotEmptyListMakesBeforeLastNodeLast()
        {
            // Arrange
            Integer value1 = 1, value2 = 2,
                    expected = 1, output;
            
            // Act
            dll.prepend(value2);
            dll.prepend(value1);
            dll.deleteLast();
            output = dll.last();

            // Assert
            assertEquals(expected, output);
        }
    }


    @Nested
    @DisplayName("First Tests")
    class FirstTests {

        @Test
        @DisplayName("Obtener el primer nodo de una lista vacía lanza excepción")
        public void GetFirstOnEmptyListThrowsException()
        {
            // Arrange
            Class<DoubleLinkedQueueException> expected = DoubleLinkedQueueException.class;
            String expectedMsg = "[ERROR]: Empty deque";

            // Act 
            Executable input = () -> dll.first();

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Obtener el primer nodo de una lista no vacía")
        public void GetFirstOnNotEmptyList()
        {
            // Arrange
            Integer value1 = 1,
                    value2 = 2,
                    value3 = 3, output,
                    expected = 3;

            // Act
            dll.append(value3);
            dll.append(value2);
            dll.append(value1);
            output = dll.first();

            // Assert
            assertEquals(expected, output);
        }
    }


    @Nested
    @DisplayName("Last Tests")
    class LastTests {

        @Test
        @DisplayName("Obtener el último nodo de una lista vacía lanza excepción")
        public void GetLastOnEmptyListThrowsException()
        {
            // Arrange
            Class<DoubleLinkedQueueException> expected = DoubleLinkedQueueException.class;
            String expectedMsg = "[ERROR]: Empty deque";

            // Act 
            Executable input = () -> dll.last();

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Obtener el último nodo de una lista no vacía")
        public void GetLastOnNotEmptyList()
        {
            // Arrange
            Integer value1 = 1,
                    value2 = 2,
                    value3 = 3, output,
                    expected = 1;

            // Act
            dll.append(value3);
            dll.append(value2);
            dll.append(value1);
            output = dll.last();

            // Assert
            assertEquals(expected, output);
        }
    }


    @Nested
    @DisplayName("Size Tests")
    class SizeTests {

        @Test 
        @DisplayName("El tamaño de una lista vacía es cero")
        public void GetSizeOnEmptyListIsZero()
        {
            // Arrange
            Integer expected = 0, output;

            // Act
            output = dll.size();

            // Assert
            assertEquals(expected, output);
        }

        @Test
        @DisplayName("El tamaño de una lista no vacía será del número de elementos añadidos")
        public void GetSizeOnNotEmptyListIsNotZero()
        {
            // Arrange
            Integer value1 = 1,
                    value2 = 2,
                    value3 = 3, 
                    notExpected = 0, output;

            // Act
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            output = dll.size();
        
            // Assert
            assertNotEquals(notExpected, output);
        }

        @Test
        @DisplayName("El tamaño de una lista tras eliminar todos sus elementos es cero")
        public void GetSizeBeforeDeleteAllElementsIsZero()
        {
            // Arrange
            Integer value1 = 1,
                    value2 = 2,
                    value3 = 3, 
                    expected = 0, output;

            // Act
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            dll.deleteFirst();
            dll.deleteFirst();
            dll.deleteFirst();
            output = dll.size();
        
            // Assert
            assertEquals(expected, output);
        }

        @Test
        @DisplayName("El tamaño de una lista tras eliminar no todos sus elementos no es cero")
        public void GetSizeBeforeDeleteNotAllElementsIsNotZero()
        {
            // Arrange
            Integer value1 = 1,
                    value2 = 2,
                    value3 = 3, 
                    notExpected = 0, output;

            // Act
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            dll.deleteFirst();
            dll.deleteFirst();
            output = dll.size();
        
            // Assert
            assertNotEquals(notExpected, output);
        }
    }

}
