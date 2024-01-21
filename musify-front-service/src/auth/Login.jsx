import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function login(event) {
    event.preventDefault();
    try {
      const res = await axios.post("http://localhost:8080/api/v1/auth/login", {
        username: username,
        password: password,
      });
      console.log(res.data);

      if (res.status === 200) {
        const { token, userId, role } = res.data;
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("userId", userId);
        localStorage.setItem("role", role);
        console.log(role);
        if (role === "[ROLE_ADMIN]") {
          navigate("/admin");
        } else {
          navigate("/dashboard");
        }
      }
    } catch (err) {
      alert(err);
    }
  }

  function signUp() {
    navigate("/signup");
  }

  return (
    <div className="login-container">
      <div className="login-content">
        <h2 className="login-btn">Log in</h2>
        <form className="login-form">
          <div>
            <label>Username</label>
            <input
              type="text"
              id="username"
              placeholder="Enter Name"
              value={username}
              onChange={(event) => {
                setUsername(event.target.value);
              }}
            />
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              id="password"
              placeholder="Enter Password"
              value={password}
              onChange={(event) => {
                setPassword(event.target.value);
              }}
            />
          </div>
          <button type="submit" className="btn btn-primary" onClick={login}>
            Login
          </button>
        </form>
        <div className="sign-up-container">
          <p>
            Don’t have an account?{" "}
            <button className="sign-up-btn" onClick={signUp}>
              Sign up
            </button>{" "}
          </p>
        </div>
      </div>
      <div className="login-header"></div>
    </div>
  );
}
