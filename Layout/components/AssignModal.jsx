import Modal from "./Modal";

const AssignModal = ({ isOpen, onClose, agents = [], onAssign }) => {
  
  return (
    <Modal title="Assign Complaint" isOpen={isOpen} onClose={onClose}>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Select Agent
          </label>
          <select
            className="w-full border border-blue-200 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            onChange={(e) => onAssign(e.target.value)}
          >
            <option value="">Choose agent</option>
            {agents.map((agent) => (
              <option key={agent.id} value={agent.id}>
                {agent.name}
              </option>
            ))}
          </select>
        </div>

        <div className="flex justify-end gap-3">
          <button
            onClick={onClose}
            className="px-4 py-2 rounded-lg text-sm border border-gray-300"
          >
            Cancel
          </button>
          <button
            className="px-4 py-2 rounded-lg text-sm bg-blue-600 text-white hover:bg-blue-700"
          >
            Assign
          </button>
        </div>
      </div>
    </Modal>
  );
};

export default AssignModal;
