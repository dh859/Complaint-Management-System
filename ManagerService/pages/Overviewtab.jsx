import { useEffect, useState } from "react";
import {
  ClipboardList,
  CheckCircle,
  AlertTriangle,
  Users,
} from "lucide-react";
import StatCard from "../../Layout/components/StatCard"; 
import { getManagerDashboard } from "../../API/newapi/DashboardApi";

const OverviewTab = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadStats = async () => {
      try {
        const res = await getManagerDashboard(); 
        setStats(res);
      } catch (err) {
        console.error("Failed to load manager dashboard stats", err);
      } finally {
        setLoading(false);
      }
    };

    loadStats();
  }, []);

  if (loading) {
    return (
      <div className="text-center py-10 text-gray-500">
        Loading overview...
      </div>
    );
  }

  return (
    <div className="space-y-8">
      {/* Header */}
      <div>
        <h2 className="text-2xl font-semibold text-gray-800">
          Manager Overview
        </h2>
        <p className="text-gray-500 mt-1">
          System-wide complaint and agent summary
        </p>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard
          title="Total Complaints"
          value={stats.totalComplaints}
          icon={<ClipboardList className="w-6 h-6" />}
        />
        <StatCard
          title="Open Complaints"
          value={stats.openComplaints}
          icon={<AlertTriangle className="w-6 h-6" />}
        />
        <StatCard
          title="Resolved Complaints"
          value={stats.resolvedComplaints}
          icon={<CheckCircle className="w-6 h-6" />}
        />
        <StatCard
          title="Active Agents"
          value={stats.activeAgents}
          icon={<Users className="w-6 h-6" />}
        />
      </div>

      {/* Manager Insights */}
      <div className="bg-white rounded-xl border border-blue-100 shadow-sm p-6">
        <h3 className="text-lg font-semibold text-gray-800 mb-2">
          Quick Insights
        </h3>
        <ul className="text-sm text-gray-600 space-y-2">
          <li>
            • {stats.openComplaints} complaints currently need agent attention
          </li>
          <li>
            • Resolution rate:{" "}
            <span className="font-medium">
              {stats.totalComplaints > 0
                ? Math.round(
                    (stats.resolvedComplaints / stats.totalComplaints) * 100
                  )
                : 0}
              %
            </span>
          </li>
          <li>
            • {stats.activeAgents} agents actively handling complaints
          </li>
        </ul>
      </div>
    </div>
  );
};

export default OverviewTab;
