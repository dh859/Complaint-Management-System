import { UserPlus, Share2, RefreshCcw } from "lucide-react";

const ActionButton = ({ icon: Icon, label, onClick, variant = "primary" }) => {
  const base =
    "flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition";

  const variants = {
    primary:
      "bg-blue-600 text-white hover:bg-blue-700 shadow-sm",
    secondary:
      "bg-blue-50 text-blue-700 hover:bg-blue-100 border border-blue-200",
    danger:
      "bg-red-50 text-red-600 hover:bg-red-100 border border-red-200",
  };

  return (
    <button onClick={onClick} className={`${base} ${variants[variant]}`}>
      <Icon size={16} />
      {label}
    </button>
  );
};

const ActionBar = ({
  onAssign,
  onForward,
  onStatusUpdate,
  disabled = false,
}) => {

    

  return (
    <div className="bg-white border border-blue-100 rounded-xl shadow-sm p-4 flex flex-wrap gap-3">
      <ActionButton
        icon={UserPlus}
        label="Assign"
        onClick={onAssign}
        variant="primary"
        disabled={disabled}
      />

      <ActionButton
        icon={Share2}
        label="Forward"
        onClick={onForward}
        variant="secondary"
        disabled={disabled}
      />

      <ActionButton
        icon={RefreshCcw}
        label="Update Status"
        onClick={onStatusUpdate}
        variant="secondary"
        disabled={disabled}
      />
    </div>
  );
};

export default ActionBar;
