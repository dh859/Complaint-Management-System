import {
  FaClipboardList,
  FaChartLine,
  FaRegListAlt
} from "react-icons/fa";

const tabs = [
  {
    key: "Overview",
    label: "Overview",
    icon: <FaChartLine />,
  },
  {
    key: "Assigned",
    label: "Assigned Complaints",
    icon: <FaClipboardList />,
  },
  {
    key:"pool",
    label:"Complaints Pool",
    icon:<FaRegListAlt/>
  }
];

const AgentTabs = ({ activeTab, setActiveTab }) => {
  return (
    <div className="bg-white border border-blue-100 rounded-xl shadow-sm">
      {/* Tabs Header */}
      <div className="flex border-b border-blue-100">
        {tabs.map((tab) => (
          <button
            key={tab.key}
            onClick={() => setActiveTab(tab.key)}
            className={`
              flex items-center gap-2 px-6 py-4 text-sm font-semibold
              transition relative
              ${
                activeTab === tab.key
                  ? "text-blue-700 bg-blue-50"
                  : "text-gray-600 hover:text-blue-700 hover:bg-blue-50"
              }
            `}
          >
            <span className="text-lg">{tab.icon}</span>
            {tab.label}

            
            {activeTab === tab.key && (
              <span className="absolute bottom-0 left-0 w-full h-[3px] bg-blue-600 rounded-t" />
            )}
          </button>
        ))}
      </div>
    </div>
  );
};

export default AgentTabs;
