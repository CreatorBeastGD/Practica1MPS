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
        }

        @Nested
        @DisplayName("Pruebas realizadas con una queue con varios elementos (pero no completa).")
        class VariousElementQueue_Tests
        {

        }

        @Nested
        @DisplayName("Pruebas realizadas con una queue completa.")
        class FullQueue_Tests
        {

        }
    }

}