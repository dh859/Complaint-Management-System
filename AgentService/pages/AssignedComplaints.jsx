import { useEffect, useState } from "react";

import Badge from "../../Layout/components/Badge";
import TableComponent from "../../Layout/components/TableComponent";
import {getAssignedComplaints} from "../../API/newapi/ManageCompaintApi";

const AssignedComplaints = () => {
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        const res = await getAssignedComplaints();
        setComplaints(res);
      } catch (err) {
        console.error(err);
        setError("Failed to load agent dashboard");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, []);

  const columns = [
    { key: "complaintId", label: "ID" },
    { key: "subject", label: "Subject" },
    { key: "category", label: "Category" },
    {
      key: "status",
      label: "Status",
      render: (row) => <Badge label={row.status} />,
    },
    { key: "createdAt", label: "Created On" },
    {
      key: "priority",
      label: "Priority",
      render: (row) => (
        <span className="font-medium text-blue-700">
          {row.priority ?? "Normal"}
        </span>
      ),
    },
  ];

  if (loading) {
    return <div className="p-8 text-gray-600">Loading dashboard...</div>;
  }

  if (error) {
    return <div className="p-8 text-red-600">{error}</div>;
  }

  return (
    <div className="p-4 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-blue-900">
          Assigned complaints
        </h1>
        <p className="text-sm text-gray-600 mt-1">
          Manage and resolve assigned complaints
        </p>
      </div>
        <TableComponent
          columns={columns}
          data={complaints}
          emptyMessage="No assigned complaints"
        />
      </div>
  );
};

export default AssignedComplaints;
