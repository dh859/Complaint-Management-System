import React, { useState } from "react";
import Home from "./Layout/Pages/Home";
import SignIn from "./Auth/Pages/SignIn";
import SignUp from "./Auth/Pages/SignUp";
import MainDashboard from "./Layout/Pages/MainDasboard";
import Notification from "./Layout/Pages/Notification";
import Report from "./Layout/Pages/Report";
import MyComplaints from "./Layout/Pages/MyComplaints";
import About from "./Layout/Pages/About";
import Contact from "./Layout/Pages/Contact";
import NotFound from "./Layout/Pages/NotFound";
import Sidebar from "./Layout/components/Sidebar";
import NavbarComponent from "./Layout/components/NavbarComponent";
import Footerfn from "./Layout/components/Footerfn";
import UserProfile from "./Layout/components/UserProfile";

function App() {
  const [open, setOpen] = useState(false);
  const [activePage, setActivePage] = useState("Home");
  const hideSidebarPages = ["SignIn", "SignUp"];

  const renderPage = () => {
    switch (activePage) {
      case "Home":
        return <Home setActivePage={setActivePage} />;
      case "SignIn":
        return <SignIn setActivePage={setActivePage} />;

      case "SignUp":
        return <SignUp setActivePage={setActivePage} />;

      case "MainDashboard":
        return <MainDashboard setActivePage={setActivePage}/>;
      case "Notifications":
        return <Notification />;
      case "Reports":
        return <Report />;
      case "MyComplaints":
        return <MyComplaints />;
      case "Profile":
        return <UserProfile />;
      case "About":
        return <About />;
      case "Contact":
        return <Contact />;
      
      default:
        return <NotFound />;
    }
  };

  return (
    <div className="flex h-screen">
      {!hideSidebarPages.includes(activePage) && (
        <Sidebar
          activePage={activePage}
          setActivePage={setActivePage}
          open={open}
          setOpen={setOpen}
        />
      )}

      {/* Right Side */}
      <div className="flex-1 flex flex-col">
        <NavbarComponent
          activePage={activePage}
          setActivePage={setActivePage}
        />
        <main
          className={`
    flex-1 bg-gray-100 mt-18 p-4
    transition-all duration-300 ease-in-out
    ${open ? "ml-56" : "ml-16"}
  `}
        >
          {renderPage()}
        </main>
        <Footerfn />
      </div>
    </div>
  );
}

export default App;
