import "./Footer.scss";
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import InstagramIcon from '@mui/icons-material/Instagram';

const Footer = () => {
    return (
        <div className="footer-wrapper">
            <div className="about">
                <div className="me">
                    <div>Created By</div>
                    <div>Kishore B N</div>
                </div>
                <div>
                    <LinkedInIcon sx={{color : "#58bdf2"}}/>
                    <InstagramIcon sx={{color : "#e15987"}}/>
                </div>
            </div>
            <div>
                <div className="contact-us">
                    <div>Contact Us</div>
                    <div>email : primeeng.tech@gmail.com</div>
                    <div>© 2025 Smol. All rights reserved.</div>
                </div>
            </div>
        </div>
    )
};

export default Footer;