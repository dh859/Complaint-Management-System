import Modal from "./Modal";

const ForwardModal = ({ isOpen, onClose, departments = [], onForward }) => {
  return (
    <Modal title="Forward Complaint" isOpen={isOpen} onClose={onClose}>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Forward To Department
          </label>
          <select
            className="w-full border border-blue-200 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400"
            onChange={(e) => onForward(e.target.value)}
          >
            <option value="">Select department</option>
            {departments.map((dep) => (
              <option key={dep.id} value={dep.id}>
                {dep.name}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Remarks (optional)
          </label>
          <textarea
            rows={3}
            className="w-full border border-blue-200 rounded-lg px-3 py-2"
            placeholder="Add remarks..."
          />
        </div>

        <div className="flex justify-end gap-3">
          <button onClick={onClose} className="px-4 py-2 border rounded-lg">
            Cancel
          </button>
          <button className="px-4 py-2 bg-blue-600 text-white rounded-lg">
            Forward
          </button>
        </div>
      </div>
    </Modal>
  );
};

export default ForwardModal;
