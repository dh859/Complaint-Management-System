import { useEffect, useState } from "react";
import { FaUserPlus, FaEdit, FaBan } from "react-icons/fa";
import TableComponent from "../../Layout/components/TableComponent";
import { createUser, getAllUsers ,changeUserStatus} from "../../API/newapi/ManageUsers";
import UserForm from "../components/UserForm";
import Badge from "../../Layout/components/Badge";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [openModal, setOpenModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadUsers = async () => {
    try {
      setLoading(true);
      const res = await getAllUsers();
      setUsers(res);
    } catch (err) {
      console.error("Failed to load users", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadUsers();
  }, []);

  const onClose = () => {
    setOpenModal(false);
    setEditingUser(null);
    setEditMode(false);
  };

  const onSubmit = async (formData) => {
    try {
      if (editMode) {
        // await updateUser(editingUser.id, formData);
        await changeUserStatus(editingUser.userId, true);
      } else {
        await createUser(formData);
      }
      onClose();
      loadUsers();
    } catch (err) {
      console.error("Failed to save user", err);
    }
  };

  const columns = [
    {key:"username", label:"Username"},
    { key: "fullname", label: "Name" },
    { key: "email", label: "Email" },
    {
      key: "role",
      label: "Role",
      render: (row) => <Badge label={row.role} />,
    },
    {
      key: "active",
      label: "Status",
      render: (row) => (
        <Badge label={row.active ? "Active" : "Inactive"} />
      ),
    },
    {
      key: "actions",
      label: "Actions",
      render: (row) => (
        <div className="flex justify-center gap-3">
          <button
            className="text-blue-600 hover:text-blue-800"
            onClick={() => {
              setEditingUser(row);
              setEditMode(true);
              setOpenModal(true);
            }}
          >
            <FaEdit />
          </button>

          <button
            className="text-red-600 hover:text-red-800"
            onClick={async () => {
              await changeUserStatus(row.userId, false);
              loadUsers();
            }}
          >
            <FaBan />
          </button>
        </div>
      ),
    },
  ];

  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-blue-900">
          User Management
        </h1>

        <button
          className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow"
          onClick={() => {
            setEditMode(false);
            setEditingUser(null);
            setOpenModal(true);
          }}
        >
          <FaUserPlus />
          Create User
        </button>
      </div>

      {loading ? (
        <div className="text-center text-blue-600 font-medium">
          Loading users...
        </div>
      ) : (
        <TableComponent columns={columns} data={users} />
      )}

      {openModal && (
        <UserForm
        isOpen={openModal}
          onClose={onClose}
          onSubmit={onSubmit}
          selectedUser={editingUser}
        />
      )}
    </div>
  );
};

export default UserManagement;
