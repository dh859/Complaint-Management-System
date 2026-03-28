import { buildRequest } from "./apiClient";

export const MailAPI = {

  notify: (data) =>
    buildRequest("mail/notify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }),
};
