package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ClubDeportivoAltoRendimientoTest {
    ClubDeportivoAltoRendimiento cdar;

    @BeforeEach
    public void setup() throws ClubException {
        cdar = new ClubDeportivoAltoRendimiento("Prueba", 15 , 3.99);
    }

    @Test
    @DisplayName("Al crear un CDAR con el tamaño se realiza correctamente")
    public void WhenCreatingObjectWithTamAndCorrectValuesThenReturnObject() {
        // Arrange
        String nombre = "Prueba";
        int tam = 100;
        int maximo = 15;
        double incremento = 10.0;

        // Act
        Executable exec = () -> new ClubDeportivoAltoRendimiento(nombre, tam, maximo, incremento);

        // Assert 
        assertDoesNotThrow(exec);
    }

    @Test
    @DisplayName("Al crear un CDAR con maximo negativo devuelve excepcion")
    public void WhenCreatingObjectWithNegativeMaxThrowException() {
        // Arrange
        String name = "Prueba";
        int max = -1;
        double incremento = 0.99;
        int tam = 10;

        // Act
        Class<ClubException> exc = ClubException.class;
        String message = "ERRORES: valores 0 o negativos.";
        Executable exe = () -> new ClubDeportivoAltoRendimiento(name, max, incremento);
        Executable exe2 = () -> new ClubDeportivoAltoRendimiento(name, tam, max, incremento);

        // Assert
        assertThrows(exc, exe, message);
        assertThrows(exc, exe2, message);
    }

    @Test
    @DisplayName("Al crear un CDAR con un incremento negativo devuelve excepcion")
    public void WhenCreatingObjectWithNegativeIncrementThrowException() {
        // Arrange
        String name = "Prueba";
        int max = 5;
        double incremento = -33.99;
        int tam = 10;

        // Act
        Class<ClubException> exc = ClubException.class;
        String message = "ERRORES: valores 0 o negativos.";
        Executable exe = () -> new ClubDeportivoAltoRendimiento(name, max, incremento);
        Executable exe2 = () -> new ClubDeportivoAltoRendimiento(name, tam, max, incremento);

        // Assert
        assertThrows(exc, exe, message);
        assertThrows(exc, exe2, message);
    }

    @Test
    @DisplayName("Añadir una actividad con valores correctos añade la actividad")
    public void WhenAnyadirActividadWithCorrectValuesThenAddActivity() throws ClubException {
        // Arrange
        String[] datos = {"GrupoTest", "Actividad", "5", "2", "1.99"};
        Grupo group = new Grupo("GrupoTest", "Actividad", 5, 2, 1.99);

        // Act
        cdar.anyadirActividad(datos);
        int result = cdar.plazasLibres("Actividad");
        int expected = group.plazasLibres();
        // La funcion buscar de clubdeportivo es privado, por lo cual comprobaremos en la variable result si hay plazas
        // libres de la actividad dentro del club, y si devuelve valor entonces se ha añadido correctamente.

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Añadir una actividad con menos de 5 datos devuelve excepción")
    public void anyadirActividadWithNotEnoughDataThrowsException() {
        // Arrange
        String[] datos = {};

        // Act
        Class<ClubException> exc = ClubException.class;
        String message = "ERROR: faltan datos";
        Executable exe = () -> cdar.anyadirActividad(datos);

        // Assert
        assertThrows(exc, exe, message);
    }

    @Test
    @DisplayName("Al añadir datos de un tipo erróneo se lanza excepción")
    public void AnyadirActividadWithWrongDataTypeThrowsException() {
        // Arrange
        String[] datos = {"GrupoTest", "Actividad", "Hapter", "Hapter", "Hap.ter"};

        // Act
        Class<ClubException> exc = ClubException.class;
        String message = "ERROR: formato de número incorrecto";
        Executable exe = () -> cdar.anyadirActividad(datos);

        // Assert
        assertThrows(exc, exe, message);
    } 

    @Test
    @DisplayName("Añadir una actividad con valores correctos pero mayor numero de plazas añade la actividad y fija el maximo del grupo")
    public void WhenAnyadirActividadWithCorrectValuesButhHigherSeatsThenAddActivityWithFixedSeats() throws ClubException {
        // Arrange
        String[] datos = {"GrupoTest", "Actividad", "33", "2", "1.99"};
        // Las plazas libres de un grupo con estos datos debería ser 33 - 2 = 31.

        // Act
        cdar.anyadirActividad(datos);
        int result = cdar.plazasLibres("Actividad");
        int expected = 13;
        // La funcion buscar de clubdeportivo es privado, por lo cual comprobaremos en la variable result si hay plazas
        // libres de la actividad dentro del club, y si devuelve valor entonces se ha añadido correctamente.

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Obtener los ingresos deberia recibir estos correctamente")
    public void GettingIngresosShouldReturnCorrectValues() throws ClubException {
        // Arrange
        // Tendremos que crear grupos para poder calcularlo
        String[] datos1 = {"GrupoTest1", "Actividad1", "10", "4", "1.00"};
        String[] datos2 = {"GrupoTest2", "Actividad2", "5", "2", "8.00"};
        String[] datos3 = {"GrupoTest3", "Actividad3", "5", "3", "5.00"};

        // Act
        cdar.anyadirActividad(datos1);
        cdar.anyadirActividad(datos2);
        cdar.anyadirActividad(datos3);
        double expected = 36.39;
        double result = cdar.ingresos();

        // Assert
        assertEquals(expected, result, 0.01);
    }
}
