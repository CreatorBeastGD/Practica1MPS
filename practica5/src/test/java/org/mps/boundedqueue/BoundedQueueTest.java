package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class BoundedQueueTest
{
    public BoundedQueue<Integer> queue;

    @Nested
    @DisplayName("Tests relacionados a fallos usando argumentos erróneos.")
    class IncorrectArguments_Tests {
        
        @Test
        @DisplayName("Cuando se inicializa con capacidad negativa devuelve excepción.")
        public void NegativeCapacity_Test() 
        {
            // Arrange
            Class<IllegalArgumentException> expected = IllegalArgumentException.class;
            String expectedMsg = "ArrayBoundedException: capacity must be positive";
            ThrowingCallable tc;
            
            // Act
            tc = () -> {queue = new ArrayBoundedQueue<Integer>(-33);};

            // Assert
            assertThatExceptionOfType(expected)
                .isThrownBy(tc)
                .withMessage(expectedMsg);
        }

        @Test
        @DisplayName("Cuando se inicializa con capacidad de 0 devuelve excepción.")
        public void NullCapacity_Test() {
            // Arrange
            Class<IllegalArgumentException> expected = IllegalArgumentException.class;
            String expectedMsg = "ArrayBoundedException: capacity must be positive";
            ThrowingCallable tc;
            
            // Act
            tc = () -> {queue = new ArrayBoundedQueue<Integer>(0);};

            // Assert
            assertThatExceptionOfType(expected)
                .isThrownBy(tc)
                .withMessage(expectedMsg);
        }

        @Test
        @DisplayName("Cuando se hace put() con un argumento nulo lanza excepción")
        public void Put_NullValue_Test()
        {
            // Arange
            queue = new ArrayBoundedQueue<Integer>(2);
            ThrowingCallable tc;
            Class<IllegalArgumentException> expected = IllegalArgumentException.class;
            String expectedMsg = "put: element cannot be null";

            // Act
            tc = () -> queue.put(null);

            // Assert
            assertThatExceptionOfType(expected)
                .isThrownBy(tc)
                .withMessage(expectedMsg);
        }
    }


    @Nested
    @DisplayName("Tests que se realizan una vez la clase está bien construida.")
    class SuccessBuild_Tests
    {
        @BeforeEach
        public void setup()
        {
            // Arrange
            queue = new ArrayBoundedQueue<Integer>(5);
        }

        @Nested
        @DisplayName("Tests realizados con la queue vacía.")
        class EmptyQueue_Tests 
        {
            @Test
            @DisplayName("Una queue vacía devuelve true al llamar a isEmpty.")
            public void isEmpty_EmptyQueue_Test()
            {
                // Arrange
                boolean output;

                // Act
                output = queue.isEmpty();

                // Assert
                assertThat(output).isTrue();
            }

            @Test
            @DisplayName("Una queue vacía devuelve false al llamar a isFull.")
            public void isFull_EmptyQueue_Test()
            {
                // Arrange
                boolean output;

                // Act
                output = queue.isFull();

                // Assert
                assertThat(output).isFalse();
            }

            @Test
            @DisplayName("Una queue vacía tiene tamaño cero.")
            public void size_EmptyQueue_Test()
            {
                // Arrange
                int output,
                    expected = 0;

                // Act 
                output = queue.size();

                // Assert
                assertThat(output).isEqualTo(expected);
            }

            @Test
            @DisplayName("Una queue vacía devuelve cero al llamar a getFirst().")
            public void getFirst_EmptyQueue_Test()
            {
                // Arrange
                Integer output,
                        expected = 0;

                // Act 
                output = queue.getFirst();

                // Assert
                assertThat(output).isEqualTo(expected);
            }

            @Test
            @DisplayName("Una queue vacía devuelve cero al llamar a getLast().")
            public void getLast_EmptyQueue_Test()
            {
                // Arrange
                Integer output,
                        expected = 0;

                // Act 
                output = queue.getFirst();

                // Assert
                assertThat(output).isEqualTo(expected);
            }

            @Test
            @DisplayName("Llamar a get() en una queue vacía lanza una excepción.")
            public void get_EmptyQueue_Test()
            {
                // Arrange
                Class<EmptyBoundedQueueException> expected = EmptyBoundedQueueException.class;
                String expectedMsg = "get: empty bounded queue";
                ThrowingCallable tc;

                // Act
                tc = () -> queue.get();
                
                // Assert
                assertThatExceptionOfType(expected)
                    .isThrownBy(tc)
                    .withMessage(expectedMsg);
            }

            @Test
            @DisplayName("LLamar a put() en una queue vacía ocurre con éxito.")
            public void put_EmptyQueue_Test()
            {
                // Arrange
                ThrowingCallable tc;

                // Act
                tc = () -> queue.put(33);

                // Assert
                assertThatNoException().isThrownBy(tc);
            }

            @Nested
            @DisplayName("Tests con el iterador de una queue vacía.")
            class Iterator_EmptyQueue_Tests
            {
                Iterator<Integer> iterator;

                @BeforeEach
                public void setup()
                {
                    iterator = queue.iterator();
                }

                @Test
                @DisplayName("El iterador de una queue vacía no tiene siguiente elemento.")
                public void hasNext_EmptyQueue_Test()
                {
                    // Arrange
                    boolean output;
                    
                    // Act
                    output = iterator.hasNext();

                    // Assert
                    assertThat(output).isFalse();
                }

                @Test
                @DisplayName("El iterador de una queue vacía, al hacer next() lanza excepción.")
                public void next_EmptyQueue_Test()
                {
                    // Arrange
                    Class<NoSuchElementException> expected = NoSuchElementException.class;
                    String expectedMsg = "next: bounded queue iterator exhausted";
                    ThrowingCallable tc;

                    // Act
                    tc = () -> iterator.next();

                    // Assert
                    assertThatExceptionOfType(expected)
                        .isThrownBy(tc)
                        .withMessage(expectedMsg);
                }


            }

        }

        @Nested
        @DisplayName("Pruebas realizadas con una queue con un elemento.")
        class OneElementQueue_Tests
        {
            @BeforeEach
            public void setup() {
                queue.put(1);
            }
            
            @Test
            @DisplayName("El size de la queue es uno")
            public void size_ReturnsOne_Test()
            {
                // Arrange
                int output,
                    expected = 1;

                // Act 
                output = queue.size();

                // Assert
                assertThat(output).isEqualTo(expected);
            }
            
            @Test
            @DisplayName("El elemento getLast() es el mismo que el siguiente del primero")
            public void GetLast_Equals_GetFirstPlusOne_Test() {
                // Arrange
                int first=0, last=0;

                // Act 
                first = queue.getFirst();
                last = queue.getLast();

                // Assert
                assertThat(first).isEqualTo(0);
                assertThat(last).isEqualTo(first + 1);
            }

            @Test
            @DisplayName("Una lista de capacidad 5 con un elemento no estará lleno")
            public void IsFull_ReturnsFalse_Test() {
                // Arrange
                boolean res;

                // Act 
                res = queue.isFull();

                // Assert
                assertThat(res).isFalse();
            }

            @Test
            @DisplayName("Una lista de capacidad 5 con un elemento no estará vacío")
            public void IsEmpty_ReturnsFalse_Test() {
                // Arrange
                boolean res;

                // Act 
                res = queue.isEmpty();

                // Assert
                assertThat(res).isFalse();
            }
            
            @Test
            @DisplayName("Llamar a get() devuelve el único elemento de queue la primera vez y lanza excepción la segunda vez")
            public void get_OnlyOneElement_Test()
            {
                // Arrange
                Integer expectedValue = 1, output;
                Class<EmptyBoundedQueueException> expected = EmptyBoundedQueueException.class;
                String expectedMsg = "get: empty bounded queue";
                ThrowingCallable tc;

                // Act
                output = queue.get();
                tc = () -> queue.get();
                
                // Assert
                assertThat(output).isEqualTo(expectedValue);
                assertThatExceptionOfType(expected)
                    .isThrownBy(tc)
                    .withMessage(expectedMsg);
            }

            @Test
            @DisplayName("Se puede realizar put() size-1 veces y la siguiente lanzará excepción")
            public void put_OnlySizeMinusOneTimes_Test()
            {
                // Arrange
                ThrowingCallable [] tc = new ThrowingCallable[5];
                Integer [] values = {1,2,3,4,5};
                Class<FullBoundedQueueException> expected = FullBoundedQueueException.class;
                String expectedMsg = "put: full bounded queue";

                // Act
                tc[0] = () -> queue.put(values[0]);
                tc[1] = () -> queue.put(values[1]);
                tc[2] = () -> queue.put(values[2]);
                tc[3] = () -> queue.put(values[3]);
                tc[4] = () -> queue.put(values[4]);

                // Assert
                for(int i=0; i<4; i++)
                {
                    assertThatNoException().isThrownBy(tc[i]);
                }
                
                assertThatExceptionOfType(expected)
                    .isThrownBy(tc[4])
                    .withMessage(expectedMsg);
                
            }
            
            @Nested
            @DisplayName("Pruebas con un iterador de una lista con un elemento")
            class IteratorWithOneElement {
                Iterator<Integer> iterator;

                @BeforeEach
                public void setup() {
                    iterator = queue.iterator();
                }

                @Test
                @DisplayName("Al hacer hasNext con un iterador con un elemento, devolverá true")
                public void hasNext_OneElementQueue_Test() {
                    // Arrange
                    boolean output;
                        
                    // Act
                    output = iterator.hasNext();
    
                    // Assert
                    assertThat(output).isTrue();
                }

                @Test
                @DisplayName("Al hacer next con un iterador de una lista de un elemento, se devolverá el objeto.")
                public void next_OneElementQueue_Test() {
                    // Arrange
                    Integer output;
                        
                    // Act
                    output = iterator.next();
    
                    // Assert
                    assertThat(output).isEqualTo(1);
                }

                @Test
                @DisplayName("Cuando se hace dos veces Next, entonces devolverá el primer elemento y después hará excepción")
                public void multipleNext_OneElementQueue_Test() {
                    Integer firstRes;
                    Class<NoSuchElementException> expected = NoSuchElementException.class;
                    String expectedMsg = "next: bounded queue iterator exhausted";
                    ThrowingCallable tc;

                    // Act
                    firstRes = iterator.next();
                    tc = () -> iterator.next();

                    // Assert
                    assertThat(firstRes).isEqualTo(1);
                    assertThatExceptionOfType(expected)
                        .isThrownBy(tc)
                        .withMessage(expectedMsg);
                }
            }
        }

        @Nested
        @DisplayName("Pruebas realizadas con una queue con varios elementos (pero no completa).")
        class VariousElementQueue_Tests
        {
            @BeforeEach
            public void setup() {
                queue.put(1);
                queue.put(2);
                queue.put(3);
                queue.put(4);
            }

            @Test
            @DisplayName("Una lista que tenga entre 0 y 5 elemenentos no inclusive, no estará ni llena ni vacía")
            public void IsEmpty_IsFull_VariousQueue_Test() {
                // Arrange
                boolean result;
                
                // Act
                result = queue.isEmpty() || queue.isFull();

                // Assert
                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Añadir un elemento más hará que el elemento getLast sea el primero")
            public void Put_GetLast_VariousQueue_Test() {
                // Arrange
                Integer first, last;

                // Act
                queue.put(5);
                first = queue.getFirst();
                last = queue.getLast();
                

                // Assert
                assertThat(first).isEqualTo(last);
            }
        }

        @Nested
        @DisplayName("Pruebas realizadas con una queue completa.")
        class FullQueue_Tests
        {
            @BeforeEach
            public void setup() {
                queue.put(1);
                queue.put(2);
                queue.put(3);
                queue.put(4);
                queue.put(5);
            }

            @Test
            @DisplayName("El size de la queue es cinco")
            public void size_ReturnsOne_Test()
            {
                // Arrange
                int output,
                    expected = 5;

                // Act 
                output = queue.size();

                // Assert
                assertThat(output).isEqualTo(expected);
            }

            @Test
            @DisplayName("Cuando se intenta hacer put sobre una queue completa se salta excepcion")
            public void put_FullQueue_Test() {
                // Arrange   
                Class<FullBoundedQueueException> expected = FullBoundedQueueException.class;
                 
                String expectedMsg = "put: full bounded queue";
                ThrowingCallable tc;

                // Act
                tc = () -> queue.put(33);

                // Assert
                assertThatExceptionOfType(expected)
                    .isThrownBy(tc)
                    .withMessage(expectedMsg);
            }

            @Test
            @DisplayName("Cuando se hace Get de todos los elementos, la lista quedará vacía y first y nextfree serán lo mismo")
            public void getAll_FullQueue_Test() {
                // Arrange
                        
                // Act
                queue.get();
                queue.get();
                queue.get();
                queue.get();
                queue.get();
                

                // Assert
                assertThat(queue.getFirst()).isEqualTo(queue.getLast());
            }

            @Test
            @DisplayName("Comprobar que una queue llena está llena dará true")
            public void isFull_FullQueue_Test() {
                // Arrange
                boolean result;

                // Act
                result = queue.isFull();

                // Assert
                assertThat(result).isTrue();
            }

            @Test
            @DisplayName("Una lista completa, no estará vacía")
            public void IsEmpty_FullQueue_Test() {
                // Arrange
                boolean result;
                
                // Act
                result = queue.isEmpty();

                // Assert
                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Getfirst y Getlast dan lo mismo")
            public void GetLast_GetFirst_FullQueue_Test() {
                // Arrange
                Integer result1, result2;

                // Act
                result1 = queue.getFirst();
                result2 = queue.getLast();

                // Assert
                assertThat(result1).isEqualTo(result2);
            }

            @Nested
            @DisplayName("Pruebas de un iterador sobre una lista llena")
            class Iterator_FullQueue_Tests {
                
                Iterator<Integer> iterator;
                
                @BeforeEach
                public void setup() {
                    iterator = queue.iterator();
                }

                @Test
                @DisplayName("Se hará next tantas veces como la capacidad del objeto, después lanza excepción")
                public void hasNextRoute_FullQueue_Test() {
                    // Arrange
                    int expectedTimes = queue.size();
                    int result = 0;
                    Class<NoSuchElementException> expected = NoSuchElementException.class;
                    String expectedMsg = "next: bounded queue iterator exhausted";
                    ThrowingCallable tc;
                    
                    // Act
                    while(iterator.hasNext()) {
                        iterator.next();
                        result++;
                    }
                    tc = () -> iterator.next();

                    // Assert
                    assertThat(expectedTimes).isEqualTo(result);
                    assertThatExceptionOfType(expected)
                        .isThrownBy(tc)
                        .withMessage(expectedMsg);
                }
            }
        }
    }
}