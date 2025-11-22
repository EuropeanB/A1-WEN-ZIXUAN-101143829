// Recording.js

class Recording {
    constructor(book, dueDate) {
        this.book = book;        // keep direct reference
        this.dueDate = dueDate;  // JS Date object or null
    }

    // getters
    getBook() {
        return this.book;
    }

    getDueDate() {
        return this.dueDate;
    }
}

module.exports = Recording;
