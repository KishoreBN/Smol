import { TextField } from "@mui/material";

const SmolTextBox = (props) => {
  const { width, ...rest } = props;
  return (
    <TextField
      {...rest}
      sx={{
        input: { color: "#ffffff" }, 
        label: { color: "#ffffff" }, 
        width: width ? width : "100px",
        "& .MuiInputBase-root": {
          color: "#ffffff", 
        },
        "& .MuiOutlinedInput-root": {
          "& fieldset": {
            borderColor: "#343232",
          },
          "&:hover fieldset": {
            borderColor: "#343232",
          },
          "&.Mui-focused fieldset": {
            borderColor: "#343232",
          },
        },
        "& .MuiInputLabel-root": {
          color: "#ffffff",
        },
        "& .MuiInputLabel-root.Mui-focused": {
          color: "#ffffff",
        },
      }}
    />
  );
};

export default SmolTextBox;
