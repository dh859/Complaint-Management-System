import { useState } from "react";
import AdminTabs from "./components/AdminTabs";
import OverviewTab from "./pages/OverviewTab";
import UserManagement from "./pages/UserManagementTab";
import ComplaintManagement from "./pages/ComplaintManagementTab";
import DepartmentManagementTab from "./pages/DepartmentManagementTab";
const AdminDashboard = () => {
  const [activeTab, setActiveTab] = useState("Overview");

  const renderContent = () => {
    switch (activeTab) {
      case "Overview":
        return <OverviewTab/>;
      case "users":
        return <UserManagement/>;
      case "complaints":
        return <ComplaintManagement />;
      case "departments":
        return <DepartmentManagementTab />;
      default:
        return null;
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Title */}
      <h1 className="text-3xl font-bold text-blue-900">Admin Dashboard</h1>

      {/* Tabs */}
      <AdminTabs activeTab={activeTab} setActiveTab={setActiveTab} />

      {/* Content */}
      <div className="bg-white border border-blue-100 rounded-2xl shadow-lg p-6">
        {renderContent()}
      </div>
    </div>
  );
};

export default AdminDashboard;
