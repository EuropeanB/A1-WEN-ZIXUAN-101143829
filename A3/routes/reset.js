// routes/reset.js
const express = require("express");
const router = express.Router();

const InitializeAccounts = require("../utils/InitializeAccounts");
const InitializeLibrary = require("../utils/InitializeLibrary");

const holdList = require("../models/holdList");
const Authentication = require("../models/Authentication");
const Control = require("../models/Control");

// Initialization
function createInitialState() {
    const accounts = new InitializeAccounts().initializeAccounts();
    const catalogue = new InitializeLibrary().initializeLibrary();
    const holds = new holdList();
    const auth = new Authentication(accounts);
    const control = new Control(accounts, auth, catalogue, holds);

    return {
        accounts,
        catalogue,
        holds,
        auth,
        control
    };
}

const globalState = {};

// reset
function resetState() {
    const s = createInitialState();
    globalState.accounts  = s.accounts;
    globalState.catalogue = s.catalogue;
    globalState.holds     = s.holds;
    globalState.auth      = s.auth;
    globalState.control   = s.control;
}

// initialize
resetState();

router.post("/", (req, res) => {
    console.log("[DEBUG] reset invoked");
    resetState();
    res.json({ status: "reset ok" });
});

module.exports = { router, globalState };


