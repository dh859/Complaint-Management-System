import {
  FaBars,
  FaTimes,
  FaHome,
  FaClipboardList,
  FaBell,
  FaFileAlt,
  FaUser,
} from "react-icons/fa";

const Sidebar = ({ activePage, setActivePage, open, setOpen }) => {
  const pages = [
    { key: "MainDashboard", label: "Dashboard", icon: <FaHome /> },
    { key: "Reports", label: "Reports", icon: <FaFileAlt /> },
    { key: "Notifications", label: "Notifications", icon: <FaBell /> },
    { key: "Profile", label: "Profile", icon: <FaUser /> },
    { key: "MyComplaints", label: "My Complaints", icon: <FaClipboardList /> },
  ];

  return (
    <aside
      className={`
        fixed top-18 left-0 z-30
        bg-blue-950 text-white
        min-h-screen
        transition-all duration-300
        ${open ? "w-56" : "w-16"}
      `}
    >
      {/* Header */}
      <div className="pt-5 px-4 pb-4 border-b border-blue-900 flex items-center justify-between">
        {open && (
          <div>
            <h1 className="text-xl font-bold">CMS</h1>
            <p className="text-xs text-blue-300">Complaint Management</p>
          </div>
        )}

        <button
          onClick={() => setOpen(!open)}
          className="text-blue-200 hover:text-white"
        >
          {open ? <FaTimes /> : <FaBars />}
        </button>
      </div>

      {/* Menu */}
      <nav className="p-3 space-y-2">
        {pages.map((page) => (
          <NavItem
            key={page.key}
            icon={page.icon}
            label={page.label}
            open={open}
            active={activePage === page.key}
            onClick={() => setActivePage(page.key)}
          />
        ))}
      </nav>
    </aside>
  );
};

const NavItem = ({ icon, label, open, active, onClick }) => {
  return (
    <div
      onClick={onClick}
      className={`
        flex items-center gap-3 px-3 py-2 rounded cursor-pointer
        transition
        ${
          active
            ? "bg-blue-700 text-white"
            : "text-blue-200 hover:bg-blue-800 hover:text-white"
        }
      `}
    >
      <span className="text-lg">{icon}</span>
      {open && <span className="whitespace-nowrap">{label}</span>}
    </div>
  );
};

export default Sidebar;
