import "./InfoCards.scss";
import SmolPaper from "../Utils/Paper/SmolPaper";
import SmolButton from "../Utils/Button/SmolButton";
import InsightsIcon from '@mui/icons-material/Insights';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import LinkOffIcon from '@mui/icons-material/LinkOff';
import DashboardIcon from '@mui/icons-material/Dashboard';
import QrCodeScannerIcon from '@mui/icons-material/QrCodeScanner';
import SwitchAccessShortcutIcon from '@mui/icons-material/SwitchAccessShortcut';
import ContentCutIcon from '@mui/icons-material/ContentCut';
import { useNavigate } from "react-router-dom";

const GetStartedCard = () => {
    const navigate = useNavigate();
    const onShortenURLClick = () =>{
        navigate("/login");
    }
    return (
        <div className="get-started-card-wrapper">
            <div>
                Shorten your URLs
            </div>
            <div>
                Experience the ease of managing your links with Smol. Our service provides a seamless way to shorten, share, 
                and track your URLs with just a few clicks.
            </div>
            <div>
                <SmolButton 
                    label = {"Shorten URL"}
                    style = {{
                        backgroundColor : "#7C3AED",
                        color : "#ffffff",
                        textTransform: "none",
                        borderRadius: "8px",
                        fontSize: "16px",
                        fontWeight: "400"
                    }}
                    onClick={onShortenURLClick}
                />
            </div>
        </div>
    )
}

const infoCardDetial = [
    {
        title : "Shorten URLs Instantly",
        icon : <ContentCutIcon sx={{color: "#c084fc"}}/>,
        description : "Convert long, messy links into clean and compact URLs in a single click."
    },
    {
        title : "Track Every Click",
        icon : <InsightsIcon sx={{color: "#fdb23e"}}/>,
        description : "Gain insight into how your links are performing with real-time click analytics."
    },
    {
        title : "Secure and Private",
        icon : <LockOpenIcon sx={{color: "#ffef8d"}}/>,
        description : "All your links are safe and protected. Only you can manage or see them."
    },
    {
        title : "Auto Expiry & One-Time Links",
        icon : <LinkOffIcon sx={{color: "#83b5cb"}}/>,
        description : "Set expiration dates or create links that expire after a single use."
    },
    {
        title : "User Dashboard",
        icon : <DashboardIcon sx={{color: "#f05057"}}/>,
        description : "View, manage, and track all your URLs from one simple, elegant dashboard."
    },
    {
        title : "QR Code Support",
        icon : <QrCodeScannerIcon sx={{color: "#81b6e7"}}/>,
        description : "Instantly generate QR codes for every short URL and share them offline."
    }
]

const InfoCard = () => {
    return (
        <div className="info-card-wrapper">
            {
                infoCardDetial?.map((item) => {
                    return (   
                        <SmolPaper >
                        <div className="card-wrapper">
                            <div>
                                {item?.icon}
                                <div>{item?.title}</div>
                            </div>
                            <div>{item?.description}</div>
                        </div>
                        </SmolPaper>
                    );
                })
            }
        </div>
    )
};

const InfoCards = () => {
    return (
        <div className="info-wrapper">
            <SmolPaper 
                children={<GetStartedCard />}
            />
            <InfoCard />
        </div>
    )
};

export default InfoCards;