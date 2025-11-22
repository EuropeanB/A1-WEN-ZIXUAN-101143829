const express = require("express");

console.log("[DEBUG] routes/auth.js loaded");

module.exports = (globalState) => {
    const router = express.Router();

    router.post("/login", (req, res) => {
        console.log("[DEBUG] POST /auth/login");
        console.log("[DEBUG] globalState.auth:", globalState.auth);
        console.log("[DEBUG] current user before login:", globalState.auth?.getCurrentUser?.());


        const auth = globalState.auth;

        const { username, password } = req.body;
        if (auth.login(username, password)) {
            return res.json({ success: true });
        }
        return res.status(400).json({ success: false });
    });

    router.post("/logout", (req, res) => {
        const auth = globalState.auth;
        auth.logout();
        res.json({ success: true });
    });

    router.get("/current", (req, res) => {
        const auth = globalState.auth;
        res.json({ user: auth.getCurrentUser() });
    });

    return router;
};
