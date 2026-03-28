import { buildRequest } from "./apiClient";

export const getNotifications = () =>
  buildRequest("api/notifications");

export const getUnreadNotifications = () =>
  buildRequest("api/notifications/unread");

export const markNotificationRead = (id) =>
  buildRequest(`api/notifications/${id}/read`, {
    method: "PUT",
  });

export const markAllNotificationsRead = () =>
  buildRequest("api/notifications/read-all", {
    method: "PUT",
  });

export const deleteNotification = (id) =>
  buildRequest(`api/notifications/${id}`, {
    method: "DELETE",
  });
