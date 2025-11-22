// public/app.js

// DOM
let userSummaryEl;
let loginSectionEl;
let mainContentEl;
let loginResultEl;
let globalMsgEl;

let borrowedTableBody;
let booksTableBody;
let notifListEl;

let currentUser = null;
let currentNotifications = [];

document.addEventListener("DOMContentLoaded", () => {
    userSummaryEl   = document.getElementById("user-summary");
    loginSectionEl  = document.getElementById("login-section");
    mainContentEl   = document.getElementById("main-content");
    loginResultEl   = document.getElementById("login-result");
    globalMsgEl     = document.getElementById("global-message");

    borrowedTableBody = document.querySelector("#borrowed-table tbody");
    booksTableBody    = document.querySelector("#books-table tbody");
    notifListEl       = document.getElementById("notif-list");

    document.getElementById("login-btn").addEventListener("click", onLogin);
    document.getElementById("logout-btn").addEventListener("click", onLogout);
    document.getElementById("reset-btn").addEventListener("click", onReset);

    initPage();
});

async function initPage() {
    await fetchCurrentUser();
    await refreshAll();
}


async function apiJson(url, options = {}) {
    const resp = await fetch(url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });
    const data = await resp.json().catch(() => ({}));
    return { ok: resp.ok, status: resp.status, data };
}


async function onLogin() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    loginResultEl.textContent = "";

    const { ok } = await apiJson("/api/auth/login", {
        method: "POST",
        body: JSON.stringify({ username, password })
    });

    if (!ok) {
        loginResultEl.textContent = "Login failed. Check username/password.";
        return;
    }

    await fetchCurrentUser();
    await refreshAll();
    await loadNotifications();
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    loginResultEl.textContent = "Login successful.";
}

async function onLogout() {
    await apiJson("/api/auth/logout", { method: "POST" });
    currentUser = null;
    clearNotifications();
    renderLayout();
}

async function onReset() {
    const { ok, data } = await apiJson("/api/reset", { method: "POST" });
    if (ok) {
        await fetchCurrentUser();
        await refreshAll();
    } else {
        //showGlobalMessage("Reset failed.", true);
    }
}

async function fetchCurrentUser() {
    const { ok, data } = await apiJson("/api/auth/current");
    currentUser = ok ? data.user : null;
}

function notify(text, isError = false) {
    notifListEl.innerHTML = "";

    const li = document.createElement("li");
    li.textContent = text;

    li.style.padding = "6px 8px";
    li.style.borderRadius = "6px";
    li.style.marginBottom = "6px";
    li.style.background = isError ? "#ffeaea" : "#eaffea";
    li.style.color = isError ? "#a00" : "#060";

    notifListEl.appendChild(li);
}

function clearNotifications() {
    notifListEl.innerHTML = "";
}

async function loadNotifications() {
    if (!currentUser) {
        clearNotifications();
        return;
    }

    const { ok, data } = await apiJson("/api/holds/notifications");

    if (!ok) {
        notify("Failed to load notifications.", true);
        return;
    }

    currentNotifications = data.books || [];

    renderNotifications();
}

async function loadBooks() {
    const { ok, data } = await apiJson("/api/books");
    if (!ok) return [];
    return data.books || [];
}

async function refreshAll() {
    renderLayout();

    if (!currentUser) return;

    //await loadNotifications();
    const allBooks = await loadBooks();

    renderBorrowedTable();
    renderBooksTable(allBooks);
}

function renderLayout() {
    const logoutBtn = document.getElementById("logout-btn");

    if (!currentUser) {
        userSummaryEl.textContent = "Not logged in";
        loginSectionEl.style.display = "block";
        mainContentEl.style.display = "none";
        logoutBtn.style.display = "none";
    } else {
        const borrowedCount = currentUser.borrowedCount ?? currentUser.borrowed_count ?? 0;
        const atLimit = borrowedCount >= 3;

        userSummaryEl.innerHTML = `
            Logged in as: <strong>${currentUser.username}</strong>
            | Borrowed: ${borrowedCount}/3
            ${atLimit ? '<span class="limit-flag">[AT LIMIT]</span>' : ""}
        `;

        loginSectionEl.style.display = "none";
        mainContentEl.style.display = "block";
        logoutBtn.style.display = "inline-block";
    }
}

function renderNotifications() {

    if (!currentNotifications || currentNotifications.length === 0) {
        notify("No notifications.");
        return;
    }

    const titles = currentNotifications.map(b => `"${b.title}"`);

    if (titles.length === 1) {
        notify(`Book ${titles[0]} is ready for you to borrow.`);
    } else {
        notify(`Books ${titles.join(", ")} are ready for you to borrow.`);
    }
}


function renderBorrowedTable() {
    borrowedTableBody.innerHTML = "";

    const records = currentUser?.records || [];

    if (records.length === 0) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 5;
        td.textContent = "You have not borrowed any books.";
        tr.appendChild(td);
        borrowedTableBody.appendChild(tr);
        return;
    }

    records.forEach((rec, idx) => {
        const tr = document.createElement("tr");

        const book = rec.book || {};
        const dueDate = rec.dueDate
            ? new Date(rec.dueDate).toISOString().split("T")[0]
            : "-";

        tr.innerHTML = `
            <td>${idx + 1}</td>
            <td>${book.title || ""}</td>
            <td>${book.author || ""}</td>
            <td>${dueDate}</td>
            <td></td>
        `;

        const actionTd = tr.lastElementChild;
        const btn = document.createElement("button");
        btn.className = "btn btn-secondary";
        btn.textContent = "Return";
        btn.addEventListener("click", () => handleReturn(book.title));
        actionTd.appendChild(btn);

        borrowedTableBody.appendChild(tr);
    });
}

function renderBooksTable(allBooks) {
    booksTableBody.innerHTML = "";

    if (!allBooks || allBooks.length === 0) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 6;
        td.textContent = "No books found.";
        tr.appendChild(td);
        booksTableBody.appendChild(tr);
        return;
    }

    const borrowedCount = currentUser?.borrowedCount ?? 0;

    allBooks.forEach((book, idx) => {
        const tr = document.createElement("tr");

        //const statusRaw = book.status || book.getStatus || "Available";
        const statusRaw = book.status;
        const statusLabel = formatStatus(statusRaw);
        const statusClass = statusClassName(statusRaw);
        const dueDate = book.dueDate
            ? new Date(book.dueDate).toISOString().split("T")[0]
            : "-";

        tr.innerHTML = `
            <td>${idx + 1}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td><span class="status-pill ${statusClass}">${statusLabel}</span></td>
            <td>${dueDate}</td>
            <td></td>
        `;

        const actionTd = tr.lastElementChild;

        const borrowBtn = document.createElement("button");
        borrowBtn.className = "btn btn-primary";
        borrowBtn.textContent = "Borrow";
        borrowBtn.addEventListener("click", () => handleBorrow(book.title));
        actionTd.appendChild(borrowBtn);

        if (statusRaw !== "Available" || borrowedCount >= 3) {
            const holdBtn = document.createElement("button");
            holdBtn.className = "btn btn-secondary";
            holdBtn.style.marginLeft = "4px";
            holdBtn.textContent = "Place Hold";
            holdBtn.addEventListener("click", () => handlePlaceHold(book.title));
            actionTd.appendChild(holdBtn);
        }

        booksTableBody.appendChild(tr);
    });
}

function formatStatus(status) {
    if (!status) return "-";
    if (status === "Checked_out") return "Checked Out";
    if (status === "On_hold") return "On Hold";
    return status;
}

function statusClassName(status) {
    if (status === "Checked_out") return "status-checkedout";
    if (status === "On_hold") return "status-onhold";
    return "status-available";
}

async function handleBorrow(title) {
    if (!currentUser) {
        notify("Please login first.", true);
        return;
    }

    const { ok, data } = await apiJson(`/api/books/${encodeURIComponent(title)}/borrow`, {
        method: "POST"
    });

    if (!ok) {
        notify(data.error || "Borrow failed.", true);
        return;
    }

    if (data.success) {
        notify(`Borrowed "${title}" successfully.`);
    } else {
        let msg = `Cannot borrow "${title}".`;

        switch (data.reason) {
            case "already_borrowed":
                msg = `You already have "${title}" checked out.`;
                break;

            case "checked_out":
                msg = `"${title}" is already checked out by another user.`;
                break;

            case "not_first_in_queue":
                msg = `You are not first in line for "${title}".`;
                break;

            case "limit":
                msg = `You have reached the borrowing limit (3 books).`;
                break;
        }

        notify(msg, true);
    }

    await fetchCurrentUser();
    await refreshAll();
}


async function handleReturn(title) {
    const { ok, data } = await apiJson(`/api/books/${encodeURIComponent(title)}/return`, {
        method: "POST"
    });

    if (!ok || !data.success) {
        notify(`Failed to return "${title}".`, true);
        return;
    }

    notify(`Returned "${title}" successfully.`);
    await fetchCurrentUser();
    await refreshAll();
}

async function handlePlaceHold(title) {
    if (!currentUser) {
        notify("Please login first.", true);
        return;
    }

    const { ok, data } = await apiJson(`/api/holds/${encodeURIComponent(title)}`, {
        method: "POST"
    });

    if (!ok) {
        notify(`Failed to place hold for "${title}".`, true);
        return;
    }

    if (data.success) {
        notify(`Hold placed for "${title}". You will be notified when it becomes available.`);
    } else {
        let msg = `Cannot place hold for "${title}".`;

        switch (data.reason) {
            case "already_borrowed":
                msg = `You already have "${title}" checked out.`;
                break;

            case "already_reserved":
                msg = `You have already reserved "${title}".`;
                break;
        }

        notify(msg, true);
    }

    await fetchCurrentUser();
    await refreshAll();
}
