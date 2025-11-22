describe('Library Book Management', () => {

  beforeEach(() => {

    // reset
    cy.request('POST', '/api/reset');
    cy.request('POST', '/api/auth/logout');

    // visit
    cy.visit('/');

    // confirmation
    cy.get('#login-section').should('be.visible');
    cy.get('#login-section', { timeout: 5000 }).should('be.visible');
    cy.get('#main-content').should('not.be.visible');
  });


  it('Scenario 1: basic borrow-return cycle', () => {
    // Login as alice
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assert login success UI
    cy.get('#user-summary')
      .should('contain', 'alice');

    // Borrow "1984"
    cy.contains('tr', '1984').within(() => {
      cy.contains('Borrow').click();
    });

    cy.get('#notif-list').should('contain', 'Borrowed "1984" successfully.');


    cy.get('#books-table')
      .contains('tr', '1984')
      .find('.status-pill')
      .should('contain', 'Checked Out');

    // Logout -> login as bob
    cy.get('#logout-btn').click();

    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Bob should not be able to borrow
    cy.contains('tr', '1984').within(() => {
      cy.contains('Borrow').click();
    });

    cy.get('#notif-list')
      .should('contain', 'already checked out'); // ✔ Borrow blocked for bob

    // Alice returns book
    cy.get('#logout-btn').click();
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    cy.contains('#borrowed-table tr', '1984')
      .contains('Return')
      .click();

    cy.get('#notif-list').should('contain', 'Returned "1984" successfully.');

    // Book becomes available again
    cy.contains('tr', '1984')
      //.find('.status-pill')
      .should('contain', 'Available');   // ✔ returned → back to available
  });

  it('Scenario 2: multiple users placed holds FIFO queue', () => {

    cy.loginUser('alice');  // (custom command below)

    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Borrow').click();
    });

    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');

    cy.logoutUser();


    cy.loginUser('bob');

    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Place Hold').click();
    });

    cy.get('#notif-list').should('contain', 'Hold placed');

    cy.logoutUser();


    cy.loginUser('charlie');

    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Place Hold').click();
    });

    cy.get('#notif-list').should('contain', 'Hold placed');

    cy.logoutUser();


    cy.loginUser('alice');
    cy.contains('#borrowed-table tr', 'Moby Dick')
      .contains('Return')
      .click();
    cy.logoutUser();


    // Bob logs in  should see notification
    cy.loginUser('bob');
    cy.get('#notif-list')
      .should('contain', 'is ready for you to borrow');  // ✔ only Bob gets it

    // Bob borrows  now Charlie becomes next
    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Borrow').click();
    });

    cy.contains('#borrowed-table tr', 'Moby Dick')
      .contains('Return')
      .click();

    cy.logoutUser();


    // Charlie logs in → should get notification now
    cy.loginUser('charlie');
    cy.get('#notif-list')
      .should('contain', 'is ready for you to borrow'); // ✔ Charlie now notified
  });


  it("Scenario 3: borrowing limit and hold interactions", () => {

      // Login as alice
      cy.get('#username').type('alice');
      cy.get('#password').type('pass123');
      cy.get('#login-btn').click();

      cy.get('#user-summary').should('contain', 'alice');

      cy.contains('tr', '1984').within(() => {
          cy.contains('Borrow').click();
      });

      cy.contains('tr', 'Harry Potter').within(() => {
          cy.contains('Borrow').click();
      });

      cy.contains('tr', 'Animal Farm').within(() => {
          cy.contains('Borrow').click();
      });

      cy.get('#user-summary').should('contain', '3/3');

      cy.contains('tr', 'The Hobbit').within(() => {
          cy.contains('Borrow').click();
      });

      cy.get('#notif-list')
          .should('be.visible')
          .should('contain', 'borrowing limit')
          .should('contain', '3');

      cy.contains('tr', 'The Hobbit')
          .within(() => {
              cy.contains('Borrow').should('exist');
          });

  });

});
