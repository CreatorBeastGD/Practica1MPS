import { browser } from 'k6/experimental/browser';
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
        ui: 
        {
            executor: 'shared-iterations',
            options: 
            {
                browser: {type: 'chromium'},
            }
        } 
    },
    thresholds: 
    {
        checks: ["rate==1.0"]
    }
};

// Test
export default async function () 
{
    const page = browser.newPage();
    try 
    {
        // Arrange
        const expected = {};

        // Act

        // Primero se hace login
        await page.goto('http://localhost:4200');  
        page.locator('input[name="nombre"]').type('Ter');
        page.locator('input[name="DNI"]').type('3333333');
        sleep(3);

        const submitButton = page.locator('button[name="login"]');
        await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), submitButton.click()]);
        sleep(3);

        // Después añadimos el paciente
        const addButton = page.locator('button[name="add"]'); // Pulsar botón de añadir
        await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), addButton.click()]);
        sleep(3);

        page.locator('input[name="dni"]').type('5555555'); // Escribir el DNI
        page.locator('input[name="nombre"]').type('Pepa'); // Escribir el Nombre
        page.locator('input[name="edad"]').type('35'); // Escribir la edad
        page.locator('input[name="cita"]').type('Revisión importante'); // Escribir la cita
        sleep(3);

        const createButton = page.locator('button[type="submit"]') // Pulsar en crear
        await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), createButton.click()]);
        sleep(3);

        // Assert
        check(page, expected);

        sleep(3);
    } 
    finally 
    {
        page.close();
    }
  }