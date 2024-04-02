package deque;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.function.Executable;

/**
 * @author1: Mario Cortés Herrera
 * @author2: Javier Molina Colmenero
 */

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

    @Nested
    @DisplayName("Get Tests")
    class GetTests {

        @Test
        @DisplayName("Hacer operacion get sobre una lista vacía devuelve excepción")
        public void GetOnEmptyLinkedListReturnsException() {
            //Arrange
            int index = 0;
            Class<IndexOutOfBoundsException> except = IndexOutOfBoundsException.class;
            String message = "ERROR: Índice introducido incorrecto";

            // Act 
            Executable exec = () -> dll.get(index);

            //Assert
            assertThrows(except, exec, message);

        }

        @Nested
        @DisplayName("En la lista hay al menos un elemento")
        class GetTestWithOneElement {
            @BeforeEach
            public void setup() {
                Integer value = 33;
                dll.append(value);
            }

            @Test
            @DisplayName("Hacer get sobre una lista con un elemento del indice 0 devuelve ese objeto")
            public void Get0thElementOfOneElementLinkedListReturnsObject() {
                // Arrange
                int index = 0;
                Integer expected = 33;

                // Act
                Integer result = dll.get(index);

                // Assert
                assertEquals(expected, result);
            }

            @Test
            @DisplayName("Hacer get con indice negativo lanza excepción")
            public void GetNegativeElementFromLinkedListThrowsException() {
                //Arrange
                int index = -1;
                Class<IndexOutOfBoundsException> except = IndexOutOfBoundsException.class;
                String message = "ERROR: Índice introducido incorrecto";

                // Act 
                Executable exec = () -> dll.get(index);

                //Assert
                assertThrows(except, exec, message);
            }

            @Nested
            @DisplayName("Lista con más de un elemento")
            class GetTestWithMultipleElements {
                @BeforeEach
                @DisplayName("Añadir más elementos")
                public void setup() {
                    Integer element1 = 12;
                    Integer element2 = 90;
                    Integer element3 = 43;

                    dll.append(element1);
                    dll.append(element2);
                    dll.append(element3);
                }

                @Test
                @DisplayName("Hacer get con un elemento válido es correcto")
                public void GetElementWithCorrectIndexReturnsThatElement() {
                    // Arrange
                    int index = 2;
                    Integer expected = 90;

                    // Act
                    Integer result = dll.get(index);

                    // Assert
                    assertEquals(expected, result);
                }

                @Test
                @DisplayName("Hacer get del ultimo elemento de la lista devuelve el ultimo elemento")
                public void GetElementWithLastElementIndexReturnsLastElement() {
                    // Arrange
                    int index = dll.size()-1;
                    Integer expected = dll.last();
                    
                    // Act
                    Integer result = dll.get(index);
                    
                    // Assert
                    assertEquals(expected, result);
                }
            }


        }


    }

    @Nested
    @DisplayName("Contains Tests")
    class ContainsTests {

        @Test
        @DisplayName("Llamar a contains a una lista sin nodos da false")
        public void ContainsCallOnEmptyListRetunsFalse()
        {
            // Arrange
            boolean output;               

            // Act
            output = dll.contains(1);

            // Assert
            assertFalse(output);
        }

        @Test
        @DisplayName("Llamar a contains donde la lista solo tiene un elemento y es el que se comprueba")
        public void ContainsCallOnOneSizeListReturnsTrue()
        {
            // Arrange
            boolean output;
            Integer value = 9;

            // Act 
            dll.append(value);
            output = dll.contains(value);

            // Assert
            assertTrue(output);
        }

        @Test
        @DisplayName("Llamar a contains donde la lista solo tiene un elemento y no es el que se comprueba")
        public void ContainsCallOnOneSizeListReturnsFalse()
        {
            // Arrange
            boolean output;
            Integer value = 9, checkValue = 8;

            // Act 
            dll.append(value);
            output = dll.contains(checkValue);

            // Assert
            assertFalse(output);
        }

        @Test
        @DisplayName("Llamar a contains donde la lista tiene más de un elemento y contiene el que se indica")
        public void ContainsCallOnMoreThanOneSizeListReturnsTrue()
        {
            // Arrange
            boolean output;
            Integer value1 = 9,
                    value2 = 1,
                    value3 = 6;

            // Act 
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            output = dll.contains(value1);

            // Assert
            assertTrue(output);
        }

        @Test
        @DisplayName("Llamar a contains donde la lista tiene más de un elemento y no contiene el que se indica")
        public void ContainsCallOnMoreThanOneSizeListReturnsFalse()
        {
            // Arrange
            boolean output;
            Integer value1 = 9,
                    value2 = 1,
                    value3 = 6,
                    checkValue = 3;

            // Act 
            dll.append(value1);
            dll.append(value2);
            dll.append(value3);
            output = dll.contains(checkValue);

            // Assert
            assertFalse(output);
        }

    }

    @Nested
    @DisplayName("Remove Tests")
    class RemoveTests {
        @Test
        @DisplayName("Remover un elemento de una lista vacía no hace nada")
        public void RemoveFromEmptyLinkedListDoesNothing() {
            // Arange
            int expected = 0;
            Integer value = 1;
            
            // Act
            dll.remove(value);
            int result = dll.size();

            // Assert
            assertEquals(expected, result);
        }

        @Nested
        @DisplayName("Al añadir un elemento")
        class RemoveTestWithOneElement {
            @BeforeEach
            public void setup() {
                Integer value = 33;
                dll.append(value);
            }

            @Test
            @DisplayName("Eliminar el valor correcto devuelve la lista vacía")
            public void RemoveElementFromOneElementListEndsUpInEmptyList() {
                // Arrange
                Integer element = 33;
                int expectedSize = 0;

                // Act
                dll.remove(element);
                int result = dll.size();

                // Assert
                assertEquals(expectedSize, result);
            }

            @Test
            @DisplayName("Eliminar un valor que no está mantiene la lista como antes")
            public void RemoveElementNotInOneElementListDoesNothing() {
                // Arrange
                Integer element = 32;
                int expectedSize = 1;

                // Act
                dll.remove(element);
                int result = dll.size();

                // Assert
                assertEquals(expectedSize, result);
            }

            @Nested
            @DisplayName("La lista tiene ahora 3 elementos")
            class RemoveTestThreeElements {
                @BeforeEach
                public void setup() {
                    Integer element1 = 55;
                    Integer element2 = 12;

                    dll.append(element1);
                    dll.append(element2);
                }

                @Test
                @DisplayName("Al eliminar el primer elemento de la lista, tiene un numero menos de elementos y el first es el segundo")
                public void whenRemovingFirstElement() {
                    // Arrange
                    Integer value = 33;
                    int expectedSize = 2;
                    Integer expectedFirst = dll.get(1);

                    // Act
                    dll.remove(value);
                    int resultSize = dll.size();
                    Integer resultFirst = dll.first();

                    // Assert
                    assertEquals(expectedFirst, resultFirst);
                    assertEquals(expectedSize, resultSize);
                }

                @Test
                @DisplayName("Al eliminar el ultimo elemento de la lista, tiene un numero menos de elementos y el last es el segundo")
                public void whenRemovingLastElement() {
                    // Arrange
                    Integer value = 12;
                    int expectedSize = 2;
                    Integer expectedLast = dll.get(1);

                    // Act
                    dll.remove(value);
                    int resultSize = dll.size();
                    Integer resultLast = dll.last();

                    // Assert
                    assertEquals(expectedLast, resultLast);
                    assertEquals(expectedSize, resultSize);
                }

                @Test
                @DisplayName("Al eliminar el elemento del medio de la lista, tiene un numero menos de elementos")
                public void whenRemovingMidElement() {
                    // Arrange
                    Integer value = 55;
                    int expectedSize = 2;

                    // Act
                    dll.remove(value);
                    int resultSize = dll.size();

                    // Assert
                    assertEquals(expectedSize, resultSize);
                }
            }


        }

    }

    @Nested
    @DisplayName("Sort Tests")
    class SortTests {
        
        Comparator<Integer> comparator;

        @BeforeEach
        @DisplayName("Inicialización del Comparator")
        public void setup()
        {
            comparator = (x,y) -> x - y;
        }

        @Test
        @DisplayName("Realizar un sort a una lista vacía")
        public void SortEmptyList()
        {
            // Arrange
            Integer expected = 0, output;

            // Act
            dll.sort(comparator);
            output = dll.size();

            // Assert
           assertEquals(expected, output);
        }

        @Test
        @DisplayName("Realizar un sort a una lista de un elemento")
        public void SortOneSizeList()
        {
            // Arrange
            Integer value = 3, output1, output2;

            // Act
            dll.append(value);
            dll.sort(comparator);
            output1 = dll.first();
            output2 = dll.last();

            // Assert
           assertEquals(value, output1);
           assertEquals(value, output2);
        }

        @Test
        @DisplayName("Realizar un sort a una lista de varios elementos desordenados")
        public void SortNotSortedList()
        {
            // Arrange
            Integer[] outputs = new Integer[10],
                      values = {9, -1, 3, 0, 10, -20, 8, 1, 33, 4},
                      expected = {-20, -1, 0, 1, 3, 4, 8, 9, 10, 33};

            // Act
            for(Integer v : values)
            {
                dll.append(v);
            }

            dll.sort(comparator);

            for(int i=0; i<dll.size(); i++)
            {
                outputs[i] = dll.get(i);
            }
            
            // Assert
            for(int i=0; i<dll.size(); i++)
            {
                assertEquals(expected[i], outputs[i]);
            }
        }

        @Test
        @DisplayName("Realizar un sort a una lista de varios elementos ordenados")
        public void SortSortedList()
        {
            // Arrange
            Integer[] outputs = new Integer[10],
                      values = {-20, -1, 0, 1, 3, 4, 8, 9, 10, 33},
                      expected = {-20, -1, 0, 1, 3, 4, 8, 9, 10, 33};

            // Act
            for(Integer v : values)
            {
                dll.append(v);
            }

            dll.sort(comparator);

            for(int i=0; i<dll.size(); i++)
            {
                outputs[i] = dll.get(i);
            }
            
            // Assert
            for(int i=0; i<dll.size(); i++)
            {
                assertEquals(expected[i], outputs[i]);
            }
        }
    }   

}
