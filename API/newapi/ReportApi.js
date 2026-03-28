import { buildRequest, BASE_URL, getAuthHeaders } from "./apiClient";

export const getAllReports = () =>
  buildRequest("reports/all-reports");

export const getMyReports = () =>
  buildRequest("reports/my-reports");

export const getReportById = (reportId) =>
  buildRequest(`reports/reportId/${reportId}`);

export const getReportByIdAdmin = (reportId) =>
  buildRequest(`reports/admin/${reportId}`);

export const getReportsByType = (reportType) =>
  buildRequest(`reports/report-type/${reportType}`);

export const getReportsByUserId = (userId) =>
  buildRequest(`reports/user-reports/${userId}`);

export const getSystemReport = () =>
  buildRequest("reports/system-report");

export const getComplaintSummaryReport = () =>
  buildRequest("reports/complaint-summary");


export const getCategorySummaryReport = (category) =>
  buildRequest("reports/category-summary", {
    method: "GET",
    body: JSON.stringify(category),
  });


export const getAgentPerformanceReport = (agentId) =>
  buildRequest("reports/agent-performance", {
    method: "GET",
    body: JSON.stringify(agentId),
  });


export const viewReport = (id) =>
  buildRequest(`reports/${id}/view`);

export const downloadReport = async (id) => {
  const res = await fetch(`${BASE_URL}/reports/${id}/download`, {
    method: "GET",
    headers: getAuthHeaders(),
  });

  if (!res.ok) {
    const error = await res.text();
    throw new Error(error || "Download failed");
  }

  const blob = await res.blob();
  const url = window.URL.createObjectURL(blob);

  const a = document.createElement("a");
  a.href = url;
  a.download = `report-${id}.pdf`;
  document.body.appendChild(a);
  a.click();
  a.remove();
};

export const deleteReport = (reportId) =>
  buildRequest(`reports/delete-report/${reportId}`, {
    method: "DELETE",
  });
