import { useState } from "react";
import AgentTabs from "./components/AgentTabs";
import OverviewTab from "./pages/OverviewTab";
import AssignedComplaints from "./pages/AssignedComplaints";
const AgentDashboard = () => {
  const [activeTab, setActiveTab] = useState("Overview");

  const renderContent = () => {
    switch (activeTab) {
      case "Overview":
        return <OverviewTab/>;
      case "Assigned":
        return <AssignedComplaints/>;
      default:
        return null;
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Title */}
      <h1 className="text-3xl font-bold text-blue-900">Agent Dashboard</h1>

      {/* Tabs */}
      <AgentTabs activeTab={activeTab} setActiveTab={setActiveTab} />

      {/* Content */}
      <div className="bg-white border border-blue-100 rounded-2xl shadow-lg p-6">
        {renderContent()}
      </div>
    </div>
  );
};

export default AgentDashboard;
