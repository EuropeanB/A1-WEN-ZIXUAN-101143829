const express = require("express");
const path = require("path");
const app = express();

app.use(express.json());

// reset
const { router: resetRouter, globalState } = require("./routes/reset");

// reset route
app.use("/api/reset", resetRouter);

// globalState
app.use("/api/auth", require("./routes/auth")(globalState));
app.use("/api/holds", require("./routes/holds")(globalState));
app.use("/api/books", require("./routes/books")(globalState));

app.use(express.static(path.join(__dirname, "public")));

app.get("/", (req, res) => {
    res.sendFile(path.join(__dirname, "public", "index.html"));
});

const PORT = 3000;
app.listen(PORT, () => {
    console.log("Server running at http://localhost:" + PORT);
});
