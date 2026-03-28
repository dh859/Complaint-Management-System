import {
  FaClipboardList,
  FaCheckCircle,
  FaExclamationCircle,
  FaUsers,
  FaUserCheck,
  FaUserTie,
  FaHeadset,
} from "react-icons/fa";
import StatCard from "../../Layout/components/StatCard";
import { useEffect, useState } from "react";
import {getAdminDashboard} from "../../API/newapi/DashboardApi";

const OverviewTab = () => {
  const [data, setData] = useState({});
  const [loading, setLoading] = useState(true);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      const res = await getAdminDashboard();
      console.log("DASHBOARD DATA:", res);
      setData(res);
    } catch (err) {
      console.error("Failed to load dashboard data", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDashboardData();
  }, []);

  const statsConfig = [
    {
      title: "Total Complaints",
      key: "totalComplaints",
      icon: <FaClipboardList />,
    },
    {
      title: "Open Complaints",
      key: "openComplaints",
      icon: <FaExclamationCircle />,
    },
    {
      title: "Resolved Complaints",
      key: "resolvedComplaints",
      icon: <FaCheckCircle />,
    },
    {
      title: "Total Users",
      key: "totalUsers",
      icon: <FaUsers />,
    },
    {
      title: "Active Users",
      key: "activeUsers",
      icon: <FaUserCheck />,
    },
    {
      title: "Active Managers",
      key: "activeManagers",
      icon: <FaUserTie />,
    },
    {
      title: "Active Agents",
      key: "activeAgents",
      icon: <FaHeadset />,
    },
  ];

  return (
    <div className="p-6 space-y-8">
      {/* Page Title */}
      <h1 className="text-2xl font-bold text-blue-900">
        Admin Dashboard Overview
      </h1>

      {/* Stats Section */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {statsConfig.map((stat) => (
          <StatCard
            key={stat.key}
            title={stat.title}
            value={data?.[stat.key] ?? 0}
            icon={stat.icon}
            loading={loading}
          />
        ))}
      </div>
    </div>
  );
};

export default OverviewTab;
