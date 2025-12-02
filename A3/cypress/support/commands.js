// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
/*
Cypress.Commands.add('loginUser', (name) => {
  cy.visit('/');
  cy.get('#username').type(name);

  const password =
    name === 'alice' ? 'pass123' :
    name === 'bob' ? 'pass456' :
    'pass789';

  cy.get('#password').type(password);
  cy.get('#login-btn').click();
});*/

Cypress.Commands.add('logoutUser', () => {
  cy.get('#logout-btn').click();
});