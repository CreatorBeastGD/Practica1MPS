package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

import org.mps.dispositivo.*;

@ExtendWith(MockitoExtension.class)
public class ronQI2SilverTest {

    @Mock
    DispositivoSilver disp;

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */

    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */
    @Nested
    class Inicializar_Tests {

        @Test
        @DisplayName("Tras añadir el dispositivo, si se inicializa el ronQI2 hara que se configura cada sensor una vez")
        public void InicializarRonQI2_Test()
        {
            // Step 1: Create the mock object
            RonQI2Silver ronq = new RonQI2Silver();
            ronq.anyadirDispositivo(disp);

            // Step 2: Define behaviour        
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);

            // Step 3: Execute
            assertTrue(ronq.inicializar());

            // Step 4: Verify
            // verify(disp, times(1)).conectarSensorPresion();
            // verify(disp, times(1)).conectarSensorSonido();
            verify(disp, times(1)).configurarSensorPresion();
            verify(disp, times(1)).configurarSensorPresion();
        }
    }
    
    
    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */

    @Nested
    @DisplayName("Pruebas del método reconectar")
    class Reconectar_Tests {

        RonQI2Silver ronq;

        @BeforeEach
        public void setup()
        {
            // Step 1: Create the mock object
            ronq = new RonQI2Silver();
            ronq.anyadirDispositivo(disp);
        }

        @Test
        @DisplayName("Si se llama a reconectar desde ronQI2 y el dispositivo no está conectado, llamará al conectar de ambos sensores")
        public void ReconectarRonQI2CuandoDispositivoNoConectado_Test()
        {
            // Step 2: Define behaviour        
            when(disp.estaConectado()).thenReturn(false);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);

            // Step 3: Execute
            assertTrue(ronq.reconectar());

            // Step 4: Verify
            verify(disp, times(1)).conectarSensorPresion();
            verify(disp, times(1)).conectarSensorSonido();
            verify(disp, times(1)).estaConectado();
        }

        @Test
        @DisplayName("Si se llama a reconectar desde ronQI2 y el dispositivo está conectado, no llamará al conectar de ambos sensores")
        public void ReconectarRonQI2CuandoDispositivoEstaConectado_Test()
        {
            // Step 2: Define behaviour        
            when(disp.estaConectado()).thenReturn(true);

            // Step 3: Execute
            assertFalse(ronq.reconectar());

            // Step 4: Verify
            verify(disp, times(1)).estaConectado();
            verify(disp, never()).conectarSensorPresion();
            verify(disp, never()).conectarSensorSonido();
        }
        
        @Test
        @DisplayName("Si se llama a reconectar desde ronQI2 y el dispositivo está desconectado pero falla la conexión de un sensor, devuelve falso")
        public void ReconnectRonQI2CuandoDispositivoNoConectado_AlgunSensorFalla_Test()
        {
            // Step 2: Define behaviour        
            when(disp.estaConectado()).thenReturn(false);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(false);

            // Step 3: Execute
            assertFalse(ronq.reconectar());

            // Step 4: Verify
            verify(disp, times(1)).estaConectado();
            verify(disp, times(1)).conectarSensorPresion();
            verify(disp, times(1)).conectarSensorSonido();
        }

        @Test
        @DisplayName("Si se llama a reconectar desde ronQI2 y el dispositivo está desconectado pero falla la conexión de los dos sensores, devuelve falso")
        public void ReconnectRonQI2CuandoDispositivoNoConectado_DosSensoresFallan_Test()
        {
            // Step 2: Define behaviour        
            when(disp.estaConectado()).thenReturn(false);
            when(disp.conectarSensorPresion()).thenReturn(false);

            // Step 3: Execute
            assertFalse(ronq.reconectar());

            // Step 4: Verify
            verify(disp, times(1)).estaConectado();
            verify(disp, times(1)).conectarSensorPresion();
            verify(disp, never()).conectarSensorSonido(); // No se ejecuta porque la primera condición del and ya se ejecuta como falso. (Lazy evaluation (?))
        }

    }
     
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
    
    @Nested
    class EvaluarApenaSueno_Tests {
        
        RonQI2Silver ronq;

        @BeforeEach
        public void setup()
        {
            // Step 1: Create the mock object
            ronq = new RonQI2Silver();
            ronq.anyadirDispositivo(disp);
        }

        @Test
        @DisplayName("Leer 5 lecturas de los sensores y que al evaluar si superan los umbrales")
        public void EvaluarApenaSueno_CondicionesNormales_Test() 
        {
            // Step 2: Define behaviour
            when(disp.leerSensorPresion()).thenReturn(21.0f);
            when(disp.leerSensorSonido()).thenReturn(31.0f);

            // Step 3: Execute
            for(int i=0; i<5; i++) 
            {
                ronq.obtenerNuevaLectura();
            }

            assertTrue(ronq.evaluarApneaSuenyo());
 
            // Step 4: Verify
            verify(disp, times(5)).leerSensorPresion();
            verify(disp, times(5)).leerSensorSonido();
        }
        
    }


    
    
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
