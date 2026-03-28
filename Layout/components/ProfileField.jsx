const ProfileField = ({ label, name, value, isEditing, onChange,placeholder }) => {
  return (
    <div>
      <label className="block text-base font-semibold text-blue-800 mb-2">
        {label}
      </label>

      {isEditing ? (
        <input
          type="text"
          name={name}
          value={value}
          onChange={onChange}
          className="w-full px-5 py-3 text-base border border-blue-200 rounded-xl
                     focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder={placeholder}
        />
      ) : (
        <div className="px-5 py-3 bg-blue-50 rounded-xl text-gray-800 text-base">
          {value}
        </div>
      )}
    </div>
  );
};

export default ProfileField;
