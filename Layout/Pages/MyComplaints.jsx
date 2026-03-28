import { useEffect, useState } from "react";
import Badge from "../components/Badge";
import TableComponent from "../components/TableComponent";
import { getMyComplaints } from "../../API/newapi/UserComplaintApi";
import ComplaintForm from "../components/ComplaintForm";
import ComplaintDetails from "../components/ComplaintDetails";

const MyComplaints = () => {
  const [showComplaintForm, setShowComplaintForm] = useState(false);
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedComplaint, setSelectedComplaint] = useState(null);
  const [showDetails, setShowDetails] = useState(false);


  useEffect(() => {
    const fetchComplaints = async () => {
      try {
        const data = await getMyComplaints();
        setComplaints(data.data ?? data);
      } catch (err) {
        console.error(err);
        setError("Failed to load complaints");
      } finally {
        setLoading(false);
      }
    };

    fetchComplaints();
  }, []);

  const columns = [
    { key: "complaintId", label: "ID" },
    { key: "subject", label: "Subject" },
    { key: "category", label: "Category" },
    { key: "createdAt", label: "Created On" },
    {
      key: "status",
      label: "Status",
      render: (row) => <Badge label={row.status} />,
    },
    {
      key: "actions",
      label: "Actions",
      render: (row) => (
        <div className="flex justify-center gap-3">
          <button
            className="text-blue-600 hover:underline font-medium"
            onClick={() =>{setSelectedComplaint(row);setShowDetails(true)}}
          >
            View
          </button>
          </div>
      ),
    },
  ];

  if (loading) {
    return <div className="p-8 text-gray-600">Loading complaints...</div>;
  }

  if (error) {
    return <div className="p-8 text-red-600">{error}</div>;
  }

  return (
    <div className="p-2">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-blue-900">My Complaints</h1>
        <button
          onClick={() => setShowComplaintForm(true)}
          className="px-5 py-2.5 bg-blue-700 text-white rounded-xl font-medium shadow-md hover:bg-blue-800 transition"
        >
          + Post Complaint
        </button>
      </div>
      {
        showDetails && (<ComplaintDetails onClose={()=>setShowDetails(false)} fetchComplaint={()=>selectedComplaint}/>)
      }
      {showComplaintForm && (
        <ComplaintForm onClose={() => setShowComplaintForm(false)} />
      )}

      <TableComponent
        columns={columns}
        data={complaints}
        emptyMessage="No complaints found"
      />
    </div>
  );
};

export default MyComplaints;
