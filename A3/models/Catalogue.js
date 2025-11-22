// Catalogue.js

class Catalogue {
    constructor() {
        this.catalogue = [];   // Array of Book objects
    }

    // Return full list
    allBooks() {
        return this.catalogue;
    }

    // Add book
    addBook(book) {
        this.catalogue.push(book);
    }

    // Return catalogue size
    getCatalogueSize() {
        return this.catalogue.length;
    }

    // Get book by index
    getBookByIndex(index) {
        return this.catalogue[index];  // Java behavior: no bounds check
    }

    // Get book by title
    getBookByTitle(title) {
        for (const b of this.catalogue) {
            if (b.getTitle().toLowerCase() === title.toLowerCase()) {
                return b;
            }
        }
        return null;
    }

    // --- Combined getBook(param) to match Java overloading ---
    getBook(param) {
        if (typeof param === "number") {
            return this.getBookByIndex(param);
        }
        if (typeof param === "string") {
            return this.getBookByTitle(param);
        }
        return null;
    }
}

module.exports = Catalogue;
