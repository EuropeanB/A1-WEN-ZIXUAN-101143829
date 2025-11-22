// Book.js

// Enum should NOT be recreated for every instance
const Status = {
    Available: "Available",
    Checked_out: "Checked_out",
    On_hold: "On_hold"
};

class Book {
    constructor(title, author) {
        this.title = title;
        this.author = author;

        this.status = Status.Available;
        this.dueDate = null;
    }

    // getters
    getTitle() { return this.title; }
    getAuthor() { return this.author; }
    getStatus() { return this.status; }
    getDueDate() { return this.dueDate; }

    getDueDateText() {
        return this.dueDate ? this.dueDate.toISOString().split("T")[0] : "-";
    }

    // setters
    setStatus(status) {
        this.status = status;
        if (status !== Status.Checked_out) {
            this.dueDate = null;
        }
    }

    setDueDate(date) {
        this.dueDate = date ? new Date(date) : null;
    }
}

// expose the enum
Book.Status = Status;

module.exports = Book;
