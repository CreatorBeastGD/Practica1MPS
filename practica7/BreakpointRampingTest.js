import http from 'k6/http';
import { sleep, check } from 'k6';

/**
 * @author Javier Molina Colmenero
 * @author Mario Cortés Herrera
 */

// Opciones del test
export const options = 
{
    scenarios: 
    {
        breakpoint: 
        {
            executor: "ramping-arrival-rate", // Prueba con executor
            maxVUs: 1e6,                      // Muchos usuarios
            stages: 
            [{ 
                duration: "10m",            // Duración de 10 minutos
                target: 100000              // Al menos 100000 VUs
            }],
            preAllocatedVUs: 1000           
        }
    },
    thresholds: 
    {
        // Peticiones fallidas mayores a 1%, sino aborta
        http_req_failed: [{ threshold: "rate<=0.01", abortOnFail: true }]
    }
};

// Breakpoint Test
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