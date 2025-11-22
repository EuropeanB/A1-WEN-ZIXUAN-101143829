const express = require("express");
const Book = require("../models/Book");

console.log("[DEBUG] routes/holds.js loaded");

module.exports = (globalState) => {
    const router = express.Router();

    router.post("/:title", (req, res) => {
        console.log("[DEBUG] POST /holds/" + req.params.title);
        console.log("[DEBUG] globalState.auth:", globalState.auth);
        console.log("[DEBUG] globalState.holds:", globalState.holds);
        console.log("[DEBUG] current user:", globalState.auth?.getCurrentUser?.());

        const { holds, catalogue, auth } = globalState;

        const borrower = auth.getCurrentUser();
        if (!borrower) return res.status(401).json({ error: "Not logged in" });

        const book = catalogue.getBook(req.params.title);
        if (!book) return res.status(404).json({ error: "Book not found" });

        const alreadyBorrowed = borrower.getRecords()
            .some(r => r.getBook() === book);

        if (alreadyBorrowed) {
            return res.json({ success: false, reason: "already_borrowed" });
        }

        // Already reserved
        if (holds.hasReserved(book, borrower)) {
            return res.json({ success: false, reason: "already_reserved" });
        }

        holds.placeHold(borrower, book);
        return res.json({ success: true });
    });

    router.get("/notifications", (req, res) => {
        const { holds, auth } = globalState;

        const borrower = auth.getCurrentUser();
        if (!borrower) return res.status(401).json({ error: "Not logged in" });

        const list = holds.bookAvailable(borrower);
        res.json({ books: list });
    });

    return router;
};
