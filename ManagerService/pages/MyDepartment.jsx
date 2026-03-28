import { useEffect, useState } from "react";
import ProfileField from "../../Layout/components/ProfileField";
import { getDepartmentById } from "../../API/newapi/ManageDepartmentApi";

const MyDepartment = () => {
  const [department, setDepartment] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchDepartment = async () => {
      try {
        setLoading(true);
        const res = await getDepartmentById(3); 
        setDepartment(res);
      } catch (err) {
        setError("Unable to load department details." + err.message);
        console.error("Failed to load department details", err);
      } finally {
        setLoading(false);
      }
    };

    fetchDepartment();
  }, []);

  if (loading) {
    return (
      <div className="p-6 text-blue-700 font-medium">
        Loading department details...
      </div>
    );
  }

  if (error) {
    return (
      <div className="p-6 text-red-600 font-medium">
        {error}
      </div>
    );
  }

  if (!department) {
    return (
      <div className="p-6 text-gray-600">
        No department assigned.
      </div>
    );
  }

  return (
    <div className="p-6">
      {/* Page Header */}
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-blue-900">
          My Department
        </h1>
        <p className="text-gray-600 text-sm">
          View department information assigned to you
        </p>
      </div>

      {/* Department Card */}
      <div className="bg-white rounded-2xl p-6 max-w-full">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <ProfileField
            label="Department Name"
            name="name"
            value={department.name}
            isEditing={false}
          />

          <ProfileField
            label="Contact Email"
            name="contactEmail"
            value={department.contactEmail}
            isEditing={false}
          />

          <ProfileField
            label="Location"
            name="location"
            value={department.location || "—"}
            isEditing={false}
          />

          <ProfileField
            label="Manager"
            name="manager"
            value={department.manager_name || "Not Assigned"}
            isEditing={false}
          />
        </div>

        {/* Meta Info */}
        <div className="mt-6 pt-4 border-t border-blue-100 text-sm text-gray-500">
          <p>
            Department ID: <span className="font-medium">{department.departmentId}</span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default MyDepartment;
