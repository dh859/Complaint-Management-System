import { buildRequest } from "./apiClient";

export const getUserDashboard = () =>
  buildRequest("dashboard/user");

export const getManagerDashboard = () =>
  buildRequest("dashboard/manager");

export const getAgentDashboard = () =>
  buildRequest("dashboard/agent");

export const getAdminDashboard = () =>
  buildRequest("dashboard/admin");
