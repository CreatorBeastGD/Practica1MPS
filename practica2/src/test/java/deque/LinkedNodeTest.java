package deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LinkedNodeTest {
    LinkedNode<Integer> node;

    @BeforeEach
    @DisplayName("Creacion del LinkedNode, un nodo con un objeto y sin un elemento siguiente o previo.")
    public void setup() {
        Integer item = 2;
        node = new LinkedNode<Integer>(item, null, null);
    }

    @Nested
    @DisplayName("Cuando un elemento LinkedNode no tiene ni siguiente ni anterior")
    class WhenNew {
        @Test
        @DisplayName("Hacer getItem sobre un linkedNode devuelve el objeto")
        public void GetItemFromALinkedNodeReturnsItem() {
            // Arrange
            Integer expected = 2;

            // Act
            Integer result = node.getItem();

            // Assert
            assertEquals(expected, result);
            
        }

        @Test
        @DisplayName("Hacer setItem sobre un LinkedNode debería cambiar el ítem que tiene asignado")
        public void SetItemToALinkedNodeSetsNewItem() {
            // Arrange
            Integer newVal = 33;
            Integer expected = 33;

            // Act
            node.setItem(newVal);
            Integer result = node.getItem();

            // Assert
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Sobre un nodo solitario, al obtener el nodo anterior, obtenemos nulo")
        public void GetPreviousWithNoPreviousReturnsNull() {
            // Arrange

            // Act
            LinkedNode<Integer> result = node.getPrevious();

            // Assert
            assertNull(result);
        }

        @Test
        @DisplayName("Sobre un nodo solitario, al obtener el nodo siguiente, obtenemos nulo")
        public void GetNextWithNoPreviousReturnsNull() {
            // Arrange

            // Act
            LinkedNode<Integer> result = node.getNext();

            // Assert
            assertNull(result);
        }

        @Test
        @DisplayName("Un nodo que no tiene un nodo siguiente o anterior, deberia ser el primer y ultimo nodo en una cadena de nodos")
        public void NodeWithNoPreviousOrNextShouldBeFirstAndLastNode() {
            // Arrange

            // Act
            boolean result = node.isFirstNode() && node.isLastNode();

            // Assert
            assertTrue(result);
        }

        @Test
        @DisplayName("Un nodo que esté solo será un nodo terminal")
        public void MiddleNodeWillNotBeATerminalNode() {
            // Arrange
            boolean result;

            // Act
            result = node.isNotATerminalNode();

            // Assert
            assertFalse(result);
        }

        @Nested
        @DisplayName("Al nodo le añadimos un nodo siguiente y anterior")
        class WhenHavingPreviousAndNextNode {
            LinkedNode<Integer> prevNode;
            LinkedNode<Integer> nextNode;

            @BeforeEach
            @DisplayName("Añadimos los dos nuevos nodos")
            public void AddNodes() {
                prevNode = new LinkedNode<Integer>(1, null, null);
                nextNode = new LinkedNode<Integer>(3, null, null);

                node.setPrevious(prevNode);
                node.setNext(nextNode);

                prevNode.setNext(node);
                nextNode.setPrevious(node);
            }

            @Test
            @DisplayName("Al obtener el previo del nodo, obtenemos prevNode")
            public void GettingPreviousNodeFromLinkedNodeReturnsPreviousNode() {
                // Arrange
                LinkedNode<Integer> expected = prevNode;

                // Act
                LinkedNode<Integer> result = node.getPrevious();

                // Assert
                assertEquals(expected, result);
            }

            @Test
            @DisplayName("Al obtener el siguiente del nodo, obtenemos nextNode")
            public void GettingNextNodeFromLinkedNodeReturnsNextNode() {
                // Arrange
                LinkedNode<Integer> expected = nextNode;

                // Act
                LinkedNode<Integer> result = node.getNext();

                // Assert
                assertEquals(expected, result);
            }

            @Test
            @DisplayName("El nodo del medio no será el nodo inicial, pero si lo será prevNode")
            public void MiddleNodeIsNotFirstNode() {
                // Arrange
                boolean result1, result2;
            
                // Act
                result1 = node.isFirstNode();
                result2 = node.getPrevious().isFirstNode(); // El previo de node es prevNode

                // Assert
                assertFalse(result1);
                assertTrue(result2);
            }

            @Test
            @DisplayName("El nodo del medio no será el nodo final, pero si lo será nextNode")
            public void MiddleNodeIsNotLastNode() {
                // Arrange
                boolean result1, result2;
            
                // Act
                result1 = node.isLastNode();
                result2 = node.getNext().isLastNode(); // El siguiente de node es nextNode

                // Assert
                assertFalse(result1);
                assertTrue(result2);
            }

            @Test
            @DisplayName("El nodo del medio no será un nodo terminal")
            public void MiddleNodeWillNotBeATerminalNode() {
                // Arrange
                boolean result;

                // Act
                result = node.isNotATerminalNode();

                // Assert
                assertTrue(result);
            }

            @Test
            @DisplayName("Los nodos de los extremos son nodos terminales")
            public void ExtremeNodesWillBeTerminalNodes() {
                // Arrange
                boolean result1, result2;

                // Act
                result1 = node.getNext().isNotATerminalNode();
                result2 = node.getPrevious().isNotATerminalNode();

                // Assert
                assertFalse(result1);
                assertFalse(result2);
            }
        }


    }

}
