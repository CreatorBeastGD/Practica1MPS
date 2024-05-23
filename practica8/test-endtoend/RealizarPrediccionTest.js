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

    const last_row = page.$$("table tbody tr")[0]; // Primera fila de la tabla
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), last_row.click()]);
    page.waitForSelector('table tbody')
    sleep(1);

    const eyeButton = page.$$('table tbody tr')[0].$('button[name="view"]');
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), eyeButton.click()])
    sleep(1);

    const predictButton = page.locator('button[name="predict"]');
    await Promise.all([predictButton.click()])
    sleep(10);

    console.log(page.locator('span[name="predict"]').textContent());


    check(page, {
      'prediction': p => p.locator('span[name="predict"]').textContent().includes('Probabilidad de cáncer:')
    });
  } finally {
    page.close();
  }
}