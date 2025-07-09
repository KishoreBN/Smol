import { Button } from "@mui/material";
import "./Header.scss";
import SmolButton from "../Utils/Button/SmolButton";
import InsertLinkIcon from '@mui/icons-material/InsertLink';
import { useNavigate } from "react-router-dom";

const Header = () => {
    const navigate = useNavigate();
    const getStartedStyle = {
        color : "#ffffff",
        background : "#7C3AED",
        borderRadius : "8px",
        textTransform : "none",
        padding : "10px"
    }
    const onGetStartedClick = () => {
        navigate("/login");
    }
    return (
        <div className="header-wrapper">
            <div className="title">
                <InsertLinkIcon />
                <div>Smol</div>
            </div>
            <SmolButton 
                label={"Get Started"}
                style={getStartedStyle}
                onClick={onGetStartedClick}
            />
        </div>
    )
};

export default Header;