import { FaTimes } from "react-icons/fa";
import Badge from "../components/Badge";
import DetailItem from "./DetailItem";
import {  useEffect, useState } from "react";
import AttachmentItem from "./AttatchmentItem";
import AuthContext from "../../Auth/AuthContext";
import ActionBar from "./ActionBar";
import AssignModal from "./AssignModal";
import ForwardModal from "./ForwardModal";
import StatusModal from "./StatusModal";

const ComplaintDetails = ({ onClose, fetchComplaint }) => {
  const [complaint, setComplaint] = useState(null);
  const [loading, setLoading] = useState(true);
  const [assignOpen, setAssignOpen] = useState(false);
  const [forwardOpen, setForwardOpen] = useState(false);
  const [statusOpen, setStatusOpen] = useState(false);;

  useEffect(() => {
    const loadComplaint = async () => {
      try {
        const res = await fetchComplaint();
        setComplaint(res);
        console.log("COMPLAINT DETAILS:", res);
      } catch (error) {
        console.error("Failed to load complaint details", error);
      } finally {
        setLoading(false);
      }
    };

    loadComplaint();
  }, [fetchComplaint]);

  const STATUS_COLOR = {
    OPEN: "yellow",
    IN_PROGRESS: "blue",
    RESOLVED: "green",
    REJECTED: "red",
  };

  const PRIORITY_COLOR = {
    LOW: "green",
    MEDIUM: "yellow",
    HIGH: "red",
  };

  const formatDate = (date) =>
    new Date(date).toLocaleString("en-IN", {
      day: "2-digit",
      month: "short",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });

  if (loading) {
    return (
      <div className="fixed inset-0 z-50 bg-black/40 flex items-center justify-center">
        <div className="bg-white p-8 rounded-xl shadow">
          Loading complaint details...
        </div>
      </div>
    );
  }

  if (!complaint) return null;

  return (
    <div className="fixed inset-0 z-50 bg-black/40 flex items-start justify-center px-4">
      <div className="max-w-6xl w-3xl mt-20 max-h-[85vh] no-scrollbar overflow-y-auto relative">
        <div className="bg-white rounded-2xl shadow-lg border border-blue-100 p-10 space-y-10">
          {/* Header */}
          <div>
            <div className=" flex ml-5 items-center gap-4 mb-3">
              <h1 className="text-3xl font-bold text-blue-900">
                Complaint Details
              </h1>

              <Badge
                label={complaint.status}
                color={STATUS_COLOR[complaint.status]}
              />

              <button
                onClick={onClose}
                className="ml-auto text-gray-400 hover:text-red-500 text-xl"
              >
                <FaTimes />
              </button>
            </div>

            <p className="text-lg  ml-5 text-gray-700">
              <span className="font-semibold">Complaint ID:</span>
              {complaint.complaintId}
            </p>
          </div>

          {/* Title */}
          <section className="ml-5">
            <h2 className="text-xl  font-semibold text-blue-800 mb-2">Title</h2>
            <p className="text-gray-800 text-lg">{complaint.subject}</p>
          </section>

          {/* Description */}
          <section className="ml-5">
            <h2 className="text-xl  font-semibold text-blue-800 mb-2">
              Description
            </h2>
            <p className="text-gray-700 leading-relaxed">
              {complaint.description}
            </p>
          </section>

          {/* Metadata */}
          <section className="grid grid-cols-1 ml-5 md:grid-cols-2 gap-8">
            <DetailItem label="Category" value={complaint.category} />
            <DetailItem label="Department" value={complaint.department_Name} />
            <DetailItem
              label="Priority"
              value={
                <Badge
                  label={complaint.priority}
                  color={PRIORITY_COLOR[complaint.priority]}
                />
              }
            />
            <DetailItem label="Raised By" value={complaint.raisedByUser_Name} />
            <DetailItem
              label="Assigned To"
              value={complaint.assignedToUser_Name || "Not Assigned"}
            />
            <DetailItem
              label="Created On"
              value={formatDate(complaint.createdAt)}
            />
            <DetailItem
              label="Last Updated"
              value={formatDate(complaint.updatedAt)}
            />
          </section>

          {/* Attachments */}
          <section>
            <h2 className="text-xl font-semibold text-blue-800 mb-3">
              Attachments
            </h2>

            {complaint.queryFiles?.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {complaint.queryFiles.map((file) => (
                  <AttachmentItem key={file.fileId} file={file} />
                ))}
              </div>
            ) : (
              <div className="border-2 border-dashed border-blue-200 rounded-xl p-6 text-gray-500">
                No attachments uploaded
              </div>
            )}
          </section>

          {/* Actions */}
          <section className="flex flex-wrap gap-4 pt-4 border-t border-blue-100">
            {/* <button className="px-6 py-3 bg-blue-600 text-white rounded-xl hover:bg-blue-700">
              Assign
            </button>
            <button className="px-6 py-3 bg-green-600 text-white rounded-xl hover:bg-green-700">
              Mark as Resolved
            </button> */}
            <ActionBar
              onAssign={() => setAssignOpen(true)}
              onForward={() => setForwardOpen(true)}
              onStatusUpdate={() => setStatusOpen(true)}
            />

            <AssignModal
              isOpen={assignOpen}
              onClose={() => setAssignOpen(false)}
            />
            <ForwardModal
              isOpen={forwardOpen}
              onClose={() => setForwardOpen(false)}
            />
            <StatusModal
              isOpen={statusOpen}
              onClose={() => setStatusOpen(false)}
            />

            <button
              onClick={onClose}
              className="px-6 py-3 bg-gray-200 text-gray-800 rounded-xl hover:bg-gray-300"
            >
              Back
            </button>
          </section>
        </div>
      </div>
    </div>
  );
};

export default ComplaintDetails;
