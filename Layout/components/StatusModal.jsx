import Modal from "./Modal";

const StatusModal = ({ isOpen, onClose, onUpdate }) => {
  return (
    <Modal title="Update Status" isOpen={isOpen} onClose={onClose}>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Complaint Status
          </label>
          <select
            className="w-full border border-blue-200 rounded-lg px-3 py-2"
            onChange={(e) => onUpdate(e.target.value)}
          >
            <option value="">Select status</option>
            <option value="OPEN">Open</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="RESOLVED">Resolved</option>
            <option value="CLOSED">Closed</option>
          </select>
        </div>

        <div className="flex justify-end gap-3">
          <button onClick={onClose} className="px-4 py-2 border rounded-lg">
            Cancel
          </button>
          <button className="px-4 py-2 bg-blue-600 text-white rounded-lg">
            Update
          </button>
        </div>
      </div>
    </Modal>
  );
};

export default StatusModal;
