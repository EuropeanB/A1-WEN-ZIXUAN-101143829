// holdList.js

const Book = require("./Book");

class holdList {
    constructor() {
        // Map<Book, Queue<Borrower>>
        this.holds = new Map();
    }

    // placeHold(Borrower borrower, Book book)
    placeHold(borrower, book) {
        let queue = this.holds.get(book);
        if (!queue) {
            queue = []; // JS array used as FIFO queue
            this.holds.set(book, queue);
        }
        queue.push(borrower);
    }

    // bookAvailable(Borrower borrower)
    bookAvailable(borrower) {
        const result = [];

        for (const [book, queue] of this.holds.entries()) {
            if (
                book.getStatus() === Book.Status.On_hold &&
                queue &&
                queue.length > 0 &&
                queue[0] === borrower
            ) {
                result.push(book);
            }
        }

        return result;
    }

    // isOnHold(Book book, Borrower borrower)
    isOnHold(book, borrower) {
        const queue = this.holds.get(book);
        const first = queue ? queue[0] : null;

        if (first === borrower) {
            return false;
        }
        return true;
    }

    // hasReserved(Book book, Borrower borrower)
    hasReserved(book, borrower) {
        const queue = this.holds.get(book);
        if (!queue) return false;
        return queue.includes(borrower);
    }

    // checkReservation(Book book)
    checkReservation(book) {
        const queue = this.holds.get(book);
        return queue !== undefined && queue.length > 0;
    }

    // removeHold(Book book, Borrower borrower)
    removeHold(book, borrower) {
        const queue = this.holds.get(book);
        if (queue && queue.length > 0) {
            const index = queue.indexOf(borrower);
            if (index !== -1) {
                queue.splice(index, 1);
            }
        }
    }

    // getQueue(Book book)
    getQueue(book) {
        return this.holds.get(book) || [];
    }
}

module.exports = holdList;
