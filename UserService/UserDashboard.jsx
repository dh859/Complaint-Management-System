import { useEffect, useState } from "react";
import {
  FaClipboardList,
  FaCheckCircle,
  FaHourglassHalf,
} from "react-icons/fa";

import StatCard from "../Layout/components/StatCard";
import MyComplaints from "../Layout/Pages/MyComplaints";
import { getUserDashboard } from "../API/newapi/DashboardApi";

const UserDashboard = () => {
  const [stats, setStats] = useState({
    total: 0,
    resolved: 0,
    pending: 0,
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadStats = async () => {
      try {
        const res = await getUserDashboard();
        setStats({
          total: res.totalComplaints,
          resolved: res.resolvedComplaints,
          pending: res.openComplaints,
        });
      } catch (err) {
        console.error("Failed to load dashboard stats", err);
      } finally {
        setLoading(false);
      }
    };

    loadStats();
  }, []);

  if (loading) {
    return <div className="p-8 text-gray-600">Loading dashboard...</div>;
  }

  return (
    <div className="p-4 space-y-6 bg-blue-50 min-h-screen">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-blue-900">
          User Dashboard
        </h1>
        <p className="text-sm text-gray-600 mt-1">
          Overview of your complaints and activity
        </p>
      </div>

      {/* Stat Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        <StatCard
          title="Total Complaints"
          value={stats.total}
          icon={<FaClipboardList />}
        />
        <StatCard
          title="Resolved"
          value={stats.resolved}
          icon={<FaCheckCircle />}
        />
        <StatCard
          title="Open Complaints"
          value={stats.pending}
          icon={<FaHourglassHalf />}
        />
      </div>

      {/* Complaints Section */}
      <div className="bg-white rounded-xl shadow-md border border-blue-100 p-4">
        <MyComplaints />
      </div>
    </div>
  );
};

export default UserDashboard;
