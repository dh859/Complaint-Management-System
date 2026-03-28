const BADGE_TYPE_MAP = {
  // PRIORITY
  LOW: "bg-green-100 text-green-700",
  MEDIUM: "bg-yellow-100 text-yellow-700",
  HIGH: "bg-orange-100 text-orange-700",
  URGENT: "bg-red-100 text-red-700",

  // ROLES
  USER: "bg-blue-100 text-blue-700",
  ADMIN: "bg-purple-100 text-purple-700",
  MANAGER: "bg-indigo-100 text-indigo-700",
  AGENT: "bg-teal-100 text-teal-700",

  // STATUS
  OPEN: "bg-red-100 text-red-700",
  ASSIGNED: "bg-blue-100 text-blue-700",
  IN_PROGRESS: "bg-yellow-100 text-yellow-700",
  FORWARDED: "bg-indigo-100 text-indigo-700",
  RESOLVED: "bg-green-100 text-green-700",
  CANCELED: "bg-gray-100 text-gray-700",
  CLOSED: "bg-gray-200 text-gray-800",
  REOPENED: "bg-orange-100 text-orange-700",

  INACTIVE: "bg-red-100 text-red-700",
  ACTIVE: "bg-green-100 text-green-700",
};

const DEFAULT_STYLE = "bg-gray-100 text-gray-700";

const Badge = ({ label }) => {
  const key = label?.toString().toUpperCase();

  return (
    <span
      className={`inline-flex items-center px-3 py-1 rounded-full
        text-xs font-semibold tracking-wide
        ${BADGE_TYPE_MAP[key] || DEFAULT_STYLE}
      `}
    >
      {label}
    </span>
  );
};

export default Badge;
