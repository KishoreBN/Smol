import { useRef, useState } from "react";
import SmolButton from "../Utils/Button/SmolButton";
import SmolTextBox from "../Utils/TextBox/SmolTextBox";
import "./Login.scss";
import { authLogin } from "./LoginSlice";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import toast from "react-hot-toast";

const loginButtonStyle = {
  color: "#ffffff",
  background: "#7C3AED",
  borderRadius: "8px",
  textTransform: "none",
  padding: "10px",
  width: "400px",
};

/**
 * Login.jsx
 *
 * Renders the login form for the Smol app.
 *
 * Dependencies:
 * - SmolTextBox: Reusable input field component.
 * - SmolButton: Reusable button component.
 * - authLogin: Function that handles login API interaction.
 *
 * Behavior:
 * - On successful login, the JWT token is stored in localStorage under the key 'token' and user is redirected to LinkWorkSpace.
 */
const Login = () => {
  const emailRef = useRef();
  const passwordRef = useRef();
  const navigate = useNavigate();
  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      email: "",
      password: "",
    },
    onSubmit: (values) => onLoginClick(values),
  });

  const onLoginClick = async (values) => {
    const response = await toast.promise(
      authLogin(values?.email, values?.password),
      {
        loading: "Logging in...",
        success: "Login successful.",
        error: "Login failed. Please check your credentials.",
      }
    );
    const jwtToken = response?.data?.jwtToken;
    localStorage.setItem("token", jwtToken);
    navigate("/workspace");
  };

  const handleSignUp = () => {
    navigate("/signup");
  };

  const handleForgotPassword = () => {
    navigate("/verifyUser");
  };

  return (
    <div className="login-wrapper">
      <div>Welcome Back to Smol</div>
      <SmolTextBox
        name="email"
        label="Email address"
        width="400px"
        value={formik?.values?.email}
        onChange={formik.handleChange}
      />
      <SmolTextBox
        name="password"
        label="Password"
        type="password"
        width="400px"
        value={formik?.values?.password}
        onChange={formik.handleChange}
      />
      <SmolButton
        label="Login"
        style={loginButtonStyle}
        onClick={formik.handleSubmit}
      />
      <div className="login-footer">
        <div onClick={handleSignUp}>New User? SignUp</div>
        <div onClick={handleForgotPassword}>Forgot Password?</div>
      </div>
    </div>
  );
};

export default Login;
