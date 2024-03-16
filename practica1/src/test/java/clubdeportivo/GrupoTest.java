package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class GrupoTest {
    Grupo grupo;
    
    @Test
    @DisplayName("Crear un grupo con valores correctos es correcto")
    public void CreatingGrupoWithCorrectValuesIsCorrect() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int matriculados = 5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        //Assert
        assertDoesNotThrow(exec);
    }

    @Test
    @DisplayName("Crear un grupo sin codigo causa excepcion")
    public void CreatingGrupoWithNoCodeThrowsException() {
        //Arrange
        String codigo = "";
        String actividad = "Actividad";
        int nplazas = 10;
        int matriculados = 5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: Los campos de actividad y codigo no pueden ser nulos.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo sin actividad causa excepcion")
    public void CreatingGrupoWithNoActivityThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "";
        int nplazas = 10;
        int matriculados = 5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: Los campos de actividad y codigo no pueden ser nulos.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo con plazas negativas causa excepcion")
    public void CreatingGrupoWithNegativeSeatsThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = -1;
        int matriculados = 5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo con plazas negativas causa excepcion")
    public void CreatingGrupoWithNoSeatsThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 0;
        int matriculados = 5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo con plazas negativas causa excepcion")
    public void CreatingGrupoWithNegativeRegistersThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int matriculados = -5;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo con plazas negativas causa excepcion")
    public void CreatingGrupoWithNegativeFeeThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int matriculados = 5;
        double tarifa = -0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: los datos numéricos no pueden ser menores o iguales que 0.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Crear un grupo con plazas negativas causa excepcion")
    public void CreatingGrupoWithMoreRegistersThanSeatsThrowsException() {
        //Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int matriculados = 15;
        double tarifa = 0.99;

        //Act
        Executable exec = () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: El número de plazas es menor que el de matriculados.";

        //Assert
        assertThrows(excep, exec, message);
    }

    @BeforeEach
    public void setup() throws ClubException {
        grupo = new Grupo("Prueba", "Actividad", 10, 5, 0.99);
    }

    @Test
    @DisplayName("Al ver la tarifa devuelve la tarifa")
    public void GetFeeReturnsFee() {
        // Arrange 

        // Act
        double result = grupo.getTarifa();
        double expected = 0.99; 

        // Assert
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Al ver el codigo devuelve el codigo")
    public void GetCodeReturnsCode() {
        // Arrange 

        // Act
        String result = grupo.getCodigo();
        String expected = "Prueba"; 

        // Assert
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Al ver la actividad devuelve la actividad")
    public void GetActivityReturnsActivity() {
        // Arrange 

        // Act
        String result = grupo.getActividad();
        String expected = "Actividad"; 

        // Assert
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Actualizar plazas con valor valido debe ser correcto")
    public void UpdateSeatsWithCorrectValueShouldBeCorrect() throws ClubException {
        // Arrange
        int n = 7;

        // Act
        grupo.actualizarPlazas(n);
        int expected = n;
        int result = grupo.getPlazas();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Actualizar plazas con valor nulo lanza excepción")
    public void UpdateSeatsWithNoSeatsThrowsException() {
        // Arrange
        int n = 0;

        // Act
        Executable exec = () -> grupo.actualizarPlazas(n);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: número de plazas negativo.";
    
        // Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Actualizar plazas con valor menor a matriculados lanza excepción")
    public void UpdateSeatsWithSeatsLessThanRegisteredThrowsException() {
        // Arrange
        int n = 1;

        // Act
        Executable exec = () -> grupo.actualizarPlazas(n);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: número de plazas negativo.";
    
        // Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Matricular con valor valido debe ser correcto")
    public void RegisterWithCorrectValueShouldBeCorrect() throws ClubException {
        // Arrange
        int n = 3;

        // Act
        grupo.matricular(n);
        int expected = 8;
        int result = grupo.getMatriculados();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Matricular con valor nulo lanza excepcion")
    public void RegisterWithNoValueThrowsException() throws ClubException {
        // Arrange
        int n = 0;

        // Act
        Executable exec = () -> grupo.matricular(n);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: El numero de matrículas nuevas no puede ser negativo o nulo.";
    
        // Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Matricular con valor negativo lanza excepcion")
    public void RegisterWithNegativeValueThrowsException() throws ClubException {
        // Arrange
        int n = -1;

        // Act
        Executable exec = () -> grupo.matricular(n);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: El numero de matrículas nuevas no puede ser negativo o nulo.";
    
        // Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Matricular con un valor no valido lanza excepcion")
    public void RegisterWithWrongValueThrowsException() throws ClubException {
        // Arrange
        int n = 10;

        // Act
        Executable exec = () -> grupo.matricular(n);
        Class<ClubException> excep = ClubException.class;
        String message = "ERROR: no hay plazas libres suficientes, plazas libre: "+ grupo.plazasLibres()+ " y matriculas: "+n;
        // Assert
        assertThrows(excep, exec, message);
    }

    @Test
    @DisplayName("Hacer equals sobre dos grupos con mismos datos deberia devolver true")
    public void EqualsTwoGrupoWithSameDataIsCorrect() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g2 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);

        // Assert
        assertTrue(g1.equals(g2));
    }

    @Test
    @DisplayName("Hacer equals sobre dos grupos con diferentes datos deberia devolver falso")
    public void EqualsTwoGrupoWithDifferentCodeIsFalse() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String codigo2 = "Prueba hapter";
        String actividad = "Actividad";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g2 = new Grupo(codigo2, actividad, nplazas, nmatriculados, tarifa);

        // Assert
        assertTrue(!g1.equals(g2));
    }

    @Test
    @DisplayName("Hacer equals sobre dos grupos con diferentes datos deberia devolver falso")
    public void EqualsTwoGrupoWithDifferentDataIsFalse() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String codigo2 = "Prueba hapter";
        String actividad = "Actividad";
        
        String actividad2 = "Actividad hapter";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g2 = new Grupo(codigo2, actividad2, nplazas, nmatriculados, tarifa);

        // Assert
        assertTrue(!g1.equals(g2));
    }

    @Test
    @DisplayName("Hacer equals sobre dos grupos con diferentes actividades deberia devolver falso")
    public void EqualsTwoGrupoWithDifferentActivityIsFalse() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        String actividad2 = "Actividad hapter";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g2 = new Grupo(codigo, actividad2, nplazas, nmatriculados, tarifa);

        // Assert
        assertTrue(!g1.equals(g2));
        assertTrue(!(g1.hashCode()==g2.hashCode()));
    }

    @Test
    @DisplayName("Hacer equals sobre dos grupos con mismos datos pero con diferentes caps deberia ser correcto")
    public void EqualsTwoGrupoWithSameDataIsTrueIgnoringCase() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String codigo2 = "PRUEBA";
        String actividad = "Actividad";
        String actividad2 = "ACTIVIDAD";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g2 = new Grupo(codigo2, actividad2, nplazas, nmatriculados, tarifa);

        Grupo g3 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g4 = new Grupo(codigo, actividad2, nplazas, nmatriculados, tarifa);

        Grupo g5 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g6 = new Grupo(codigo2, actividad, nplazas, nmatriculados, tarifa);

        Grupo g7 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        Grupo g8 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);

        // Assert
        assertTrue(g1.equals(g2));
        assertTrue(g3.equals(g4));
        assertTrue(g5.equals(g6));
        assertTrue(g7.equals(g8));
    }

    @Test
    @DisplayName("Hacer equals sobre distintos objetos deberia devolver falso")
    public void EqualsTwoGrupoWithDifferentObjectReturnFalse() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String codigo2 = "PRUEBA";
        String actividad = "Actividad";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        ClubDeportivo g2 = new ClubDeportivo(codigo2); 

        // Assert
        assertTrue(!g1.equals(g2));
    }

    @Test
    @DisplayName("ToString devuelve los datos del objeto en string")
    public void ToStringReturnsStringOfGrupo() throws ClubException {
        // Arrange
        String codigo = "Prueba";
        String actividad = "Actividad";
        int nplazas = 10;
        int nmatriculados = 5;
        double tarifa = 0.99;

        // Act
        Grupo g1 = new Grupo(codigo, actividad, nplazas, nmatriculados, tarifa);
        String expected = "(Prueba - Actividad - 0.99 euros - P:10 - M:5)";
        String result = g1.toString();

        // Assert
        assertEquals(expected, result);;
    }
}