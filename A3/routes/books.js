// routes/books.js
const express = require("express");
const Book = require("../models/Book");

module.exports = (globalState) => {
    const router = express.Router();

    // =============================
    // Borrow a book
    // =============================
    router.post("/:title/borrow", (req, res) => {
        console.log("[DEBUG] POST /books/" + req.params.title + "/borrow");

        const { catalogue, control, holds, auth } = globalState;

        const borrower = auth.getCurrentUser();
        if (!borrower) {
            console.log("[DEBUG] Borrow failed: not logged in");
            return res.status(401).json({ error: "Not logged in" });
        }

        const book = catalogue.getBook(req.params.title);
        if (!book) {
            console.log("[DEBUG] Borrow failed: book not found");
            return res.status(404).json({ error: "Book not found" });
        }

        // If borrower already checked out this book
        const alreadyBorrowed = borrower.getRecords()
            .some(r => r.getBook() === book);

        if (alreadyBorrowed) {
            return res.json({ success: false, reason: "already_borrowed" });
        }

        if (borrower.getBorrowedCount() >= 3) {
            console.log("[DEBUG] Borrow failed: borrow limit");
            return res.json({ success: false, reason: "limit" });
        }

        if (book.getStatus() === Book.Status.Checked_out) {
            console.log("[DEBUG] Borrow failed: checked out");
            return res.json({ success: false, reason: "checked_out" });
        }

        if (book.getStatus() === Book.Status.On_hold &&
            holds.isOnHold(book, borrower)) {
            console.log("[DEBUG] Borrow failed: not first in queue");
            return res.json({ success: false, reason: "not_first_in_queue" });
        }

        // Borrow successfully
        control.processBorrow(borrower, book);
        console.log("[DEBUG] Borrow success");
        return res.json({ success: true });
    });

    // =============================
    // Return a book
    // =============================
    router.post("/:title/return", (req, res) => {
        console.log("[DEBUG] POST /books/" + req.params.title + "/return");

        const { catalogue, control, auth } = globalState;

        const borrower = auth.getCurrentUser();
        if (!borrower) return res.status(401).json({ error: "Not logged in" });

        const book = catalogue.getBook(req.params.title);
        if (!book) return res.status(404).json({ error: "Book not found" });

        control.processReturn(borrower, book);
        return res.json({ success: true });
    });

    router.get("/", (req, res) => {
        const { catalogue, holds, auth } = globalState;
        const current = auth.getCurrentUser();

        const books = catalogue.allBooks().map(book => {
            let status = book.getStatus();

            const queue = holds.getQueue(book);

            if (status === Book.Status.On_hold &&
                queue.length > 0 &&
                queue[0] === current) {

                status = Book.Status.Available;
            }

            return {
                title: book.getTitle(),
                author: book.getAuthor(),
                status,
                dueDate: book.getDueDate(),
                queueLength: queue.length,
            };
        });

        res.json({ books });
    });


    return router;
};
