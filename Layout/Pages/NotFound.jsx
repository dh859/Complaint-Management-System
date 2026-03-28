import React from "react";

const NotFound = () => {
  const goHome = () => {
    window.location.href = "/";
  };

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Content */}
      <div className="flex flex-1 items-center justify-center">
        <div className="bg-white p-10 rounded-lg shadow text-center max-w-md">
          <h1 className="text-6xl font-bold text-blue-600">404</h1>

          <h2 className="text-2xl font-semibold mt-4">Page Not Found</h2>

          <p className="text-gray-600 mt-3">
            The page you are trying to access does not exist.
          </p>

          <button
            onClick={goHome}
            className="mt-6 bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 transition font-semibold"
          >
            Go to Home
          </button>
        </div>
      </div>
    </div>
  );
};

export default NotFound;
