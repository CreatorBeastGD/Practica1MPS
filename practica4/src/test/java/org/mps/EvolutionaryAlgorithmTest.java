package org.mps;

import org.mps.crossover.*;
import org.mps.selection.*;
import org.mps.mutation.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EvolutionaryAlgorithmTest {

    EvolutionaryAlgorithm algorithm;
    
    @Nested
    @DisplayName("Operaciones cuando se ejecuta el algoritmo de evolución con datos de entrada erróneos.")
    class FailedBuild_Tests
    {
        
        SelectionOperator selection;
        MutationOperator mutation;
        CrossoverOperator crossover;

        @Test
        @DisplayName("El crossoverOperator es nulo")
        public void FailedBuild_CrossoverOperator_isNull() throws EvolutionaryAlgorithmException 
        {
            // Arrange
            selection = new TournamentSelection(6);
            mutation = new SwapMutation();
            crossover = null;
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("El mutationOperator es nulo")
        public void FailedBuild_MutationOperator_isNull() throws EvolutionaryAlgorithmException 
        {
            // Arrange
            selection = new TournamentSelection(6);
            mutation = null;
            crossover = new OnePointCrossover();
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("El SelectionOperator es nulo")
        public void FailedBuild_SelectionOperator_isNull() throws EvolutionaryAlgorithmException 
        {
            // Arrange
            selection = null;
            mutation = new SwapMutation();
            crossover = new OnePointCrossover();
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }

        @Test
        @DisplayName("Todos los argumentos son nulos")
        public void FailedBuild_AllOperators_areNull() throws EvolutionaryAlgorithmException 
        {
            // Arrange
            selection = null;
            mutation = null;
            crossover = null;
            Class<EvolutionaryAlgorithmException> expected = EvolutionaryAlgorithmException.class;
            Executable input;
            String expectedMsg = "Argumentos nulos";

            // Act
            input = () -> new EvolutionaryAlgorithm(selection, mutation, crossover);

            // Assert
            assertThrows(expected, input, expectedMsg);
        }
    }

    @Nested
    @DisplayName("hap")
    class Hapter
    {

    }

}