import { useContext } from "react";
import AuthContext from "../../Auth/AuthContext";

const NavbarComponent = ({activePage,setActivePage}) => {
  const { user, signOut, loading } = useContext(AuthContext);


  const pages = [
    { key: "Home", label: "Home" },
    { key: "About", label: "About" },
    { key: "Contact", label: "Contact" },
  ];

  return (
    <nav className="fixed top-0 left-0 w-full z-20 bg-blue-200 border-b border-blue-300">
      <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
        
        {/* Logo */}
        <div
          onClick={() => setActivePage("/Home")}
          className="flex items-center gap-3 cursor-pointer"
        >
          <div className="h-8 w-8 bg-blue-700 text-white rounded-full flex items-center justify-center font-bold">
            C
          </div>
          <span className="text-xl font-semibold text-blue-900">CMS</span>
        </div>

        <div className="flex items-center gap-8">
          
          <ul className="hidden md:flex items-center gap-8">
            {pages.map((page) => (
              <li key={page.key}>
                <button
                  onClick={() => {setActivePage(page.key)}}
                  className={`font-medium transition
                    ${
                      activePage === page.key
                        ? "text-blue-900 border-b-2 border-blue-700 pb-1"
                        : "text-gray-700 hover:text-blue-700"
                    }`}
                >
                  {page.label}
                </button>
              </li>
            ))}
          </ul>

          
          {loading ? (
            
            <div className="h-9 w-32 bg-blue-300 rounded animate-pulse" />
          ) : !user ? (
            <button
              onClick={() => setActivePage("SignUp")}
              className="px-4 py-2 bg-blue-700 text-white rounded-md font-medium hover:bg-blue-800 transition"
            >
              Sign Up
            </button>
          ) : (
            <div className="flex items-center gap-4">
              <span className="text-sm text-gray-700">
                Hi, <b>{user.username || "User"}</b>
              </span>

              <button
                onClick={() => {
                  signOut();
                  setActivePage("Home");
                }}
                className="px-4 py-2 bg-blue-700 text-white rounded-md font-medium hover:bg-blue-800 transition"
              >
                Logout
              </button>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavbarComponent;
