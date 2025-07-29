import SmolButton from "../Utils/Button/SmolButton";
import Footer from "../Welcome/Footer";
import Dashboard from "./Dashboard/Dashboard";
import Smolify from "./Smolify/Smolify";
import "./LinkWorkSpace.scss";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import { Outlet, useNavigate } from "react-router-dom";
import SmolHeaderTabs from "../Utils/Tabs/SmolHeaderTabs";
import Logo from "../assets/Logo.png";

const LinkWorkSpace = () => {
  const navigate = useNavigate();
  const options = [
    {
      label: "Create Link",
      value: 1,
      onClick: () => navigate("/workspace/createLink"),
    },
    {
      label: "Dashboard",
      value: 2,
      onClick: () => navigate("/workspace/dashboard"),
    },
  ];

  const onLogoutClick = () => {
    localStorage.removeItem('token');
    navigate("/");
  }

  return (
    <div className="workspace-wrapper">
      <div className="header">
        <div>
          <img src={Logo} style={{height: "100%"}}/>
        </div>
        <div>
          <SmolHeaderTabs
            options={options}
            defaultSelection={options[0]}
          />
          <SmolButton label="Logout" onClick={onLogoutClick}/>
        </div>
      </div>
      <div className="content">
        <Outlet />
      </div>
      <Footer />
    </div>
  );
};

export default LinkWorkSpace;
