const express = require("express");
const cors = require("cors");

const app = express();
app.use(cors());
app.use(express.json());

let focusSessions = [];

// CREATE session
app.post("/sessions", (req, res) => {
  const session = {
    id: Date.now(),
    user_id: req.body.user_id || 1,
    start_time: req.body.start_time,
    end_time: req.body.end_time || null,
    duration_minutes: req.body.duration_minutes || 0,
    status: req.body.status || "active",
    created_at: new Date()
  };

  focusSessions.push(session);
  res.status(201).json(session);
});

// READ all sessions
app.get("/sessions", (req, res) => {
  res.json(focusSessions);
});

// READ one session
app.get("/sessions/:id", (req, res) => {
  const session = focusSessions.find(s => s.id == req.params.id);

  if (!session) {
    return res.status(404).json({ message: "Session not found" });
  }

  res.json(session);
});

// UPDATE session
app.put("/sessions/:id", (req, res) => {
  const session = focusSessions.find(s => s.id == req.params.id);

  if (!session) {
    return res.status(404).json({ message: "Session not found" });
  }

  session.start_time = req.body.start_time || session.start_time;
  session.end_time = req.body.end_time || session.end_time;
  session.duration_minutes = req.body.duration_minutes ?? session.duration_minutes;
  session.status = req.body.status || session.status;

  res.json(session);
});

// DELETE session
app.delete("/sessions/:id", (req, res) => {
  const index = focusSessions.findIndex(s => s.id == req.params.id);

  if (index === -1) {
    return res.status(404).json({ message: "Session not found" });
  }

  focusSessions.splice(index, 1);
  res.json({ message: "Session deleted successfully" });
});

app.listen(3000, () => {
  console.log("Backend server is running on port 3000");
});