import {  useState } from "react";
import {createComplaint} from "../../API/newapi/UserComplaintApi";
import { FaTimes } from "react-icons/fa";
import { useAuth } from "../../Auth/UseAuth";

const ComplaintForm = ({ onClose }) => {
  const { user} = useAuth();
  const userRole = user?.role?.toUpperCase();


  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    title: "",
    category: "",
    priority: "MEDIUM",
    description: "",
    attachment: null,
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const formData = new FormData();
      formData.append("subject", form.title);
      formData.append("category", form.category.toUpperCase());
      formData.append("priority", form.priority.toUpperCase());
      formData.append("description", form.description);
      if (form.attachment) {
        formData.append("files", form.attachment);
      }

      await createComplaint(formData);
      onClose(); 
    } catch (err) {
      console.error("Failed to submit complaint", err);
      alert("Failed to submit complaint");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 bg-black/40 flex items-center justify-center px-4">
      
      <div className="bg-white w-full max-w-3xl rounded-2xl shadow-xl border border-blue-100 relative">
        
        {/* Header */}
        <div className="flex items-center justify-between px-8 py-5 border-b border-blue-100">
          <h2 className="text-2xl font-bold text-blue-900">
            Submit Complaint
          </h2>

          <button
            onClick={onClose}
            className="text-gray-400 hover:text-red-500 text-xl"
          >
            <FaTimes/>
          </button>
        </div>

        {/* Form */}
        <form
          onSubmit={handleSubmit}
          className="p-8 space-y-6 max-h-[80vh] overflow-y-auto"
        >
          {/* Title */}
          <div>
            <label className="form-label">Complaint Title</label>
            <input
              name="title"
              required
              value={form.title}
              onChange={handleChange}
              className="form-input"
            />
          </div>

          {/* Category */}
          <div>
            <label className="form-label">Category</label>
            <select
              name="category"
              required
              value={form.category}
              onChange={handleChange}
              className="form-input"
            >
              <option value="">Select category</option>
              <option>Technical</option>
              <option>Service</option>
              <option>Billing</option>
              <option>Other</option>
              
            </select>
          </div>

          {/* Priority */}
          <div>
            <label className="form-label">Priority</label>

            {userRole === "USER" ? (
              <div className="form-readonly">MEDIUM</div>
            ) : (
              <div className="flex gap-3">
                {["LOW", "MEDIUM", "HIGH","URGENT"].map((p) => (
                  <button
                    key={p}
                    type="button"
                    onClick={() => setForm({ ...form, priority: p })}
                    className={`px-4 py-2 rounded-lg border font-medium
                      ${
                        form.priority === p
                          ? "bg-blue-600 text-white border-blue-600"
                          : "bg-white border-blue-200 text-blue-700 hover:bg-blue-50"
                      }`}
                  >
                    {p}
                  </button>
                ))}
              </div>
            )}
          </div>

          {/* Description */}
          <div>
            <label className="form-label">Description</label>
            <textarea
              name="description"
              rows="5"
              required
              value={form.description}
              onChange={handleChange}
              className="form-input resize-none"
            />
          </div>

          {/* Attachment */}
          <div>
            <label className="form-label">Attachment</label>
            <input
              type="file"
              name="attachment"
              onChange={handleChange}
              className="block w-full text-sm text-gray-600"
            />
          </div>

          {/* Footer */}
          <div className="flex justify-end gap-4 pt-4 border-t border-blue-100">
            <button
              type="button"
              onClick={onClose}
              className="px-6 py-2 rounded-lg border border-gray-300 text-gray-600 hover:bg-gray-100"
            >
              Cancel
            </button>

            <button
              type="submit"
              disabled={loading}
              className="px-6 py-2 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700"
            >
              {loading ? "Submitting..." : "Submit"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ComplaintForm;
