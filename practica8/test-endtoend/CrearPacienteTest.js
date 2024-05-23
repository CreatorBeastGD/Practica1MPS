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
        // Primero se hace login
        await page.goto('http://localhost:4200');  
        sleep(3);

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

        const datos = ['5555555', 'Pepa', '35', 'Revisión importante'];
        page.locator('input[name="dni"]').type(datos[0]); // Escribir el DNI
        page.locator('input[name="nombre"]').type(datos[1]); // Escribir el Nombre
        page.locator('input[name="edad"]').type(datos[2]); // Escribir la edad
        page.locator('input[name="cita"]').type(datos[3]); // Escribir la cita
        sleep(3);

        const createButton = page.locator('button[type="submit"]') // Pulsar en crear
        await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), createButton.click()]);
        sleep(3);

        // Acceder al último paciente de la tabla
        const table_len = page.$$("table tbody tr").length; // Longitud de la tabla
        const last_row = page.$$("table tbody tr")[table_len - 1]; // Última fila de la tabla
        await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), last_row.click()]);
        sleep(3);

        const info_paciente = page.$$(".detalle-paciente fieldset div");
        const dni = info_paciente[0].$('span').textContent;
        const nombre = info_paciente[1].$('span').textContent;
        const edad = info_paciente[2].$('span').textContent;
        const cita = info_paciente[3].$('span').textContent;

        console.log(dni, nombre, edad, cita)

        const test = 
        {
            'CrearPacienteTest': dni == datos[0] && nombre == datos[1] && edad == datos[2] && cita == datos[3]
        }
        check(page, test);
        sleep(3);
        
    } 
    finally 
    {
        page.close();
    }
  }