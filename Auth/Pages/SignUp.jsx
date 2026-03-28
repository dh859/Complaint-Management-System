import { useState } from "react";
import { useAuth } from "../UseAuth";

const SignUp = ({ setActivePage }) => {
  const { signUp } = useAuth();

  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPass, setConfirmPass] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPass) {
      alert("Passwords do not match");
      return;
    }

    const success = await signUp({
      fullname: name,
      username,
      email,
      password,
    });

    if (success) {
      setActivePage("MainDasboard");
    }
  };

  return (
    <div className="min-h-screen  flex items-center justify-center bg-blue-50 px-4">
      <div className="w-full m-4 max-w-md bg-white rounded-2xl shadow-lg border border-blue-100 p-8">
        
        {/* Header */}
        <h2 className="text-3xl font-bold text-blue-900 text-center mb-2">
          Create Account
        </h2>
        <p className="text-sm text-gray-600 text-center mb-8">
          Join the Complaint Management System
        </p>

        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-5">
          
          <div>
            <label className="form-label">Full Name</label>
            <input
              className="form-input"
              placeholder="Your full name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div>
            <label className="form-label">Username</label>
            <input
              className="form-input"
              placeholder="Choose a username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div>
            <label className="form-label">Email Address</label>
            <input
              type="email"
              className="form-input"
              placeholder="you@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div>
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-input"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div>
            <label className="form-label">Confirm Password</label>
            <input
              type="password"
              className="form-input"
              placeholder="••••••••"
              value={confirmPass}
              onChange={(e) => setConfirmPass(e.target.value)}
              required
            />
          </div>

          {/* Submit */}
          <button
            type="submit"
            className="w-full mt-4 bg-blue-600 text-white py-3 rounded-xl
                       font-semibold shadow hover:bg-blue-700 transition"
          >
            Sign Up
          </button>
        </form>

        {/* Footer */}
        <p className="text-sm text-center text-gray-600 mt-6">
          Already have an account?{" "}
          <span
            onClick={() => setActivePage("SignIn")}
            className="text-blue-600 font-semibold cursor-pointer hover:underline"
          >
            Sign In
          </span>
        </p>
      </div>
    </div>
  );
};

export default SignUp;
