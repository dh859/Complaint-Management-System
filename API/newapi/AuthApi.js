import { buildRequest } from "./apiClient";
import { setTokens, clearTokens } from "./apiClient";

export const AuthAPI = {

  login: async (data) => {
    const res = await buildRequest("auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    setTokens(res.accessToken, res.refreshToken);
    return res;
  },

  register: (data) =>
    buildRequest("auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }),

  refreshToken: (data) =>
    buildRequest("auth/refresh-token", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }),

  validateToken: (token) =>
    buildRequest("auth/validate-token", {
      method: "POST",
      headers: { Authorization: token },
    }),

  getMe: () => buildRequest("auth/me"),

  logout: async () => {
    await buildRequest("auth/logout");
    clearTokens();
  },

  updateProfile: (data) =>
    buildRequest("auth/update", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }),

  changePassword: (oldPassword, newPassword) =>
    buildRequest(`auth/changePassword?oldPassword=${oldPassword}&newPassword=${newPassword}`, {
      method: "PUT",
    }),
};
