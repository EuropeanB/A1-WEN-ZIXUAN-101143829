// Borrower.js

class Borrower {
    constructor(username, password) {
        this.username = username;
        this.password = password;
        this.borrowedCount = 0;
        this.records = []; // array of Recording
    }

    // getters
    getUsername() {
        return this.username;
    }

    getPassword() {
        return this.password;
    }

    getBorrowedCount() {
        return this.borrowedCount;
    }

    getRecords() {
        return [...this.records];
    }

    // setters
    increaseBorrowedCount() {
        this.borrowedCount += 1;
    }

    decreaseBorrowedCount() {
        this.borrowedCount -= 1;
    }

    // handling the system recording
    addRecord(record) {
        this.records.push(record);
    }

    removeRecord(book) {
        // removeIf(r -> r.getBook().equals(book))
        this.records = this.records.filter(r => r.getBook() !== book);
        this.decreaseBorrowedCount();
    }
}

module.exports = Borrower;
