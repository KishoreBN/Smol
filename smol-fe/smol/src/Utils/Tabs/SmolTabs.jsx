import { Box } from "@mui/material";
import { useState } from "react";
import { TabContext, TabList, TabPanel } from "@mui/lab";
import { Tab } from "@mui/material";



const SmolTabs = (props) => {
  const {value, tabs, tabPanels, onChange, defaultSelection, setValue, centered, ...rest } = props;
  const handleChange = (event, newValue) => {
    setValue(newValue);
    if (onChange) onChange(newValue, event);
  };

  return (
    <Box sx={{ width: "100%", typography: "body1", display: "grid", gridTemplateRows: "auto 1fr", height: "100%" }}>
      <TabContext value={value}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList
            onChange={handleChange}
            textColor="primary"
            indicatorColor="primary"
            sx={{
                '& .MuiTabs-flexContainer': {
                  justifyContent: 'center',
                },
                '& .MuiTab-root': {
                  color: '#ffffff !important',
                  fontWeight: 600,
                },
                '& .Mui-selected': {
                  color: '#7C3AED !important',
                },
                '& .MuiTabs-indicator': {
                  backgroundColor: '#7C3AED !important',
                },
            }}
            value={value}
            centered={centered}
            {...rest}
          >
            {tabs?.map((item, index) => {
              return <Tab id={index} label={item?.label} value={item?.value} />;
            })}
          </TabList>
        </Box>
        {tabPanels?.map((item, index) => {
          return <TabPanel value={item?.value}>{item?.component}</TabPanel>;
        })}
      </TabContext>
    </Box>
  );
};

export default SmolTabs;
