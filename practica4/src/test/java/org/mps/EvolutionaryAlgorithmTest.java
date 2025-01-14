package org.mps;

import org.mps.crossover.*;
import org.mps.selection.*;
import org.mps.mutation.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

/**
 * @author Mario Cortés Herrera
 * @author Javier Molina Colmenero
 */

/**
 * NOTA: La coverage es del 100% excepto en SwapMutation.mutate() 
 * debido a que una rama de esa función nunca se puede recorrer desde la ejecución
 * de EvolutionaryAlgorithm.optimize() porque la función TournamentSelection.select()
 * salta primero la excepción por los mismos motivos que la función mutate()
 */

public class EvolutionaryAlgorithmTest {

    EvolutionaryAlgorithm algorithm;
    SelectionOperator selection;
    MutationOperator mutation;
    CrossoverOperator crossover;

    @Nested
    @DisplayName("Operaciones cuando se ejecuta el algoritmo de evolución con datos de entrada erróneos.")
    class FailedBuild_Tests {

        @Test
        @DisplayName("El crossoverOperator es nulo")
        public void FailedBuild_CrossoverOperator_isNull() throws EvolutionaryAlgorithmException {
            // Arrange
            selection = new TournamentSelection(6);
            mutation = new SwapMutation();
            crossover = null;
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> algorithm = new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("El mutationOperator es nulo")
        public void FailedBuild_MutationOperator_isNull() throws EvolutionaryAlgorithmException {
            // Arrange
            selection = new TournamentSelection(6);
            mutation = null;
            crossover = new OnePointCrossover();
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> algorithm = new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("El SelectionOperator es nulo")
        public void FailedBuild_SelectionOperator_isNull() throws EvolutionaryAlgorithmException {
            // Arrange
            selection = null;
            mutation = new SwapMutation();
            crossover = new OnePointCrossover();
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> algorithm = new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Todos los argumentos son nulos")
        public void FailedBuild_AllOperators_areNull() throws EvolutionaryAlgorithmException {
            // Arrange
            selection = null;
            mutation = null;
            crossover = null;
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> algorithm = new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("El SelectionOperator no se construye con tournamentSize mayor que cero")
        public void FailedBuild_SelectionOperator_hasNotMoreThanZeroTournamentSize()
                throws EvolutionaryAlgorithmException {
            // Arrange
            int tournamentSize = 0;
            mutation = new SwapMutation();
            crossover = new OnePointCrossover();
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> algorithm = new EvolutionaryAlgorithm(new TournamentSelection(tournamentSize), mutation,
                    crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

    }

    @Nested
    @DisplayName("Operaciones con Optimize")
    class OptimizePopulation_Tests {

        @BeforeEach
        @DisplayName("Vamos a considerar TournamentSize como 4 para siempre tener esta referencia posteriormente")
        public void setup() throws EvolutionaryAlgorithmException {
            // Arrange
            selection = new TournamentSelection(4);
            mutation = new SwapMutation();
            crossover = new OnePointCrossover();
            algorithm = new EvolutionaryAlgorithm(selection, mutation, crossover);
        }


        @Nested
        @DisplayName("Diferentes casos en los que se llama a Optimize con diferentes valores de entrada erróneos en population")
        class FailedOptimizePopulation_Tests {
            

            @Test
            @DisplayName("Realizar la función optimize a una population nula saltará una excepción")
            public void Optimize_NullPopulation_Test() {
                // Arrange
                int[][] population = null;
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "Poblacion no valida";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Realizar la función optimize a una population de tamaño cero saltará una excepción")
            public void Optimize_PopulationLengthZero_Test() {
                // Arrange
                int[][] population = {};
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "Poblacion no valida";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Realizar la función optimize a una population donde el índice cero es nulo saltará una excepción")
            public void Optimize_Population_IndexZeroIsNull_Test() {
                // Arrange
                int[][] population = { null, { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 3, 3, 3, 3 } };
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "Poblacion no valida";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Realizar la función optimize a una population donde el índice cero es de tamaño cero saltará una excepción")
            public void Optimize_Population_IndexZero_Length_Zero_Test() {
                // Arrange
                int[][] population = { {}, { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 3, 3, 3, 3 } };
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "Poblacion no valida";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Realizar la función optimize a una population donde el índice cero es de tamaño cero saltará una excepción")
            public void Optimize_Population_Odd_Length_Population() {
                // Arrange
                int[][] population = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 3, 3, 3, 3 } };
                Class<ArrayIndexOutOfBoundsException> expected = ArrayIndexOutOfBoundsException.class;
                Executable input;
                String expectedMsg = "Index 3 out of bounds for length 3";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Realizar la función optimize a una population donde el índice tres es nulo saltará una excepción (ejemplo de caso que no sea el índice cero)")
            public void Optimize_Population_IndexThreeIsNull_Test() {
                // Arrange
                int[][] population = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 3, 3, 3, 3 }, null };
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "Poblacion no valida";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Caso de ejemplo donde la population es de tamaño 4x(4,4,2,2) (hay un par de subarrays con lenght menor a 4) y salta excepción")
            public void Optimize_NxLTN_LenghtPopulation_Test() throws EvolutionaryAlgorithmException
            {
                // Arrange
                int [][] population = {{1,2,3,5},{8,1,0,2},{3,5},{1,3}};
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "No se ha podido realizar la selección";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

            @Test
            @DisplayName("Caso de ejemplo donde la population es de tamaño 4x(4,4,5,4) (hay un par de subarrays que su lenght no coincide para el crossover) y salta excepción")
            public void Optimize_NotGoodForCrossover_LenghtPopulation_Test() throws EvolutionaryAlgorithmException
            {
                // Arrange
                int [][] population = {{1,2,3,5},{8,1,0,2},{3,5,0,0,9},{1,3,6,7}};
                Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
                Executable input;
                String expectedMsg = "No se ha podido realizar el cruce";

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertThrows(expected, input, expectedMsg);
            }

        }

        @Nested
        @DisplayName("Diferentes casos en los que se llama a Optimize con diferentes valores de entrada correctos en population")
        class SucceededOptimizePopulation_Tests {

            @Test
            @DisplayName("Caso de ejemplo donde la population es de tamaño 4x4 (array bidimensional NxN) y se hace correctamente optimize")
            public void Optimize_NxNLenghtPopulation_Test() throws EvolutionaryAlgorithmException
            {
                // Arrange
                int [][] population = {{1,2,3,5},{8,1,0,2},{3,5,6,1},{1,3,9,6}};
                Executable input;

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertDoesNotThrow(input);
            }

            @Test
            @DisplayName("Caso de ejemplo donde la population es de tamaño 4x4 (array bidimensional NxN) y se hace correctamente optimize, utilizando la funcion better")
            public void Optimize_NxNLenghtPopulation_UsesBetterFunction_Test() throws EvolutionaryAlgorithmException
            {
                // Arrange
                int [][] population = { {1,2,3,4}, {99,11,22,33}, {1,2,3,4}, {4,3,2,1} };
                Executable input;

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertDoesNotThrow(input);
            }
            
            @Test
            @DisplayName("Caso de ejemplo donde la population es de tamaño 4x(4,4,5,5) (Hay un par de subarrays con lenght mayor a 4) y se hace correctamente optimize")
            public void Optimize_NxMTN_LenghtPopulation_Test() throws EvolutionaryAlgorithmException
            {
                // Arrange
                int [][] population = {{1,2,3,5},{8,1,0,2},{3,5,6,4,1},{1,3,0,0,1}};
                Executable input;

                // Act
                input = () -> algorithm.optimize(population);

                // Assert
                assertDoesNotThrow(input);
            }

        }

        @Nested
        @DisplayName("Getters y setters")
        class GetterSetter_Tests {

            @Nested
            @DisplayName("Getters")
            class Getter_Tests {

                @Test
                @DisplayName("Geter de MutationOperation")
                public void Getter_MutationOperator() {
                    // Arrange
                    MutationOperator expected = mutation;
                    MutationOperator result;

                    // Act
                    result = algorithm.getMutationOperator();

                    // Assert
                    assertEquals(expected, result);
                }

                @Test
                @DisplayName("Geter de SelectionOperator")
                public void Getter_SelectionOperator() {
                    // Arrange
                    SelectionOperator expected = selection;
                    SelectionOperator result;

                    // Act
                    result = algorithm.getSelectionOperator();

                    // Assert
                    assertEquals(expected, result);
                }

                @Test
                @DisplayName("Geter de CrossoverOperator")
                public void Getter_CrossoverOperation() {
                    // Arrange
                    CrossoverOperator expected = crossover;
                    CrossoverOperator result;

                    // Act
                    result = algorithm.getCrossoverOperator();

                    // Assert
                    assertEquals(expected, result);
                }
            }
        
            @Nested
            @DisplayName("Setters")
            class Setter_Tests {
                
                @Test
                @DisplayName("Setter de SelectionOperator")
                public void Setter_SelectionOperator() throws EvolutionaryAlgorithmException {
                    // Arrange
                    SelectionOperator expected = new TournamentSelection(33);
                    SelectionOperator result;

                    // Act
                    algorithm.setSelectionOperator(expected);
                    result = algorithm.getSelectionOperator();

                    // Assert
                    assertEquals(expected, result);
                }

                @Test
                @DisplayName("Setter de MutationOperator")
                public void Setter_MutationOperator() throws EvolutionaryAlgorithmException {
                    // Arrange
                    MutationOperator expected = new SwapMutation();
                    MutationOperator result;

                    // Act
                    algorithm.setMutationOperator(expected);
                    result = algorithm.getMutationOperator();

                    // Assert
                    assertEquals(expected, result);
                }

                @Test
                @DisplayName("Setter de CrossoverOperator")
                public void Setter_CrossoverOperator() throws EvolutionaryAlgorithmException {
                    // Arrange
                    CrossoverOperator expected = new OnePointCrossover();
                    CrossoverOperator result;

                    // Act
                    algorithm.setCrossoverOperator(expected);
                    result = algorithm.getCrossoverOperator();

                    // Assert
                    assertEquals(expected, result);
                }
            }
        }

    }

}