import { useNavigate } from "react-router-dom";
import "./LoginBtn.css";

export default function LoginBtn() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/login");
  };

  return (
    <button className="loginBtn" onClick={handleClick}>
      Log out
    </button>
  );
}
