import toast from "react-hot-toast";
import Paper from "../Utils/Paper/SmolPaper";
import Footer from "./Footer";
import Header from "./Header";
import InfoCards from "./InfoCards";
import "./Welcome.scss";

const Welcome = () => {
  return (
    <div className="welcome-wrapper">
      <div className="header">
        <Header />
      </div>
      <div className="body">
        <InfoCards />
      </div>
      <div className="footer">
        <Footer />
      </div>
    </div>
  );
};

export default Welcome;
