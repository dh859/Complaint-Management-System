import { useEffect } from "react";
import { useAuth } from "../../Auth/UseAuth";
import AdminDashboard from "../../AdminService/AdminDashboard";
import UserDashboard from "../../UserService/UserDashboard";
import AgentDashboard from "../../AgentService/AgentDashboard";
import ManagerDashboard from "../../ManagerService/ManagerDashboard";

const MainDashboard = ({ setActivePage }) => {
  const { user, loading } = useAuth();

  useEffect(() => {
    if (loading) return;

    if (!user) {
      setActivePage("SignIn");
    }
  }, [loading, user,setActivePage]);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full">
        <div
          className="bg-white px-6 py-4 rounded-xl shadow
                        text-blue-700 font-semibold"
        >
          Loading dashboard...
        </div>
      </div>
    );
  }

  if (!user) 
    {
      return null;
    }
  const role = user.role;

  switch (role) {
    case "ADMIN":
      return <AdminDashboard />;

    case "MANAGER":
      return <ManagerDashboard />;

    case "AGENT":
      return <AgentDashboard />;

    case "USER":
      return <UserDashboard />;

    default:
      return (
        <div className="flex items-center justify-center h-full">
          <div
            className="bg-red-50 border border-red-200
                          text-red-700 px-6 py-4 rounded-xl"
          >
            Unauthorized Access
          </div>
        </div>
      );
  }
};

export default MainDashboard;
