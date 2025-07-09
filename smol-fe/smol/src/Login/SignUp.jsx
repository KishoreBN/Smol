import { useRef } from "react";
import SmolButton from "../Utils/Button/SmolButton";
import SmolTextBox from "../Utils/TextBox/SmolTextBox";
import "./SignUp.scss";
import { registerUser } from "./LoginSlice";
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
 * SignUp.jsx
 *
 * Minimal signup form for user registration in the Smol application.
 *
 * Dependencies:
 * - SmolTextBox: Reusable input field component.
 * - SmolButton: Reusable button component.
 * - registerUser: Function that sends signup data to the backend.
 *
 * Styling:
 * - The component is styled via the local SCSS file `SignUp.scss`.
 *
 * Behavior:
 * - On clicking register, user details (username, email, password) are submitted to the backend.
 * - Registration response is handled via a promise; currently placeholder for snackbar/redirect.
 */
const SignUp = (props) => {
  const { setValue, ...rest } = props;
  const navigate = useNavigate();
  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      username: "",
      email: "",
      password: "",
    },
    onSubmit: (values) => onRegisterClick(values),
  });

  const onRegisterClick = async (values) => {
    const response = await toast.promise(registerUser(values?.username, values?.email, values?.password), {
      loading : "Registering user...",
      success: "User registered successfully. Please login.",
      error: "Failed to register user. Please try again.",
    });
    navigate("/login");
  };

  const handleLogin = () => {
    navigate("/login");
  };

  return (
    <div className="signup-wrapper">
      <div>Welcome to Smol</div>
      <SmolTextBox
        name="username"
        label="Username"
        width="400px"
        value={formik?.username}
        onChange={formik.handleChange}
      />
      <SmolTextBox
        name="email"
        label="Email address"
        width="400px"
        value={formik?.email}
        onChange={formik?.handleChange}
      />
      <SmolTextBox
        name="password"
        label="Password"
        type="password"
        width="400px"
        value={formik?.password}
        onChange={formik.handleChange}
      />
      <SmolButton
        label="Register"
        style={loginButtonStyle}
        onClick={formik.handleSubmit}
      />
      <div onClick={handleLogin}>Already have an account? Login</div>
    </div>
  );
};

export default SignUp;
