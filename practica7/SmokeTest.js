import http from 'k6/http';
import { sleep, check } from 'k6';

/**
 * @author Javier Molina Colmenero
 * @author Mario Cortés Herrera
 */

// Opciones del test
export const options = 
{
    vus: 5,                 // Cinco usuarios
    duration: '1m',         // Duración de 1 minuto
    thresholds: 
    {
        http_req_duration: ['avg<100'], // Promedio de duración de peticiones < 100ms
        http_req_failed: [{ threshold: 'rate==0', abortOnFail: true }] // Abortar si falla
    }
};

// Smoke Test
export default () => 
{
    // Arrange
    const expected = { 'Response code was 200': (res) => res.status == 200 };
    
    // Act
    const response = http.get('http://localhost:8080/medico/1');

    // Assert
    check(response, expected);

    sleep(1);
};