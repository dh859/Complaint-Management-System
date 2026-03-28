import React from "react";

const About = () => {
  return (
    <div className=" px-6 md:px-10 bg-gray-50 min-h-screen">
      <div className="max-w-4xl mx-auto bg-white rounded-xl shadow-md p-4">
        {/* Title */}
        <h1 className="text-3xl font-bold text-blue-900 mb-4">
          About Complaint Management System
        </h1>

        {/* Intro */}
        <p className="text-gray-700 leading-relaxed mb-6">
          The{" "}
          <span className="font-semibold">
            Complaint Management System (CMS)
          </span>
          is designed to simplify the process of registering, tracking, and
          resolving complaints efficiently. It provides transparency,
          accountability, and faster resolution for users and administrators.
        </p>

        {/* Features */}
        <h2 className="text-xl font-semibold text-blue-800 mb-3">
          Key Features
        </h2>
        <ul className="list-disc list-inside text-gray-700 space-y-2 mb-6">
          <li>Easy complaint registration with file attachments</li>
          <li>Real-time complaint status tracking</li>
          <li>Role-based dashboards (User, Agent, Manager, Admin)</li>
          <li>Notifications and reporting system</li>
          <li>Secure and scalable architecture</li>
        </ul>

        {/* Roles */}
        <h2 className="text-xl font-semibold text-blue-800 mb-3">User Roles</h2>
        <p className="text-gray-700 mb-6">
          CMS supports multiple roles such as <strong>Users</strong>,{" "}
          <strong>Agents</strong>,<strong>Managers</strong>, and{" "}
          <strong>Admins</strong>, each with a dedicated dashboard to ensure
          smooth complaint handling and monitoring.
        </p>

        {/* Footer */}
        <div className="border-t pt-4 text-sm text-gray-500">
          © {new Date().getFullYear()} Complaint Management System. Built for
          efficient issue resolution.
        </div>
      </div>
    </div>
  );
};

export default About;
