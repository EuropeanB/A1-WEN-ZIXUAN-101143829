// Accounts.js

class Accounts {
    constructor() {
        this.list = []; // Array of Borrower objects
    }

    // add(Borrower b)
    add(borrower) {
        this.list.push(borrower);
    }

    // size()
    size() {
        return this.list.length;
    }

    // all() -> returns original list (NOT a copy)
    all() {
        return this.list;
    }

    // getUser(username)
    getUser(username) {
        for (const b of this.list) {
            if (b.getUsername() === username) {
                return b;
            }
        }
        return null;
    }
}

module.exports = Accounts;
