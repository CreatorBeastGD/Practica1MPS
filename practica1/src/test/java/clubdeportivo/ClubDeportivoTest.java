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
    public void ClubConTamañoArrayGruposNegativaEsIncorrecto()
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

    @Test
    @DisplayName("Si al construir un club deportivo se introduce como gruposLenght un cero se debe lanza una excepción")
    public void ClubConTamañoArrayGruposCeroEsIncorrecto()
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

    @Test 
    @DisplayName("Añadir un grupo nulo al club lanza una excepción")
    public void AnyadirGrupoNuloEsIncorrecto()
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

    @Test
    @DisplayName("Añadir un grupo que no estaba en la lista de grupos del club debe añadirlo al final de la lista")
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

    @Test
    @DisplayName("Añadir un grupo al club teniendo grupos ya insertados añadirá este al final de la lista")
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

    @Test
    @DisplayName("Añadir un grupo que ya existe en el club tan solo actualiza las plazas de este")
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

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos correctos en un array de Strings y que este se añada correctamente")
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

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `nplazas` no sea un número debe lanzar una expeción")
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

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `matriculados` no sea un número debe lanzar una expeción")
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

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `tarifa` no sea un número debe lanzar una expeción")
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

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `matriculados` sea mayor a `nplazas` debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConMatriculadosMayorNPlazasEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "45", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: El número de plazas es menor que el de matriculados.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `nplazas` sea negativo debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoNPlazasNegativoEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "-40", "10", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `matriculados` sea negativo debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoMatriculadosNegativoEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "-10", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `tarifa` sea negativo debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoTarifaNegativoEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "10", "-20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `nplazas` sea cero debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoNPlazasCeroEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "0", "10", "20.0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `tarifa` sea cero debe lanzar un excepción")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoTarifaCeroEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "10", "0"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero que el dato `matriculados` sea cero debe poder añadirse con éxito")
    public void AnyadirGrupoMedianteArrayDatosConArgumentoMatriculadosCeroConExitoTest() throws ClubException
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40", "0", "20.0"};
        String output, expected = "Málaga --> [ (1 - Baloncesto - 20.0 euros - P:40 - M:0) ]";

        // Act
        cd.anyadirActividad(datos);
        output = cd.toString();
        
        // Assert
        assertEquals(expected, output);
    }

    @Test
    @DisplayName("Añadir al club un grupo mediante el pase de datos en un array de Strings pero faltan datos en el array (su lenght es menor a 5)")
    public void AnyadirGrupoMedianteArrayDatosConFaltaDeArgumentosEsIncorrecto()
    {
        // Arrange
        String[] datos = {"1", "Baloncesto", "40"};
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: falta de argumentos en el array de datos";

        // Act
        Executable input = () -> cd.anyadirActividad(datos);
        
        // Assert
        assertThrows(expected, input, expectedMsg);
    }
    
    @Test
    @DisplayName("Un club sin grupos devolverá cero si se consulta las plazas libres de lo que sea")
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

    @Test
    @DisplayName("Consultar las plazas libres de un actividad que no tiene un club devuelve cero")
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

    @Test
    @DisplayName("Si se consulta una actividad que el club posee, se devolverán el número de plazas libres que tiene")
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

    @Test
    @DisplayName("Si se consulta una actividad que el club posee y tiene más de un grupo con esa actividad, se devolverán la suma de las plazas libres de todos esos grupos")
    public void ActividadConsultadaDevuelvePlazasLibresHabiendoVariosGruposConExitoTest() throws ClubException
    {
        //Arrange
        Grupo[] grupos = {null, null, null, null};
        String[] codigos = {"1", "2", "3", "4"};
        String[] actividades = {"Baloncesto", "Baloncesto", "Baloncesto", "Surf"};
        int[] nplazas = {40, 20, 30, 30};
        int[] matriculados = {10, 12, 20, 20};
        double[] tarifas = {10.0, 15.5, 12.8, 30.0};
    
        int expected = 48;
        int output;

        // Act
        for(int i=0; i<grupos.length; i++)
        {
            grupos[i] = new Grupo(codigos[i], actividades[i], nplazas[i], matriculados[i], tarifas[i]);
            cd.anyadirActividad(grupos[i]);
        }

        output = cd.plazasLibres("Baloncesto");

        // Assert
        assertEquals(expected, output);
    }

    @Test
    @DisplayName("Llamar a la función ingresos devolverá las ganancias entre todos los grupos que hay en el club")
    public void ObtenerIngresosConExitoTest() throws ClubException
    {
        //Arrange
        Grupo[] grupos = {null, null, null};
        String[] codigos = {"1", "2", "3"};
        String[] actividades = {"Baloncesto", "Pintura", "Danza"};
        int[] nplazas = {40, 20, 30};
        int[] matriculados = {10, 12, 20};
        double[] tarifas = {10.0, 15.5, 12.8};

        double expected = 542.0;
        double output;

        // Act
        for(int i=0; i<grupos.length; i++)
        {
            grupos[i] = new Grupo(codigos[i], actividades[i], nplazas[i], matriculados[i], tarifas[i]);
            cd.anyadirActividad(grupos[i]);
        }

        output = cd.ingresos();

        // Assert
        assertEquals(expected, output, 0.0001);

    }

    @Test
    @DisplayName("Llamar a la función ingresos de un club sin grupos devolverá cero")
    public void ObtenerIngresosClubSinGruposDevuelveCero()
    {
        // Arrange
        double expected = 0.0;
        double output;

        // Act 
        output = cd.ingresos();

        // Assert
        assertEquals(expected, output, 0);
    }

    @Test
    @DisplayName("Matricular más personas que las plazas disponibles en una actividad del club saltará una excepción")
    public void MatricularMasQuePlazasDisponibles() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 10;
        double tarifa = 20.0;

        int nuevosMatriculados = 45; // 10 + 45 > 40
        Class<ClubException> expected = ClubException.class;
        String expectedMsg = "ERROR: no hay suficientes plazas libres para esa actividad en el club o no tiene esa actividad registrada.";

        // Act
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);
        Executable input = () -> cd.matricular(actividad, nuevosMatriculados);

        // Assert
        assertThrows(expected, input, expectedMsg);

    }

    @Test
    @DisplayName("Matricular personas a una actividad del club con éxito y no sobrando plazas libres")
    public void MatricularJustoLasPlazasDisponiblesConExito() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 10;
        double tarifa = 20.0;

        String output, expected = "Málaga --> [ (1 - Baloncesto - 20.0 euros - P:40 - M:40) ]";

        // Act
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);
        cd.matricular("Baloncesto", 30);
        output = cd.toString();

        // Assert
        assertEquals(expected, output);
 
    }

    @Test
    @DisplayName("Matricular personas a una actividad del club con éxito y que sobren plazas libres")
    public void MatricularPlazasDisponiblesYQueSobrenConExito() throws ClubException
    {
        // Arrange
        Grupo grupo;
        String codigo = "1";
        String actividad = "Baloncesto";
        int nplazas = 40;
        int matriculados = 0;
        double tarifa = 20.0;

        String output, expected = "Málaga --> [ (1 - Baloncesto - 20.0 euros - P:40 - M:30) ]";

        // Act
        grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        cd.anyadirActividad(grupo);
        cd.matricular("Baloncesto", 30);
        output = cd.toString();

        // Assert
        assertEquals(expected, output);
 
    }

    @Test
    @DisplayName("Matricular personas a una actividad del club con éxito y que haya varios grupos de esa actividad")
    public void MatricularPlazasDisponiblesHabiendoVariosGruposMismaActividadConExito() throws ClubException
    {
        // Arrange
        Grupo[] grupos = {null, null, null};
        String[] codigos = {"1", "2", "3"};
        String[] actividades = {"Baloncesto", "Danza", "Baloncesto"};
        int[] nplazas = {40, 20, 30};
        int[] matriculados = {10, 12, 20};
        double[] tarifas = {10.0, 15.5, 12.8};

        String output, expected = "Málaga --> [ (1 - Baloncesto - 10.0 euros - P:40 - M:40), (2 - Danza - 15.5 euros - P:20 - M:12), (3 - Baloncesto - 12.8 euros - P:30 - M:25) ]";

        // Act
        for(int i=0; i<grupos.length; i++)
        {
            grupos[i] = new Grupo(codigos[i], actividades[i], nplazas[i], matriculados[i], tarifas[i]);
            cd.anyadirActividad(grupos[i]);
        }
        
        cd.matricular("Baloncesto", 35);
        output = cd.toString();

        // Assert
        assertEquals(expected, output);
 
    }


}
