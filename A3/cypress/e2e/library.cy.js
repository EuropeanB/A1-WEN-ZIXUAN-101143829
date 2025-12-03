describe('Library Book Management', () => {

  beforeEach(() => {

    // reset
    cy.request('POST', '/api/reset');
    //cy.request('POST', '/api/auth/logout');

    // visit the website
    cy.visit('/');

    // confirmation before scenario start
    cy.get('#login-section').should('be.visible');
    cy.get('#login-section', { timeout: 5000 }).should('be.visible');
    cy.get('#main-content').should('not.be.visible');
  });


  it('Scenario 1: basic borrow-return cycle', () => {
    //Test scenario1

    // Login as alice
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'alice');

    // Alice Borrow "1984"
    cy.contains('tr', '1984').within(() => {
      cy.contains('Borrow').click();
    });

    // Assertion: Check the book actually checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "1984" successfully.');

    // Assertion: Check the book actually becomes unavailable
    //            Book availability correctly asserted after borrow
    cy.get('#books-table')
      .contains('tr', '1984')
      .find('.status-pill')
      .should('contain', 'Checked Out');

    // Alice logout
    cy.get('#logout-btn').click();

    // Bob logs in
    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'bob');

    // Bob should not be able to borrow
    cy.contains('tr', '1984').within(() => {
      cy.contains('Borrow').click();
    });

    // Assertion: Check bob actually cannot borrow this book because it checked out by someone
    //            Inability to borrow unavailable book is asserted
    cy.get('#notif-list').should('contain', 'already checked out');

    // Bob logs out and Alice logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'alice');

    // Alice Return a book
    cy.contains('#borrowed-table tr', '1984')
      .contains('Return')
      .click();

    // Assertion: Check this book actually returned by someone
    cy.get('#notif-list').should('contain', 'Returned "1984" successfully.');

    // Alice logs out and Bob logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'bob');

    // Assertion: Check the book becomes available again
    //            Book availability correctly asserted after return
    cy.contains('tr', '1984').should('contain', 'Available');
  });

  it('Scenario 2: multiple users placed holds FIFO queue', () => {
    // Test scenario2

    // Alice logs in
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'alice');

    // Alice borrows a book
    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');

    // Alice logs out and Bob logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'bob');

    // Bob try to borrows a book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check bob actually cannot borrow this book because it checked out by someone
    cy.get('#notif-list').should('contain', 'already checked out');

    // Bob try to place hold on this book
    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Place Hold').click();
    });

    // Assertion: Check Bob should place hold on this book successfully
    cy.get('#notif-list').should('contain', 'Hold placed');

    // Bob logs out and Charlie logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('charlie');
    cy.get('#password').type('pass789');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'charlie');

    // Charlie try to borrows a book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check Charlie actually cannot borrow this book because it checked out by someone
    cy.get('#notif-list').should('contain', 'already checked out');

    // Charlie try to place hold on this book
    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Place Hold').click();
    });

    // // Assertion: Check Charlie should place hold on this book successfully
    cy.get('#notif-list').should('contain', 'Hold placed');

    // Charlie logs out and Alice logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'alice');

    // Alice returns a book
    cy.contains('#borrowed-table tr', 'Moby Dick')
      .contains('Return')
      .click();

    // Assertion: Check this book actually returned by someone
    cy.get('#notif-list').should('contain', 'Returned "Moby Dick" successfully.');

    // Alice logs out and Charlie logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('charlie');
    cy.get('#password').type('pass789');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'charlie');

    // Assertion: Check Charlie should haven't any notification
    //            because she is not the first user in FIFO queue
    //            Hold queue FIFO ordering correctly asserted
    cy.get('#notif-list').should('contain', 'No notifications');

    // Charlie try to borrows this book
    cy.contains('tr', 'Moby Dick').within(() => {
      cy.contains('Borrow').click();
    });

    // Assertion:Charlie failed on borrows this book
    //           because she is not the first user in FIFO queue
    //           Hold queue FIFO ordering correctly asserted
    //           Only notified user can borrow reserved book is asserted
    cy.get('#notif-list').should('contain', 'You are not first in line for "Moby Dick"');

    // Charlie logs out and Bob logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'bob');

    // Assertion: Check Bob should get the notification because he is the first in line for this book
    //            Notifications to correct user is asserted
    cy.get('#notif-list').should('contain', 'is ready for you to borrow');

    // Bob try to borrows this book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    //            Only notified user can borrow reserved book is asserted
    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');

    cy.contains('#borrowed-table tr', 'Moby Dick')
          .contains('Return')
          .click();

    // Assertion: Check this book actually returned by someone
    cy.get('#notif-list').should('contain', 'Returned "Moby Dick" successfully.');

    // Bob logs out and Charlie logs in
    cy.get('#logout-btn').click();
    //cy.logoutUser();
    cy.get('#username').type('charlie');
    cy.get('#password').type('pass789');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'charlie');

    // Assertion: Check Charlie should get the notification because he is the first in line for this book
    //            Notifications to correct user is asserted
    cy.get('#notif-list').should('contain', 'is ready for you to borrow');

    // Charlie try to borrows this book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    //            Only notified user can borrow reserved book is asserted
    //            Queue advances properly whens borrowed/returned is asserted
    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');

    cy.contains('#borrowed-table tr', 'Moby Dick')
        .contains('Return')
        .click();

    // Assertion: Check this book actually returned by someone
        cy.get('#notif-list').should('contain', 'Returned "Moby Dick" successfully.');

  });

  it("Scenario 3: borrowing limit and hold interactions", () => {
    // Test scenario3

    // Login as alice
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check the account should be correct user
    cy.get('#user-summary').should('contain', 'alice');

    cy.contains('tr', 'Animal Farm').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "Animal Farm" successfully.');

    // Assertion: Check Alice borrowed one book
    cy.get('#user-summary').should('contain', '1/3');

    cy.contains('tr', 'Harry Potter').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "Harry Potter" successfully.');

    // Assertion: Check Alice borrowed two books
    cy.get('#user-summary').should('contain', '2/3');

    cy.contains('tr', 'Jane Eyre').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "Jane Eyre" successfully.');

    // Assertion: Check Alice borrowed three books
    //            3 book borrowing limit is enforced and asserted
    cy.get('#user-summary').should('contain', '3/3');

    // Alice try to borrow fourth book
    cy.contains('tr', 'The Hobbit').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Alice failed on borrowing because she reached the borrow limit
    //            3 book borrowing limit is enforced and asserted
    cy.get('#notif-list')
        .should('be.visible')
        .should('contain', 'borrowing limit')
        .should('contain', '3');

    // Alice try to place hold on this book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Place Hold').click();
    });

    // Assertion: Check Alice should place hold on this book successfully
    cy.get('#notif-list').should('contain', 'Hold placed');

    // Alice logs out and Bob logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('bob');
    cy.get('#password').type('pass456');
    cy.get('#login-btn').click();

    // Bob try to borrows this book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');

    cy.contains('#borrowed-table tr', 'Moby Dick')
        .contains('Return')
        .click();

    // Assertion: Check this book actually returned by someone
    cy.get('#notif-list').should('contain', 'Returned "Moby Dick" successfully.');

    // Bob logs out and Alice logs in
    cy.get('#logout-btn').click();
    cy.get('#username').type('alice');
    cy.get('#password').type('pass123');
    cy.get('#login-btn').click();

    // Assertion: Check Charlie should get the notification because he is the first in line for this book
    // Notification received when user returns book (while at limit) and is next in hold queue
    cy.get('#notif-list').should('contain', 'is ready for you to borrow');

    cy.contains('#borrowed-table tr', 'Jane Eyre')
        .contains('Return')
        .click();

    // Assertion: Check this book actually returned by someone
    cy.get('#notif-list').should('contain', 'Returned "Jane Eyre" successfully.');

    // Assertion: Check Alice borrowed three books
    //            Borrowing capacity increases after return is asserted
    cy.get('#user-summary').should('contain', '2/3');

    // Alice try to borrows this book
    cy.contains('tr', 'Moby Dick').within(() => {
        cy.contains('Borrow').click();
    });

    // Assertion: Check the specific book was checked out by someone
    //            Borrowing capacity increases after return is asserted
    cy.get('#notif-list').should('contain', 'Borrowed "Moby Dick" successfully.');



  });

});
