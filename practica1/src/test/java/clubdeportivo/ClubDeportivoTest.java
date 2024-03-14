package clubdeportivo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ClubDeportivoTest 
{

    ClubDeportivo cd;

    /**
     * Se inicializa el club deportivo que se usará en la mayoría de test
     */
    @BeforeEach
    public void setup() throws ClubException
    {
        // Arrange
        String nombre = "Málaga";
        cd = new ClubDeportivo(nombre);
    }

    /**
     * Si al construir un club deportivo se introduce como gruposLenght un
     * número negativo se debe lanza una excepción
     */
    @Test
    public void GruposNegativeLenghtThrowsException()
    {
        // Arrange
        String nombre = "Málaga";
        int gruposLenght = -3;
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: el club no puede crearse con un número de grupos 0 o negativo";

        // Act
        Executable input = () -> new ClubDeportivo(nombre, gruposLenght);

        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    /**
     * Si al construir un club deportivo se introduce como gruposLenght un
     * cero se debe lanza una excepción
     */
    @Test
    public void GruposZeroLenghtThrowsException()
    {
        // Arrange
        String nombre = "Málaga";
        int gruposLenght = 0;
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: el club no puede crearse con un número de grupos 0 o negativo";

        // Act
        Executable input = () -> new ClubDeportivo(nombre, gruposLenght);

        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    /**
     * Añadir un grupo nulo al club lanza una excepción
     */
    @Test 
    public void AnyadirGrupoNullThrowsException()
    {
        // Arrange
        Grupo grupo = null;
        Class<ClubException> expected1 = ClubException.class;
        String expectedMsg = "ERROR: el grupo es nulo";
        String expected2 = "Málaga --> [  ]";
        String output;

        // Act
        Executable input = () -> cd.anyadirActividad(grupo);
        output = cd.toString();

        // Assert
        assertThrows(expected1, input, expectedMsg); 
        assertEquals(expected2, output);
    }

    /**
     * Añadir un grupo que no estaba en la lista de grupos
     * del club debe añadirlo al final de la lista
     */
    @Test
    public void AnyadirGrupoNuevoConExitoTest() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 10;
        double tarifa = 20.0;
        String output;
        String expected;

        // Act
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        expected = "Málaga --> [ " + grupo.toString() + " ]";
        cd.anyadirActividad(grupo);
        output = cd.toString();

        // Assert
        assertEquals(expected, output);
    }

    /**
     * Añadir un grupo que ya existe en el club
     * tan solo actualiza las plazas de este
     */
    @Test
    public void AnyadirGrupoExistenteActualizaPlazas() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int nplazas_post = 50;
        int matriculados = 10;
        double tarifa = 20.0;
        String output;
        String expected;

        // Act
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);

        grupo.actualizarPlazas(nplazas_post);
        cd.anyadirActividad(grupo);

        expected = "Málaga --> [ " + grupo.toString() + " ]";
        output = cd.toString();

        // Assert
        assertEquals(expected, output);
    }


  

}
