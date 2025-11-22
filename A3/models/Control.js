const Recording = require("./Recording");
const Book = require("./Book");

class Control {
    constructor(accounts, authentication, catalogue, holdList) {
        this.accounts = accounts;
        this.authentication = authentication;
        this.catalogue = catalogue;
        this.holdList = holdList;
    }

    // =======================
    // Borrow Logic
    // =======================
    processBorrow(borrower, book) {

        const due = new Date();
        due.setDate(due.getDate() + 14);

        const record = new Recording(book, due);
        borrower.addRecord(record);

        this.holdList.removeHold(book, borrower);

        book.setStatus(Book.Status.Checked_out);
        book.setDueDate(due);

        borrower.increaseBorrowedCount();
    }

    // =======================
    // Return Logic
    // =======================
    processReturn(borrower, book) {

        if (this.holdList.checkReservation(book)) {
            book.setStatus(Book.Status.On_hold);
        } else {
            book.setStatus(Book.Status.Available);
        }

        book.setDueDate(null);

        borrower.removeRecord(book);
    }
}

module.exports = Control;
