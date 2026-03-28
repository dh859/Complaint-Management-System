import { useEffect, useState } from "react";
import {
  FaClipboardCheck,
  FaCheckCircle,
  FaHourglassHalf,
} from "react-icons/fa";

import StatCard from "../../Layout/components/StatCard";
import {getAgentDashboard} from "../../API/newapi/DashboardApi";


const OverviewTab=()=>{

    const [stats, setStats] = useState({
        total: 0,
        resolved: 0,
        open: 0,
      });

      const [loading, setLoading] = useState(true);
      const [error, setError] = useState(null);
    
      useEffect(() => {
        const loadDashboard = async () => {
          try {
            const dashboard = await getAgentDashboard(); 
            setStats({
              total: dashboard.totalComplaints,
              resolved: dashboard.resolvedComplaints,
              open: dashboard.openComplaints,
            });
          } catch (err) {
            console.error(err);
            setError("Failed to load agent dashboard");
          } finally {
            setLoading(false);
          }
        };
    
        loadDashboard();
      }, []);

    if (loading) {
    return <div className="p-8 text-gray-600">Loading dashboard...</div>;
  }

  if (error) {
    return <div className="p-8 text-red-600">{error}</div>;
  }

  return (
    <div className="p-4 space-y-6 ">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-blue-900">
          Agent Dashboard
        </h1>
        <p className="text-sm text-gray-600 mt-1">
          Manage and resolve assigned complaints
        </p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        <StatCard
          title="Assigned Complaints"
          value={stats.total}
          icon={<FaClipboardCheck />}
        />
        <StatCard
          title="Resolved"
          value={stats.resolved}
          icon={<FaCheckCircle />}
        />
        <StatCard
          title="Open / In Progress"
          value={stats.open}
          icon={<FaHourglassHalf />}
        />
      </div>
      </div>
  )
}

export default OverviewTab;