const StatCard = ({ title, value, icon }) => (
  <div className="bg-white rounded-xl shadow-md border border-blue-100 p-5 flex items-center gap-4">
    <div className="text-blue-600 text-3xl">{icon}</div>
    <div>
      <p className="text-sm text-gray-500">{title}</p>
      <h2 className="text-2xl font-semibold text-blue-900">{value}</h2>
    </div>
  </div>
);

export default StatCard;  