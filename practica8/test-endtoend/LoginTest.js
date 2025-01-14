import { browser } from 'k6/experimental/browser';
import { check, sleep } from 'k6';

/**
 * @author Mario Cortés Herrera
 * @author Javier Molina Colmenero
 */

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations', // para realizar iteraciones sin indicar el tiempo
      options: {
        browser: {
          type: 'chromium',
        },
      },
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

    const submitButton = page.locator('button[name="login"]');

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    check(page, {
      'header' : p => p.locator('h2').textContent() == 'Listado de pacientes',
    });
  } finally {
    page.close();
  }
}