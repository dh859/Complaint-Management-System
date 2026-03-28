import { useEffect, useState } from "react";
import Modal from "../../Layout/components/Modal";
import ProfileField from "../../Layout/components/ProfileField";

const DepartmentDetailsModal = ({
  isOpen,
  onClose,
  department = null,
  managers = [],
  mode = "view",
  isAdmin = false,
  onSave,
}) => {
  const isCreate = mode === "create";
  const isEdit = mode === "edit";
  const editable = isAdmin && (isEdit || isCreate);

  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    name: "",
    contactEmail: "",
    location: "",
    manager_id: "",
    manager_name: "",
  });

  useEffect(() => {
    if (!isOpen) return;

    try {
      setLoading(true);

      if (isCreate) {
        setForm({
          name: "",
          contactEmail: "",
          location: "",
          manager_id: "",
          manager_name: "",
        });
      } else if (department) {
        setForm({
          name: department.name || "",
          contactEmail: department.contactEmail || "",
          location: department.location || "",
          manager_id: department.manager_id || "",
          manager_name: department.manager_name || "",
        });
      }
    } catch (error) {
      alert("Error loading department data. " + error.message);
    } finally {
      setLoading(false);
    }
  }, [isOpen, mode, department, isCreate]);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = () => {
    const payload = {
      ...form,
      ...(isCreate ? {} : { departmentId: department.departmentId }),
    };

    onSave(payload);
    onClose();
  };

  const getTitle = () => {
    if (isCreate) return "Create Department";
    if (isEdit) return "Edit Department";
    return "Department Details";
  };

  return (
    <Modal title={getTitle()} isOpen={isOpen} onClose={onClose}>
      <div className="space-y-5">
        <ProfileField
          label="Department Name"
          name="name"
          value={form.name}
          isEditing={editable}
          onChange={handleChange}
        />

        <ProfileField
          label="Contact Email"
          name="contactEmail"
          value={form.contactEmail}
          isEditing={editable}
          onChange={handleChange}
        />

        <ProfileField
          label="Location"
          name="location"
          value={form.location}
          isEditing={editable}
          onChange={handleChange}
        />

        {/* Manager Assignment */}
        <div>
          <label className="block text-base font-semibold text-blue-800 mb-2">
            Manager
          </label>

          {editable ? (
            <select
              name="manager_id"
              value={form.manager_id || ""}
              onChange={handleChange}
              className="w-full px-5 py-3 text-base border border-blue-200 rounded-xl
                         focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Unassigned</option>
              {managers.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.name}
                </option>
              ))}
            </select>
          ) : (
            <div className="px-5 py-3 bg-blue-50 rounded-xl text-gray-800 text-base">
              {department?.manager_name || "Not Assigned"}
            </div>
          )}
        </div>

        {/* Footer */}
        <div className="flex justify-end gap-3 pt-4 border-t border-blue-100">
          <button
            onClick={onClose}
            className="px-4 py-2 border bg-red-600 text-white rounded-lg text-sm"
          >
            Cancel
          </button>

          {editable && (
            <button
              onClick={handleSubmit}
              className="px-4 py-2 bg-blue-600 text-white rounded-lg text-sm hover:bg-blue-700"
            >
              {isCreate ? "Create Department" : "Save Changes"}
            </button>
          )}
        </div>
      </div>
    </Modal>
  );
};

export default DepartmentDetailsModal;
