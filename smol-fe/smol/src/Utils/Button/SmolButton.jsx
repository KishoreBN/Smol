import { Button } from "@mui/material";

const SmolButton = (props) => {
    const {onClick, style, label, ...rest} = props;
    const defaultStyle = {
        color : "#ffffff",
        background : "#7C3AED",
        borderRadius : "8px",
        textTransform : "none",
        padding : "10px"
    }
    return (
        <div>
            <Button 
                onClick={onClick} 
                style={style ? style : defaultStyle}
                {...rest}
            >
                {label}
            </Button>
        </div>
    )
};

export default SmolButton;