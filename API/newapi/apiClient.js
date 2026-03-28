export const BASE_URL = "http://localhost:7022";

// ---------- TOKEN HELPERS ----------
export const getAccessToken = () => localStorage.getItem("accessToken");
export const getRefreshToken = () => localStorage.getItem("refreshToken");

export const setTokens = (accessToken, refreshToken) => {
  localStorage.setItem("accessToken", accessToken);
  localStorage.setItem("refreshToken", refreshToken);
};

export const clearTokens = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
};

// ---------- AUTH HEADERS ----------
export const getAuthHeaders = () => {
  const token = getAccessToken();
  return {
    "Content-Type": "application/json",
    ...(token && { Authorization: `Bearer ${token}` }),
  };
};

// ---------- RESPONSE HANDLER ----------
export const handleResponse = async (res) => {
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error || `Request failed with ${res.status}`);
  }

  const contentType = res.headers.get("content-type");

  return contentType?.includes("application/json")
    ? res.json()
    : res.text();
};

// ---------- REFRESH TOKEN ----------
const refreshAccessToken = async () => {
  const refreshToken = getRefreshToken();
  if (!refreshToken) return null;

  const res = await fetch(`${BASE_URL}/auth/refresh-token`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ refreshToken }),
  });

  if (!res.ok) {
    clearTokens();
    return null;
  }

  const data = await res.json();
  setTokens(data.accessToken, data.refreshToken);
  return data.accessToken;
};

// ---------- BUILD REQUEST WITH RETRY ----------
export const buildRequest = async (
  endpoint,
  { method = "GET", body, headers = {} } = {},
  retry = true
) => {
  const res = await fetch(`${BASE_URL}/${endpoint}`, {
    method,
    headers: {
      ...getAuthHeaders(),
      ...headers,
    },
    body,
  });

  if (res.status === 401 && retry) {
    const newToken = await refreshAccessToken();
    if (newToken) {
      return buildRequest(endpoint, { method, body, headers }, false);
    } else {
      clearTokens();
      window.location.href = "/login"; 
      return;
    }
  }

  return handleResponse(res);
};
