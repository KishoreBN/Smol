import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers';

const SmolDatePicker = (props) => {
    const { label, width, disablePast, ...rest } = props;

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker 
                label={label} 
                sx={{
                    borderRadius: '3px',
                    border: '2px solid #343232',
                    width : width ? width : '100px',
                    '& .MuiInputBase-root': {
                      color: '#ffffff',
                      borderColor: '#343232',
                    },
                    '& .MuiOutlinedInput-notchedOutline': {
                      borderColor: '#343232',
                    },
                    '& .MuiSvgIcon-root': {
                      color: '#ffffff',
                    },
                    '& .MuiInputLabel-root': {
                      color: '#ffffff',
                    },
                    '& .Mui-focused': {
                      color: '#ffffff !important'
                    },
                    '& .MuiPickersSectionList-section': {
                      color: '#ffffff'
                    },
                    '& .MuiPickersOutlinedInput-notchedOutline': {
                      border: '#343232 !important'
                    }
                }}
                disablePast={disablePast ? disablePast : false}
                {...rest} 
            />
        </LocalizationProvider>
    );
};

export default SmolDatePicker;
