// userComplaintAPI.js

import { buildRequest } from "./apiClient";

export const createComplaint = (formData) =>
  buildRequest("api/user/complaints/create", {
    method: "POST",
    headers: { "Content-Type": "multipart/form-data" },
    body: formData,
  });

export const getMyComplaints = () =>
  buildRequest("api/user/complaints/my");

export const getComplaintById = (id) =>
  buildRequest(`api/user/complaints/${id}`);

export const cancelComplaint = (id, remarks) =>
  buildRequest(
    `api/user/complaints/${id}/cancel?remarks=${remarks}`,
    { method: "PUT" }
  );
