import { browser } from 'k6/experimental/browser';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations', // para realizar iteraciones sin indicar el tiempo
      options: {
        browser: {
          type: 'chromium',
        }
      }
    } 
  },
  thresholds: {
    checks: ["rate==1.0"]
  }
}

export default async function () {
  const page = browser.newPage();
  try {
    await page.goto('http://localhost:4200');

    page.locator('input[name="nombre"]').type('Ter');
    page.locator('input[name="DNI"]').type('3333333');
    sleep(3);

    const submitButton = page.locator('button[name="login"]');
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), submitButton.click()]);
    sleep(3);

    const rows = page.$$("table tbody tr");
    const first_row = rows[0];
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), first_row.click()]);
    page.waitForSelector('table tbody');
    sleep(3);

    const eyeButton = page.$$('table tbody tr')[0].$('button[name="view"]');
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), eyeButton.click()])
    sleep(3);

    // Predecir
    const predictButton = page.locator('button[name="predict"]');
    await Promise.all([predictButton.click()]);
    sleep(3);

    // Pulsar en añadir informe
    const addInformeButton = page.locator('button[name="add"]');
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), addInformeButton.click()]);
    sleep(3);

    // Escribir texto de informe
    const informe = "¡Todo bien!";
    page.locator('textarea').type(informe);
    sleep(3);
    
    // Pulsar en guardar informe
    const saveInformeButton = page.locator('button[name="save"]');
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), saveInformeButton.click()]);
    sleep(3);

    const infoList = page.$$('.info-item');
    const informeItem = infoList[infoList.length - 1];
    const headerInforme = informeItem.$('.info-label');
    const contentInforme = informeItem.$('.info-value');
    
    console.log(headerInforme.textContent());
    console.log(contentInforme.textContent());

    const test = 
    {
        'CrearInformeTest': headerInforme.textContent().includes("El médico ha anotado:") && contentInforme.textContent().includes(informe)
    };

    check(page, test);

  } 
  finally {
    page.close();
  }
}