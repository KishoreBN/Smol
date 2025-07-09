import "./SmolPaper.scss";

const SmolPaper = (props) => {
    const {children, style} = props;
    return (
        <div className="paper-wrapper" style={style}>
            <div className="paper-content">
                {children}
            </div>
        </div>
    )
}

export default SmolPaper;