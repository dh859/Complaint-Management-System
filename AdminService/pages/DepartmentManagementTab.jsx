import { useEffect, useState } from "react";
import TableComponent from "../../Layout/components/TableComponent";
import DepartmentDetailsModal from "../components/DepartmentDetailsModal";
import {getAllDepartments,createDepartment} from "../../API/newapi/ManageDepartmentApi";

const DepartmentManagementTab = () => {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);

  const [modalOpen, setModalOpen] = useState(false);
  const [mode, setMode] = useState("create"); 
  const [selectedDept, setSelectedDept] = useState(null);

  const fetchDepartments = async () => {
    setLoading(true);
    try {
      const res = await getAllDepartments();
      setDepartments(res.data ?? res);
      
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDepartments();
  }, []);

  /* ---------- Handlers ---------- */

  const handleCreate = async (payload) => {
    await createDepartment(payload);
    closeModal();
    fetchDepartments();
  };

  const handleUpdate = async (payload) => {
    // await updateDepartment(payload.departmentId, payload);
    console.log("Updating department:", payload);
    closeModal();
    fetchDepartments();
  };

  const openCreateModal = () => {
    setSelectedDept(null);
    setMode("create");
    setModalOpen(true);
  };

  const openViewModal = (row) => {
    setSelectedDept(row);
    setMode("view");
    setModalOpen(true);
  }

  const openEditModal = (row) => {
    setSelectedDept(row);
    setMode("edit");
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setSelectedDept(null);
  };

  /* ---------- Table ---------- */

  const columns = [
    { key: "id", label: "ID" },
    { key: "name", label: "Department Name" },
    { key: "contactEmail", label: "Email" },
    { key: "managerName", label: "Manager" },
    { key: "activeStaffCount", label:"Staff Count"},
    {
      key: "actions",
      label: "Actions",
      render: (row) => (
        <>
          <button
            onClick={() => openViewModal(row)}
            className="text-blue-600 hover:underline font-medium"
          >
            View
          </button>
          <button
            onClick={() => openEditModal(row)}
            className="text-blue-600 hover:underline font-medium ml-2"
          >
            Edit
          </button>
        </>
      ),
    },
  ];
  console.log("Departments:", departments);

  if (loading) {
    return <div className="p-8 text-gray-600">Loading departments...</div>;
  }

  return (
    <div className="p-2">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-blue-900">
          Department Management
        </h1>

        <button
          onClick={openCreateModal}
          className="px-5 py-2.5 bg-blue-700 text-white rounded-xl font-medium shadow-md hover:bg-blue-800 transition"
        >
          + Add Department
        </button>
      </div>

      <TableComponent
        columns={columns}
        data={departments}
        emptyMessage="No departments found"
      />

      <DepartmentDetailsModal
        isOpen={modalOpen}
        onClose={closeModal}
        mode={mode}
        department={selectedDept}
        isAdmin={true}
        managers={[]} // pass manager list when ready
        onSave={mode === "create" ? handleCreate : handleUpdate}
      />
    </div>
  );
};

export default DepartmentManagementTab;
