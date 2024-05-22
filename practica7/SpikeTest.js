import http from 'k6/http';
import { sleep, check } from 'k6';

/**
 * @author Javier Molina Colmenero
 * vus_max = 9254
 * @author Mario Cortés Herrera
 */


// const VUS_MAX = 9254 // Javi
const VUS_MAX = 9477 // Mario

// Opciones del test
export const options = 
{
    stages: 
    [
        // Duración total de 2 minutos
        // 40% de la carga máxima
        { duration: '1m', target: Math.floor(0.4 * VUS_MAX) },
        { duration: '1m', target: 0 },
    ],
    thresholds: 
    {
        // Peticiones fallidas inferiores al 0.5%, sino aborta
        http_req_failed: ['rate<0.005'],
    }
};

// Spike Test
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