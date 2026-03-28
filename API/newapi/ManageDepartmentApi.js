import { buildRequest } from "./apiClient";

export const getAllDepartments = () =>
  buildRequest("api/admin/departments");

export const getDepartmentById = (deptId) =>
  buildRequest(`api/admin/departments/${deptId}`);

export const createDepartment = (data) =>
  buildRequest("api/admin/departments", {
    method: "POST",
    body: JSON.stringify(data),
  });

export const updateDepartment = (deptId, data) =>
  buildRequest(`api/admin/departments/${deptId}`, {
    method: "PUT",
    body: JSON.stringify(data),
  });

export const deleteDepartment = (deptId) =>
  buildRequest(`api/admin/departments/${deptId}`, {
    method: "DELETE",
  });

export const searchDepartment = (name) =>
  buildRequest(`api/admin/departments/search/${name}`);
