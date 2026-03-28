import { BASE_URL, getAccessToken } from "./apiClient";

export const FileAPI = {

  getFile: async (fileId) => {
    const res = await fetch(`${BASE_URL}files/${fileId}/content`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`
      },
    });

    if (!res.ok) throw new Error("File download failed");

    return res.blob();
  },
};
