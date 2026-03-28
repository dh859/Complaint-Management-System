import { buildRequest } from "./apiClient";

export const getAllComplaints = () =>
  buildRequest("api/admin/complaints");

export const getAdminComplaintById = (id) =>
  buildRequest(`api/admin/complaints/${id}`);

export const deleteComplaint = (id) =>
  buildRequest(`api/admin/complaints/${id}`, {
    method: "DELETE",
  });

export const updateComplaintStatus = (complaintId, status, remarks) =>
  buildRequest(
    `api/admin/complaints/${complaintId}/status?remarks=${remarks}`,
    {
      method: "PUT",
      body: JSON.stringify(status),
    }
  );

export const assignComplaint = (complaintId, agentId, remarks) =>
  buildRequest(
    `api/admin/complaints/${complaintId}/assign/${agentId}?remarks=${remarks}`,
    { method: "PUT" }
  );

export const forwardComplaint = (complaintId, departmentId, remarks) =>
  buildRequest(
    `api/admin/complaints/${complaintId}/forward/${departmentId}?remarks=${remarks}`,
    { method: "PUT" }
  );

export const getAssignedComplaints = () =>
  buildRequest("api/agent/complaints/assigned");

export const overrideCloseComplaint = (id, remarks) =>
  buildRequest(`api/agent/complaints/${id}/override-close?remarks=${remarks}`, {
    method: "PUT",
  });

export const getComplantsByDepartment = (departmentId) =>
  buildRequest(`api/admin/complaints/department/${departmentId}`);
