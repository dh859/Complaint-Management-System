import { buildRequest } from "./apiClient";

export const getAllUsers = () =>
  buildRequest("api/admin/users");

export const getUserById = (userId) =>
  buildRequest(`api/admin/users/${userId}`);

export const createUser = (data) =>
  buildRequest("api/admin/users", {
    method: "POST",
    body: JSON.stringify(data),
  });

export const deleteUser = (userId) =>
  buildRequest(`api/admin/users/${userId}`, {
    method: "DELETE",
  });

export const changeUserRole = (userId, role) =>
  buildRequest(`api/admin/users/${userId}/role`, {
    method: "PUT",
    body: JSON.stringify(role),
  });

export const changeUserStatus = (userId, isActive) =>
  buildRequest(`api/admin/users/${userId}/status`, {
    method: "PUT",
    body: JSON.stringify(isActive),
  });
