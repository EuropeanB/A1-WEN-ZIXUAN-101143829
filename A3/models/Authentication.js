// Authentication.js

class Authentication {
    constructor(accounts) {
        this.accounts = accounts;
        this.currentUser = null;
    }

    // login(String username, String password)
    login(username, password) {
        const allUsers = this.accounts.all();

        for (const borrower of allUsers) {
            if (
                borrower.getUsername() === username &&
                borrower.getPassword() === password
            ) {
                this.currentUser = borrower;
                return true;
            }
        }
        return false;
    }

    // getCurrentUser()
    getCurrentUser() {
        return this.currentUser;
    }

    // logout()
    logout() {
        this.currentUser = null;
    }
}

module.exports = Authentication;
