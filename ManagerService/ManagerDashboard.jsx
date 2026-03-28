import { useState } from "react";
import OverviewTab from "./pages/OverviewTab";
import ManagerTabs from "./components/ManagerTabs";
import MyDepartment from "./pages/MyDepartment";
import AllComplaints from "./pages/AllComplaints";
const ManagerDashboard = () => {
  const [activeTab, setActiveTab] = useState("Overview");

  const renderContent = () => {
    switch (activeTab) {
      case "Overview":
        return <OverviewTab/>;
      case "AllComplaints":
        return <AllComplaints/>;
      case "MyDepartment":
        return <MyDepartment/>;
      default:
        return null;
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Title */}
      <h1 className="text-3xl font-bold text-blue-900">Agent Dashboard</h1>

      {/* Tabs */}
      <ManagerTabs activeTab={activeTab} setActiveTab={setActiveTab} />

      {/* Content */}
      <div className="bg-white border border-blue-100 rounded-2xl shadow-lg p-6">
        {renderContent()}
      </div>
    </div>
  );
};

export default ManagerDashboard;
