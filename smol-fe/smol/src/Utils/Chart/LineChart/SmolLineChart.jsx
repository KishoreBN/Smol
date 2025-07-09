import * as React from "react";
import { LineChart } from "@mui/x-charts/LineChart";

const SmolLineChart = (props) => {
  const { xAxis, yAxis, ...rest } = props;
  return (
    <LineChart
      xAxis={[
        {
          data: xAxis,
          tickLabelStyle: { fill: "#ffffff" },
          line: { stroke: "#ffffff", strokeWidth: 1 },
          scaleType: "band",
          label: "Day",
          labelStyle: { fill: "#ffffff" }
        },
      ]}
      yAxis={[
        {
          tickLabelStyle: { fill: "#ffffff" },
          line: { stroke: "#ffffff", strokeWidth: 1 },
          label: "Click count",
          labelStyle: { fill: "#ffffff" }
        },
      ]}
      series={[
        {
          data: yAxis,
          color: "#7C3AED",
        },
      ]}
      height={300}
      sx={{
        "& .MuiChartsAxis-line" : {
            stroke: "white !important"
        },
        "& .MuiChartsAxis-tick" : {
            stroke: "white !important"
        }
      }}
      {...rest}
    />
  );
};

export default SmolLineChart;
