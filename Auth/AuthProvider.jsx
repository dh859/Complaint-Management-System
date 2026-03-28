import { useEffect, useState } from "react";
import { clearTokens, getAccessToken, setTokens } from "../API/newapi/apiClient"; 
import AuthContext from "./AuthContext";
import { jwtDecode } from "jwt-decode";
import {AuthAPI} from "../API/newapi/AuthApi";


export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const signIn = async (credentials) => {
  const tokens = await AuthAPI.login(credentials);
  setTokens(tokens.accessToken, tokens.refreshToken);

  const { sub, role } = jwtDecode(tokens.accessToken);
  setUser({ username: sub, role });
};

  const signOut = () => {
    clearTokens();
    setUser(null);
  };

  const signUp = async (credentials) => {
    const success = await AuthAPI.register(credentials);
    return success;
  };


  useEffect(() => {
    let cancelled = false;

    const initAuth = async () => {
      try {
        const token = getAccessToken();
        if (!token) return;

        const me = await AuthAPI.getMe(token);
        if (!cancelled) {
          setUser(me); 
        }
      } catch (error) {
        console.error("Auth initialization failed:", error);
        clearTokens(); 
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    initAuth();

    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <AuthContext.Provider value={{ user,signUp, signIn, signOut, loading }}>
      {children}
    </AuthContext.Provider>
  );
};


