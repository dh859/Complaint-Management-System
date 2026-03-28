import { useEffect, useState } from "react";
import {
  FaEye,
  FaEdit,
  FaArrowRight,
} from "react-icons/fa";

import TableComponent from "../../Layout/components/TableComponent";
import Badge from "../../Layout/components/Badge";
import { getComplantsByDepartment, getAdminComplaintById } from "../../API/newapi/ManageCompaintApi";
import ComplaintDetails from "../../Layout/components/ComplaintDetails";


const AllComplaints = () => {
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showDetails, setShowDetails] = useState(false);
  const [selectedComplaint, setSelectedComplaint] = useState(null);
  

  const loadComplaints = async () => {
    try {
      setLoading(true);
      const res = await getComplantsByDepartment();
      setComplaints(res);
    } catch (err) {
      console.error("Failed to load complaints", err);
    } finally {
      setLoading(false);
    }
  };

  const fetchComplaint = async () => {
    const res= await getAdminComplaintById(selectedComplaint.complaintId);
    return res;
  }

  useEffect(() => {
    loadComplaints();
  }, []);

  const columns = [
    {
      key: "complaintId",
      label: "ID",
    },
    {
      key: "subject",
      label: "Subject",
    },
    {
      key: "category",
      label: "Category",
      render: (row) => <span className="font-medium">{row.category}</span>,
    },
    {
      key: "priority",
      label: "Priority",
      render: (row) => <Badge label={row.priority} />,
    },
    {
      key: "status",
      label: "Status",
      render: (row) => <Badge label={row.status} />,
    },
    {
      key: "createdAt",
      label: "Created On",
      render: (row) => (
        <span className="text-sm text-gray-600">
          {new Date(row.createdAt).toLocaleDateString()}
        </span>
      ),
    },
    {
      key: "actions",
      label: "Actions",
      render: (row) => (
        <div className="flex justify-center gap-3">
          <button
            className="text-blue-600 hover:text-blue-800"
            title="View"
            onClick={() => {setSelectedComplaint(row);setShowDetails(true)}}
          >
            <FaEye />
          </button>

          <button
            className="text-indigo-600 hover:text-indigo-800"
            title="Edit"
            onClick={() => console.log("Edit", row.complaintId)}
          >
            <FaEdit />
          </button>

          <button
            className="text-green-600 hover:text-green-800"
            title="Forward"
            onClick={() => console.log("Forward", row.complaintId)}
          >
            <FaArrowRight />
          </button>
        </div>
      ),
    },
  ];

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-blue-900">
          Complaint Management
        </h1>
      </div>

      {showDetails && (
        <ComplaintDetails onClose={()=>setShowDetails(false)} fetchComplaint={fetchComplaint}/>
      )}

      {/* Table */}
      {loading ? (
        <div className="text-center text-blue-600 font-medium">
          Loading complaints...
        </div>
      ) : (
        <TableComponent columns={columns} data={complaints} />
      )}
    </div>
  );
};

export default AllComplaints;
