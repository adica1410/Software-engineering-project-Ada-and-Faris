const BASE_URL = "http://192.168.1.33:3000";

const testUser = {
  full_name: "Test User",
  email: `testuser_${Date.now()}@example.com`,
  password: "test12345"
};

let userId;
let goalId;
let sessionId;
let blockedWebsiteId;

describe("Focus Mode API Tests", () => {
  test("Register new user", async () => {
    const response = await fetch(`${BASE_URL}/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(testUser)
    });

    const data = await response.json();

    expect(response.status).toBe(201);
    expect(data.user.email).toBe(testUser.email);

    userId = data.user.id;
  });

  test("Login user", async () => {
    const response = await fetch(`${BASE_URL}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        email: testUser.email,
        password: testUser.password
      })
    });

    const data = await response.json();

    expect(response.status).toBe(200);
    expect(data.user.id).toBe(userId);
  });

  test("Create study goal", async () => {
    const response = await fetch(`${BASE_URL}/goals`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        user_id: userId,
        title: "Test Daily Goal",
        goal_type: "Daily",
        target_minutes: 120
      })
    });

    const data = await response.json();

    expect(response.status).toBe(201);
    expect(data.title).toBe("Test Daily Goal");

    goalId = data.id;
  });

  test("Create focus session", async () => {
    const response = await fetch(`${BASE_URL}/sessions`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        user_id: userId,
        start_time: "2026-06-06 10:00:00",
        end_time: "2026-06-06 10:25:00",
        duration_minutes: 25,
        duration_seconds: 1500,
        status: "completed"
      })
    });

    const data = await response.json();

    expect(response.status).toBe(201);
    expect(data.user_id).toBe(userId);

    sessionId = data.id;
  });

  test("Create blocked website", async () => {
    const response = await fetch(`${BASE_URL}/blocked-websites`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        user_id: userId,
        website_url: "instagram.com"
      })
    });

    const data = await response.json();

    expect(response.status).toBe(201);
    expect(data.website_url).toBe("instagram.com");

    blockedWebsiteId = data.id;
  });

  test("Change user password", async () => {
    const response = await fetch(`${BASE_URL}/users/${userId}/password`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        current_password: testUser.password,
        new_password: "newtest12345"
      })
    });

    const data = await response.json();

    expect(response.status).toBe(200);
    expect(data.message).toBe("Password changed successfully");
  });
});