import http from 'k6/http';
import { sleep, check } from 'k6';

/**
 * @author Javier Molina Colmenero
 * vus_max = 9254
 * @author Mario Cortés Herrera
 */

const VUS_MAX = 9254

// Opciones del test
export const options = 
{
    
    stages: [
        {duration: '3m', target: Math.floor(0.8 * VUS_MAX)},
        {duration: '3m', target: Math.floor(0.8 * VUS_MAX)},
        {duration: '2m', target: 0}
    ],
    thresholds: 
    {
        // Peticiones fallidas mayores a 1%, sino aborta
        http_req_failed: [{ threshold: "rate<=0.01", abortOnFail: true }],
        // El promedio de la duracion de las peticiones debe ser de 1000ms
        http_req_duration: [{threshold: "avg<1000", abortOnFail: true}] 
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