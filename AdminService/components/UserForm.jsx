import { useState } from "react";
import Modal from "../../Layout/components/Modal";
import ProfileField from "../../Layout/components/ProfileField";

const UserForm = ({ isOpen, onClose, onSubmit,selectedUser }) => {
  const [formData, setFormData] = useState({
    username: selectedUser ? selectedUser.username : "",
    fullname: selectedUser ? selectedUser.fullname : "",
    password: "",
    email: selectedUser ? selectedUser.email : "",
    role: selectedUser ? selectedUser.role : "USER",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
    onClose();
  };

  return (
    <Modal title="Create User" isOpen={isOpen} onClose={onClose}>
      <form className="space-y-5" onSubmit={handleSubmit}>
        <ProfileField
          label=""
          name="username"
          value={formData.username}
          placeholder="Username"
          isEditing={true}
          onChange={handleChange}
        />

        <ProfileField
          label=""
          name="fullname"
          value={formData.fullname}
          placeholder="Full Name"
          isEditing={true}
          onChange={handleChange}
        />

        <ProfileField
          label=""
          name="email"
          value={formData.email}
          placeholder="Email"
          isEditing={true}
          onChange={handleChange}
        />

        <ProfileField
          label=""
          name="password"
          value={formData.password}
          placeholder="Password"
          isEditing={true}
          onChange={handleChange}
        />

        {/* Role */}
        <div>
          <label className="block text-base font-semibold text-blue-800 mb-2">
            Role
          </label>

          <select
            name="role"
            value={formData.role}
            onChange={handleChange}
            className="w-full px-5 py-3 text-base border border-blue-200 rounded-xl
                       focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="ADMIN">ADMIN</option>
            <option value="MANAGER">MANAGER</option>
            <option value="AGENT">AGENT</option>
            <option value="USER">USER</option>
          </select>
        </div>

        {/* Footer */}
        <div className="flex justify-end gap-3 pt-4 border-t border-blue-100">
          <button
            type="button"
            className="px-4 py-2 border rounded-lg text-sm"
            onClick={onClose}
          >
            Cancel
          </button>

          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded-lg text-sm hover:bg-blue-700"
          >
            Save
          </button>
        </div>
      </form>
    </Modal>
  );
};

export default UserForm;
