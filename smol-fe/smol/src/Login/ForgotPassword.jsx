import toast from "react-hot-toast";
import axiosInstance from "../Config/AxiosConfig/AxiosInstance";
import SmolButton from "../Utils/Button/SmolButton";
import SmolTextBox from "../Utils/TextBox/SmolTextBox";
import "./ForgotPassword.scss";
import { verifyUser } from "./LoginSlice";
import { useFormik } from "formik";

const ForgotPassword = () => {
  const loginButtonStyle = {
    color: "#ffffff",
    background: "#7C3AED",
    borderRadius: "8px",
    textTransform: "none",
    padding: "10px",
    width: "400px",
  };

  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      email: "",
    },
    onSubmit: (values) => onForgotPasswordClick(values),
  });

  const onForgotPasswordClick = async (values) => {
    console.log(values);
    const response = await toast.promise(verifyUser(values?.email), {
      loading: "Sending verification link...",
      success: "Verification link sent successfully.",
      error: "Failed to send verification link. Please try again.",
    });
  };

  console.log(formik?.values);

  return (
    <div className="forgot-password-wrapper fade-in">
      <div>Forgot Password?</div>
      <SmolTextBox
        name="email"
        label="Email address"
        width="400px"
        placeholder="Enter your email address"
        value={formik?.values?.email}
        onChange={formik?.handleChange}
      />
      <SmolButton
        label="Send Verification Link"
        width="400px"
        style={loginButtonStyle}
        onClick={formik.handleSubmit}
      />
    </div>
  );
};

export default ForgotPassword;
