import toast from "react-hot-toast";
import axiosInstance from "../Config/AxiosConfig/AxiosInstance";
import SmolButton from "../Utils/Button/SmolButton";
import SmolTextBox from "../Utils/TextBox/SmolTextBox";
import "./ForgotPassword.scss";
import { passwordReset, verifyUser } from "./LoginSlice";
import { useFormik } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const loginButtonStyle = {
  color: "#ffffff",
  background: "#7C3AED",
  borderRadius: "8px",
  textTransform: "none",
  padding: "10px",
  width: "400px",
};

const ForgotPassword = () => {
  const [otpPhase, setOtpPhase] = useState(false);
  const navigate = useNavigate();

  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      email: "",
      otp: "",
      password: "",
    },
    onSubmit: (values) => onPasswordReset(values),
  });

  const onForgotPasswordClick = async (values) => {
    const response = await toast
      .promise(verifyUser(values?.email), {
        loading: "Sending verification link...",
        success: "Verification link sent successfully.",
        error: (err) =>
          err?.response?.data?.message
            ? err?.response?.data?.message
            : "Something went wrong.",
      })
      .then(() => {
        setOtpPhase(true);
      });
  };

  const onPasswordReset = async (values) => {
    const response = await toast.promise(
      passwordReset(values?.email, values?.otp, values?.password),
      {
        loading: "Resetting password...",
        success:
          "Password reset successful! You can now log in with your new password",
        error: (err) =>
          err?.response?.data?.message
            ? err?.response?.data?.message
            : "Something went wrong.",
      }
    ).then(()=> {
        navigate("/login");
    });
  };

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
      {!otpPhase && (
        <SmolButton
          label="Send Verification Link"
          width="400px"
          style={loginButtonStyle}
          onClick={() => onForgotPasswordClick(formik?.values)}
        />
      )}
      {otpPhase && (
        <>
          <SmolTextBox
            name="otp"
            label="OTP"
            width="400px"
            placeholder="Enter the OTP"
            className="fade-in"
            value={formik?.values?.otp}
            onChange={formik?.handleChange}
          />
          <SmolTextBox
            name="password"
            label="Password"
            width="400px"
            value={formik?.values?.password}
            onChange={formik?.handleChange}
            className="fade-in"
          />
          <SmolButton
            label="Verify OTP"
            width="400px"
            style={loginButtonStyle}
            onClick={formik.handleSubmit}
            className="fade-in"
          />
        </>
      )}
    </div>
  );
};

export default ForgotPassword;
