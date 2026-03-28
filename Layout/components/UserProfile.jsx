import { useEffect, useState } from "react";
import Badge from "../components/Badge";
import ProfileField from "./ProfileField";
import UsernameHeader from "./UsernameHeader";
import { AuthAPI } from "../../API/newapi/AuthApi";


const UserProfile = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await AuthAPI.getMe();
        console.log("FULL RESPONSE:", response);
        setUser(response);
      } catch (err) {
        console.error("Failed to load user profile", err);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSave = () => {
    try {
      AuthAPI.updateProfile(user);
      setIsEditing(false);
    } catch (error) {
      console.error("Failed to save profile", error);
    }
  };

  if (loading) return <div className="p-10">Loading profile...</div>;
  if (!user) return <div className="p-10 text-red-500">Please Login</div>;

  return (
    <div className="max-w-6xl mx-auto px-8 py-5">
      {/* Header */}
      <div className="flex justify-between items-center mb-10">
        <h1 className="text-3xl font-bold text-blue-900">My Profile</h1>

        {!isEditing ? (
          <button
            onClick={() => setIsEditing(true)}
            className="px-6 py-3 bg-blue-600 text-white rounded-xl hover:bg-blue-700 shadow-md"
          >
            Edit Profile
          </button>
        ) : (
          <div className="space-x-4">
            <button
              onClick={handleSave}
              className="px-6 py-3 bg-green-600 text-white rounded-xl shadow-md"
            >
              Save Changes
            </button>
            <button
              onClick={() => setIsEditing(false)}
              className="px-6 py-3 bg-gray-200 text-gray-700 rounded-xl"
            >
              Cancel
            </button>
          </div>
        )}
      </div>

      <UsernameHeader
        username={user.username}
        userId={user.userId}
        createdAt={user.createdAt}
      />

      <div className="bg-white rounded-2xl shadow-lg border border-blue-100 p-10">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-10">
          <ProfileField
            label="Full Name"
            name="fullname"
            value={user.fullname ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="Email"
            name="email"
            value={user.email ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="Contact Number"
            name="contactNumber"
            value={user.contactNumber ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="Address"
            name="address"
            value={user.address ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="City"
            name="city"
            value={user.city ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="State"
            name="state"
            value={user.state ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <ProfileField
            label="Pincode"
            name="pincode"
            value={user.pincode ?? ""}
            isEditing={isEditing}
            onChange={handleChange}
          />

          <div>
            <label className="block text-base font-semibold text-blue-800 mb-2">
              Status
            </label>
            <Badge label={user.active ? "Active" : "Inactive"} />
          </div>

          <div>
            <label className="block text-base font-semibold text-blue-800 mb-2">
              Role
            </label>
            <Badge label={user.role} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
