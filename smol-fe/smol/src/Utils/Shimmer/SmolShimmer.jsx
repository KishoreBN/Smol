import { Skeleton } from "@mui/material";

const SmolShimmer = (props) => {
    const {style, ...rest} = props;
  return (
    <Skeleton
      {...props}
      style={style}
      sx={{
        transform: "none",
        bgcolor: "#1f1f1f", // base color
        "&::after": {
          background: `linear-gradient(90deg, transparent, rgba(255,255,255,0.1), transparent)`, // shimmer color
        },
      }}
    />
  );
};

export default SmolShimmer;
