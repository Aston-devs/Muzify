import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "../auth/Registration.jsx";
import Login from "../auth/Login.jsx";
import Admin from "../admin_page/AdminPage.jsx";

import Home from "./Home";

export default function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/dashboard" element={<Home />} />
          <Route path="/signup" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/admin" element={<Admin />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}
