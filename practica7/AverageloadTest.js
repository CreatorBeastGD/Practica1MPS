import http from 'k6/http';
import { sleep, check } from 'k6';

/**
 * @author Javier Molina Colmenero
 * @author Mario Cortés Herrera
 */

// Opciones del test
export const options = 
{
    tages: 
    [
        // Como Stress Test pero la carga promedio es del 50% de VUs del 
        /*
        { duration: '', target:  },
        { duration: '', target:  },
        { duration: '', target: 0 }
        */
    ],
    thresholds: 
    {
        http_req_failed: 
        [
            {
                // Peticiones fallidas menor a 1%, sino aborta
                threshold: "rate<=0.01",
                abortOnFail: true,
            }
        ]
    }
};

// Average Load Test
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