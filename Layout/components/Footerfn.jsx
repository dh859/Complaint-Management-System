import React from "react";

const Footer = () => {
  return (
    <footer className="bg-blue-200 border-t border-blue-300 py-4 text-center text-sm text-gray-700">
      © {new Date().getFullYear()} Complaint Management System. All rights reserved.
    </footer>
  );
};

export default Footer;
