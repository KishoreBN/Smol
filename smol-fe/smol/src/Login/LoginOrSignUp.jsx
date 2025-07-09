import SmolTabs from "../Utils/Tabs/SmolTabs";
import Login from "./Login";
import SignUp from "./SignUp";
import "./LoginOrSignUp.scss";
import { useState } from "react";

const LoginOrSignUp = () => {
  const [value, setValue] = useState(1);
  const tabs = [
    {
      label: "Login",
      value: 1,
    },
    {
      label: "Signup",
      value: 2,
    },
  ];
  const tabPanels = [
    {
      value: 1,
      component: <Login />,
    },
    {
      value: 2,
      component: <SignUp />,
    },
  ];

  return (
    <div className="login-or-signup-wrapper">
      <SmolTabs tabs={tabs} tabPanels={tabPanels} value={value} setValue={setValue} centered={true}/>
    </div>
  );
};

export default LoginOrSignUp;
