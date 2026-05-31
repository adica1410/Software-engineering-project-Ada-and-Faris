const express = require("express");
const cors = require("cors");
const db = require("./db");
const bcrypt = require("bcryptjs");

const app = express();
app.use(cors());
app.use(express.json());

// TEST ROUTE
app.get("/", (req, res) => {
  res.json({ message: "Focus Mode API is running" });
});

// REGISTER user
app.post("/register", async (req, res) => {
  const { full_name, email, password } = req.body;

  if (!full_name || !email || !password) {
    return res.status(400).json({ message: "Full name, email and password are required" });
  }

  const checkSql = "SELECT * FROM users WHERE email = ?";

  db.query(checkSql, [email], async (err, results) => {
    if (err) {
      return res.status(500).json({ message: "Error checking user", error: err.message });
    }

    if (results.length > 0) {
      return res.status(409).json({ message: "Email already registered" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const insertSql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";

    db.query(insertSql, [full_name, email, hashedPassword], (err, result) => {
      if (err) {
        return res.status(500).json({ message: "Error registering user", error: err.message });
      }

      res.status(201).json({
        message: "User registered successfully",
        user: {
          id: result.insertId,
          full_name,
          email
        }
      });
    });
  });
});

// LOGIN user
app.post("/login", (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ message: "Email and password are required" });
  }

  const sql = "SELECT * FROM users WHERE email = ?";

  db.query(sql, [email], async (err, results) => {
    if (err) {
      return res.status(500).json({ message: "Error logging in", error: err.message });
    }

    if (results.length === 0) {
      return res.status(401).json({ message: "Invalid email or password" });
    }

    const user = results[0];
    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.status(401).json({ message: "Invalid email or password" });
    }

    res.json({
      message: "Login successful",
      user: {
        id: user.id,
        full_name: user.full_name,
        email: user.email
      }
    });
  });
});


// CREATE goal
app.post("/goals", (req, res) => {
  const {
    user_id,
    title,
    goal_type,
    target_minutes,
    current_minutes = 0
  } = req.body;

  if (!user_id || !title || !goal_type || !target_minutes) {
    return res.status(400).json({
      message: "user_id, title, goal_type and target_minutes are required"
    });
  }

  const sql = `
    INSERT INTO study_goals
    (user_id, title, goal_type, target_minutes, current_minutes)
    VALUES (?, ?, ?, ?, ?)
  `;

  db.query(
    sql,
    [user_id, title, goal_type, target_minutes, current_minutes],
    (err, result) => {
      if (err) {
        return res.status(500).json({
          message: "Error creating goal",
          error: err.message
        });
      }

      res.status(201).json({
        id: result.insertId,
        user_id,
        title,
        goal_type,
        target_minutes,
        current_minutes
      });
    }
  );
});

// READ goals by user
app.get("/goals/user/:userId", (req, res) => {
  const sql = `
    SELECT * FROM study_goals
    WHERE user_id = ?
    ORDER BY created_at DESC
  `;

  db.query(sql, [req.params.userId], (err, results) => {
    if (err) {
      return res.status(500).json({
        message: "Error fetching goals",
        error: err.message
      });
    }

    res.json(results);
  });
});

// DELETE goal
app.delete("/goals/:id", (req, res) => {
  const sql = "DELETE FROM study_goals WHERE id = ?";

  db.query(sql, [req.params.id], (err, result) => {
    if (err) {
      return res.status(500).json({
        message: "Error deleting goal",
        error: err.message
      });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: "Goal not found" });
    }

    res.json({ message: "Goal deleted successfully" });
  });
});


// CREATE reminder
app.post("/reminders", (req, res) => {
  const {
    user_id,
    title,
    reminder_time,
    is_enabled = true
  } = req.body;

  if (!user_id || !title || !reminder_time) {
    return res.status(400).json({
      message: "user_id, title and reminder_time are required"
    });
  }

  const sql = `
    INSERT INTO reminders
    (user_id, title, reminder_time, is_enabled)
    VALUES (?, ?, ?, ?)
  `;

  db.query(
    sql,
    [user_id, title, reminder_time, is_enabled],
    (err, result) => {
      if (err) {
        return res.status(500).json({
          message: "Error creating reminder",
          error: err.message
        });
      }

      res.status(201).json({
        id: result.insertId,
        user_id,
        title,
        reminder_time,
        is_enabled
      });
    }
  );
});

// READ reminders by user
app.get("/reminders/user/:userId", (req, res) => {
  const sql = `
    SELECT * FROM reminders
    WHERE user_id = ?
    ORDER BY created_at DESC
  `;

  db.query(sql, [req.params.userId], (err, results) => {
    if (err) {
      return res.status(500).json({
        message: "Error fetching reminders",
        error: err.message
      });
    }

    res.json(results);
  });
});



// CREATE session
app.post("/sessions", (req, res) => {
  const {
    user_id = 1,
    start_time,
    end_time = null,
    duration_minutes = 0,
    status = "active"
  } = req.body;

  const sql = `
    INSERT INTO focus_sessions
    (user_id, start_time, end_time, duration_minutes, status)
    VALUES (?, ?, ?, ?, ?)
  `;

  db.query(sql, [user_id, start_time, end_time, duration_minutes, status], (err, result) => {
    if (err) {
      return res.status(500).json({ message: "Error creating session", error: err.message });
    }

    res.status(201).json({
      id: result.insertId,
      user_id,
      start_time,
      end_time,
      duration_minutes,
      status
    });
  });
});

// READ all sessions
app.get("/sessions", (req, res) => {
  const sql = "SELECT * FROM focus_sessions ORDER BY created_at DESC";

  db.query(sql, (err, results) => {
    if (err) {
      return res.status(500).json({ message: "Error fetching sessions", error: err.message });
    }

    res.json(results);
  });
});

// READ sessions by user
app.get("/sessions/user/:userId", (req, res) => {
  const sql = `
    SELECT * FROM focus_sessions
    WHERE user_id = ?
    ORDER BY created_at DESC
  `;

  db.query(sql, [req.params.userId], (err, results) => {
    if (err) {
      return res.status(500).json({
        message: "Error fetching user sessions",
        error: err.message
      });
    }

    res.json(results);
  });
});


// READ one session
app.get("/sessions/:id", (req, res) => {
  const sql = "SELECT * FROM focus_sessions WHERE id = ?";

  db.query(sql, [req.params.id], (err, results) => {
    if (err) {
      return res.status(500).json({ message: "Error fetching session", error: err.message });
    }

    if (results.length === 0) {
      return res.status(404).json({ message: "Session not found" });
    }

    res.json(results[0]);
  });
});

// UPDATE session
app.put("/sessions/:id", (req, res) => {
  const {
    start_time,
    end_time,
    duration_minutes,
    status
  } = req.body;

  const sql = `
    UPDATE focus_sessions
    SET
      start_time = COALESCE(?, start_time),
      end_time = COALESCE(?, end_time),
      duration_minutes = COALESCE(?, duration_minutes),
      status = COALESCE(?, status)
    WHERE id = ?
  `;

  db.query(sql, [start_time, end_time, duration_minutes, status, req.params.id], (err, result) => {
    if (err) {
      return res.status(500).json({ message: "Error updating session", error: err.message });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: "Session not found" });
    }

    res.json({ message: "Session updated successfully" });
  });
});

// DELETE session
app.delete("/sessions/:id", (req, res) => {
  const sql = "DELETE FROM focus_sessions WHERE id = ?";

  db.query(sql, [req.params.id], (err, result) => {
    if (err) {
      return res.status(500).json({ message: "Error deleting session", error: err.message });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: "Session not found" });
    }

    res.json({ message: "Session deleted successfully" });
  });
});

app.listen(3000, "0.0.0.0", () => {
  console.log("Backend server is running on port 3000");
});