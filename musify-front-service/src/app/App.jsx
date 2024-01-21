import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "../auth/Registration.jsx";
import Login from "../auth/Login.jsx";

import Home from "./Home";

export default function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/dashboard" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}
