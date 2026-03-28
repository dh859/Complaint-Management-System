import { Clipboard, Check } from "lucide-react";
import { useState } from "react";

const UsernameHeader = ({ username, createdAt }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    await navigator.clipboard.writeText(username);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="flex items-center gap-6 mb-8">
      {/* Avatar */}
      <div className="w-20 h-20 rounded-full bg-blue-600 flex items-center justify-center
                      text-white text-2xl font-bold shadow-md">
        {username.charAt(0).toUpperCase()}
      </div>

      {/* Username Info */}
      <div className="flex-1">
        <div className="flex items-center gap-3">
          <h2 className="text-2xl font-semibold text-blue-900 font-mono">
            {username}
          </h2>

          {/* Copy Button */}
          <button
            onClick={handleCopy}
            title="Copy username"
            className="p-2 rounded-lg hover:bg-blue-100 transition"
          >
            {copied ? (
              <Check className="w-5 h-5 text-green-600" />
            ) : (
              <Clipboard className="w-5 h-5 text-blue-600" />
            )}
          </button>

          {/* Tooltip */}
          <span className="text-sm text-gray-500">
            (read-only)
          </span>
        </div>

        {/* Audit Info */}
        <div className="mt-1 text-sm text-gray-600 space-x-4">
          <span>
            <strong>Joined:</strong> {createdAt}
          </span>
        </div>
      </div>
    </div>
  );
};

export default UsernameHeader;
