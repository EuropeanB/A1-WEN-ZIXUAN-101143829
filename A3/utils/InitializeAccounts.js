const Accounts = require("../models/Accounts");
const Borrower = require("../models/Borrower");

// EXACT 1:1 conversion from Java
class InitializeAccounts {
    initializeAccounts() {
        const accounts = new Accounts();
        accounts.add(new Borrower("alice", "pass123"));
        accounts.add(new Borrower("bob", "pass456"));
        accounts.add(new Borrower("charlie", "pass789"));
        return accounts;
    }
}

module.exports = InitializeAccounts;
