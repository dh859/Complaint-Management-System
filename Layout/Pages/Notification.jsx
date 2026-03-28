import { useEffect, useState } from "react";
import { BASE_URL, getAuthHeaders } from "../../API/newapi/apiClient";
import Badge from "../components/Badge";

const Notification = () => {
  const [users, setUsers] = useState([]);
  const [selectedEmails, setSelectedEmails] = useState([]);
  const [search, setSearch] = useState("");
  const [subject, setSubject] = useState("");
  const [title, setTitle] = useState("");
  const [message, setMessage] = useState("");
  const [isHtml, setIsHtml] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetch(`${BASE_URL}/api/admin/users`, {
      headers: getAuthHeaders(),
    })
      .then((res) => res.json())
      .then((data) => setUsers(data))
      .catch((err) => console.error("Failed to fetch users", err));
  }, []);

  const filteredUsers = users.filter(
    (user) =>
      user.fullname.toLowerCase().includes(search.toLowerCase()) ||
      user.email.toLowerCase().includes(search.toLowerCase()),
  );

  const handleUserSelect = (email) => {
    setSelectedEmails((prev) =>
      prev.includes(email) ? prev.filter((e) => e !== email) : [...prev, email],
    );
  };

  const handleSendMail = async () => {
    if (!subject || !message || selectedEmails.length === 0) {
      alert("Please select users and fill required fields.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(`${BASE_URL}/mail/notify`, {
        method: "POST",
        headers: getAuthHeaders(),
        body: JSON.stringify({
          to: selectedEmails,
          subject,
          message,
          title,
          html: isHtml,
        }),
      });

      if (!response.ok) throw new Error("Failed");

      alert("Notification sent successfully!");
      setSubject("");
      setTitle("");
      setMessage("");
      setSelectedEmails([]);
      setIsHtml(false);
    } catch (err) {
      alert("Error sending notification :" + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-2">
      {/* Two Column Layout */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* LEFT PANEL - Search + User List */}
        <div className="bg-white shadow rounded-2xl p-5 flex flex-col">
          <h3 className="text-lg font-medium mb-4">Select Recipients</h3>

          {/* Search */}
          <input
            type="text"
            placeholder="Search by name or email..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="mb-4 border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400 outline-none"
          />

          {/* Selected Count */}
          <div className="text-xs text-gray-500 mb-2">
            {selectedEmails.length} selected
          </div>

          {/* User List */}
          <div className="h-100 no-scrollbar overflow-y-auto border rounded-xl">
            <ul className="divide-y">
              {filteredUsers.map((user) => (
                <li
                  key={user.userId}
                  className="flex items-center justify-between px-3 py-2 hover:bg-gray-50 transition"
                >
                  <div className="flex items-center gap-2">
                    <input
                      type="checkbox"
                      checked={selectedEmails.includes(user.email)}
                      onChange={() => handleUserSelect(user.email)}
                      className="accent-blue-600"
                    />

                    <div className="leading-tight">
                      <p className="text-sm font-medium">{user.fullname}</p>
                      <p className="text-xs text-gray-500">{user.email}</p>
                    </div>
                  </div>

                  <Badge label={user.role} />
                </li>
              ))}
            </ul>
          </div>
        </div>

        {/* RIGHT PANEL - Compose Mail */}
        <div className="lg:col-span-2 bg-white shadow rounded-2xl p-5 space-y-4">
          <h3 className="text-lg font-medium">Compose Notification</h3>

          <div>
            <label className="block text-sm mb-1">Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400 outline-none"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Subject *</label>
            <input
              type="text"
              value={subject}
              onChange={(e) => setSubject(e.target.value)}
              className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400 outline-none"
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Message *</label>
            <textarea
              rows="8"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              className="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400 outline-none"
            />
          </div>

          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={isHtml}
              onChange={() => setIsHtml(!isHtml)}
              className="accent-blue-600"
            />
            <span className="text-sm text-gray-600">Send as HTML email</span>
          </div>

          <div className="flex justify-end">
            <button
              onClick={handleSendMail}
              disabled={loading}
              className="px-6 py-2 bg-blue-600 text-white rounded-xl hover:bg-blue-700 transition disabled:opacity-50"
            >
              {loading ? "Sending..." : "Send Notification"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Notification;
