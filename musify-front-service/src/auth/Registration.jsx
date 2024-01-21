import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Registration.css";

export default function Registration() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function save(event) {
    event.preventDefault();
    try {
      await axios
        .post("http://localhost:8080/api/v1/auth/signup", {
          email: email,
          username: username,
          password: password,
        })
        .then((res) => {
          if (res.status === 200) {
            const { token, userId } = res.data;
            localStorage.setItem("jwtToken", token);
            localStorage.setItem("userId", userId);
            navigate("/dashboard");
          }
        });
    } catch (error) {
      if (error.response) {
        console.error("Error response from server:", error.response.data);
        alert(`Error: ${error.response.data}`);
      }
    }
  }

  function login() {
    navigate("/login");
  }

  return (
    <div className="register-container">
      <div className="register-content">
        <h2 className="register-btn">Sign Up</h2>
        <form className="register-form">
          <div>
            <label>Email</label>
            <input
              type="Email"
              id="email"
              placeholder="Enter Email"
              value={email}
              onChange={(event) => {
                setEmail(event.target.value);
              }}
            />
          </div>
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
              placeholder="Enter password"
              value={password}
              onChange={(event) => {
                setPassword(event.target.value);
              }}
            />
          </div>
          <button type="submit" className="btn btn-primary mt-4" onClick={save}>
            Save
          </button>
        </form>
        <div className="login-redirect-container">
          <p>
            Already have an account?{" "}
            <button className="login-redirect-btn" onClick={login}>
              Login
            </button>{" "}
          </p>
        </div>
      </div>
      <div className="register-header"></div>
    </div>
  );
}
