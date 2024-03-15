package clubdeportivo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ClubDeportivoTest 
{

    ClubDeportivo cd;

    @BeforeEach
    @DisplayName("Se inicializa el club deportivo que se usará en la mayoría de test")
    public void setup() throws ClubException
    {
        // Arrange
        String nombre = "Málaga";
        cd = new ClubDeportivo(nombre);
    }

    @Test
    @DisplayName("Si al construir un club deportivo se introduce como gruposLenght un número negativo se debe lanza una excepción")
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
     * Añadir un grupo al club teniendo grupos 
     * ya insertados añadirá este al final de la lista
     */
    @Test
    public void AnyadirVariosGruposNuevosConExitoTest() throws ClubException
    {
        // Arrange
        Grupo grupo1;
        String codigo1 = "1";
        String actividad1 = "Baloncesto";
        int nplazas1 = 40;
        int matriculados1 = 10;
        double tarifa1 = 20.0;

        Grupo grupo2;
        String codigo2 = "2";
        String actividad2 = "Béisbol";
        int nplazas2 = 50;
        int matriculados2 = 30;
        double tarifa2 = 30.0;

        String output;
        String expected;

        // Act
        grupo1 = new Grupo(codigo1, actividad1, nplazas1, matriculados1, tarifa1);
        grupo2 = new Grupo(codigo2, actividad2, nplazas2, matriculados2, tarifa2);

        cd.anyadirActividad(grupo1);
        cd.anyadirActividad(grupo2);

        expected = "Málaga --> [ " + grupo1.toString() + ", " + grupo2.toString() + " ]";
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

    /**
    * Añadir al club un grupo mediante el pase de datos
    * correctos en un array de Strings y que este se añada correctamente
    */
    @Test
    public void AnyadirGrupoMedianteArrayDatosConExitoTest() throws ClubException
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "10", "20.0"};
        String output;
        String expected = "Málaga --> [ (1 - Baloncesto - 20.0 euros - P:40 - M:10) ]";

        // Act
        cd.anyadirActividad(datos);
        output = cd.toString();
        
        // Assert
        assertEquals(expected, output);

    }

    /**
     * Añadir al club un grupo mediante el pase de datos
     * en un array de Strings pero que el dato nplazas no sea 
     * un número debe lanzar una expeción
     */
    @Test
    public void AnyadirGrupoMedianteArrayDatosConIncorrectoArgumentoNPlazas()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "hola", "10", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: formato de número incorrecto";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    /**
     * Añadir al club un grupo mediante el pase de datos
     * en un array de Strings pero que el dato matriculados no sea 
     * un número debe lanzar una expeción
     */
    @Test
    public void AnyadirGrupoMedianteArrayDatosConIncorrectoArgumentoMatriculados()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "hola", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: formato de número incorrecto";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    /**
     * Añadir al club un grupo mediante el pase de datos
     * en un array de Strings pero que el dato nplazas no sea 
     * un número debe lanzar una expeción
     */
    @Test
    public void AnyadirGrupoMedianteArrayDatosConIncorrectoArgumentoTarifa()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "10", "hola"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: formato de número incorrecto";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    /**
     * Un club sin grupos devolverá cero si 
     * se consulta las plazas libres de lo que sea
     */
    @Test
    public void ClubSinGruposImplicaCeroPlazasLibres()
    {
        // Arrange
        int expected = 0;
        int output;

        // Act 
        output = cd.plazasLibres("Baloncesto");

        // Assert
        assertEquals(expected, output);

    }

    /**
     * Consultar las plazas libres de un actividad
     * que no tiene un club devuelve cero
     */
    @Test
    public void ActividadConsultadaQueNoTieneElClubImplicaCeroPlazasLibres() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 10;
        double tarifa = 20.0;
        int expected = 0;
        int output;

        // Act 
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);
        output = cd.plazasLibres("Surf");

        // Assert
        assertEquals(expected, output);
    }

    /**
     * Si se consulta una ctividad que el club posee,
     * se devolverán el número de plazas libres que tiene
     */
    @Test
    public void ActividadConsultadaDevuelvePlazasLibresConExitoTest() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 10;
        double tarifa = 20.0;

        int expected = nplazas - matriculados;
        int output;

        // Act 
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);
        output = cd.plazasLibres("Baloncesto");

        // Assert
        assertEquals(expected, output);
    }

    /**
     * 
     */
    @Test
    public void ObtenerIngresosConExitoTest()
    {

    }

}
