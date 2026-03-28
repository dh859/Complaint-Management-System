import React from "react";
import "./Home.css";

const Home = ({ setActivePage }) => {
  return (
    <div className="home-container">
      {/* Hero Section */}
      <div className="hero fade-in">
        <h1>Complaint Management System</h1>
        <p>
          A centralized platform to register, track, and resolve complaints
          efficiently with transparency and accountability.
        </p>

        <div className="hero-buttons">
          <button
            className="primary-btn"
            onClick={() => setActivePage("SignIn")}
          >
            Raise a Complaint
          </button>
        </div>
      </div>

      {/* Features */}
      <div className="features">
        <Feature
          title="Easy Complaint Registration"
          desc="Submit complaints quickly with proper categorization."
        />
        <Feature
          title="Real-time Tracking"
          desc="Track complaint status and updates in real-time."
        />
        <Feature
          title="Role-based Dashboards"
          desc="Separate dashboards for Users, Agents, Managers, and Admins."
        />
      </div>
    </div>
  );
};

const Feature = ({ title, desc }) => {
  return (
    <div className="feature-card slide-up">
      <h3>{title}</h3>
      <p>{desc}</p>
    </div>
  );
};

export default Home;
