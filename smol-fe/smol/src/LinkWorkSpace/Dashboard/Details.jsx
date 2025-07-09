import { useEffect, useState } from "react";
import SmolLineChart from "../../Utils/Chart/LineChart/SmolLineChart";
import "./Details.scss";
import { deleteUrl, updateUrl, urlAnalytics } from "./DashboardSlice";
import dayjs from "dayjs";
import EditTwoToneIcon from "@mui/icons-material/EditTwoTone";
import DeleteOutlineTwoToneIcon from "@mui/icons-material/DeleteOutlineTwoTone";
import SmolTextBox from "../../Utils/TextBox/SmolTextBox";
import SmolDatePicker from "../../Utils/DateTimePicker/SmolDatePicker";
import SmolButton from "../../Utils/Button/SmolButton";
import { useFormik } from "formik";
import { shortenUrl } from "../Smolify/SmolifySlice";
import toast from "react-hot-toast";

/*
1. Displays and allows editing of URL details and analytics for a shortened link.
2. Dependencies: React, Formik, dayjs, MUI icons, Smol UI components, react-hot-toast, DashboardSlice functions.
3. After editing or deleting, triggers a refresh in the parent to update the displayed data.
*/
// Displays and allows editing of URL details and analytics.
const Details = (props) => {
  const { data, setRefresh, ...rest } = props;
  const defaultGraphData = { xData: [], yData: [] };
  const [graphData, setGraphData] = useState(defaultGraphData);
  const [editMode, setEditMode] = useState(false);
  console.log(data);
  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      id: data?.id,
      expirationDate: data?.expirationDate,
      maxClick: data?.maxClick,
      title: data?.title,
      description: data?.description,
      clickCount: data?.clickCount,
      shortUrl: data?.shortUrl,
      fullShortUrl: data?.fullShortUrl,
      originalUrl: data?.originalUrl,
      createdDate: data?.createdDate,
    },
    onSubmit: (values) => updateUrlDetails(values),
  });

  const updateUrlDetails = async (values) => {
    const body = {
      id: values?.id,
      expirationDate: values?.expirationDate ? dayjs(values?.expirationDate).format(
        "YYYY-MM-DDTHH:mm:ss"
      ) : null,
      maxClick: values?.maxClick,
      longUrl: values?.originalUrl,
    };
    const response = await toast.promise(updateUrl(body), {
      loading: "Updating URL details.",
      success: "URL details updated successfully.",
      error: "Failed to updated URL details.",
    });
    setRefresh((prev) => !prev);
  };

  useEffect(() => {
    const start = dayjs().subtract(10, "day").format("YYYY-MM-DDTHH:mm:ss"),
      end = dayjs().format("YYYY-MM-DDTHH:mm:ss");
    urlAnalytics(data?.shortUrl, start, end).then((response) => {
      const data = response?.data;
      const xData = data?.map((item) => dayjs(item?.date).format("MMM D"));
      const yData = data?.map((item) => item?.count);
      setGraphData({ xData: xData, yData: yData });
    }).catch((error) =>{
      toast.error("Something went wrong.");
    });
    return () => {
      setEditMode(false);
    };
  }, [data]);

  const onEditClick = () => {
    setEditMode(true);
  };

  const onCancelClick = () => {
    formik.handleReset();
    setEditMode(false);
  };

  const onDeleteClick = async () => {
    const response = await toast.promise(deleteUrl(formik?.values?.id), {
      loading : "Deleting URL.",
      success : "URL deleted successfully.",
      error : "Failed to delete URL."
    })
    setRefresh((prev) => !prev);
  };

  return (
    <div className="details-wrapper">
      <div className="d-header">
        <div>
          <div>{formik?.values?.title}</div>
          <div>
            <EditTwoToneIcon sx={{ color: "#95e093" }} onClick={onEditClick} />
            <DeleteOutlineTwoToneIcon
              sx={{ color: "#f67373" }}
              onClick={onDeleteClick}
            />
          </div>
        </div>
        <div>{formik?.values?.description}</div>
      </div>
      <div className="details-container">
        <div className="click">
          <div>{formik?.values?.clickCount} Clicks</div>
        </div>
        <div className="details-graph">
          <div className="details">
            <div>
              <div>Short Url</div>
              <div>{formik?.values?.fullShortUrl}</div>
            </div>
            <div>
              <div>Long Url</div>
              {editMode ? (
                <SmolTextBox
                  name="originalUrl"
                  width="100%"
                  value={formik?.values?.originalUrl}
                  onChange={formik.handleChange}
                />
              ) : (
                <div>{formik?.values?.originalUrl}</div>
              )}
            </div>
            <div>
              <div>Created Date</div>
              <div>{formik?.values?.createdDate}</div>
            </div>
            <div>
              <div>Expiration Date</div>
              {editMode ? (
                <SmolDatePicker
                  name="expirationDate"
                  width="100%"
                  value={dayjs(formik?.values?.expirationDate)}
                  disablePast={true}
                  onChange={(newValue) =>
                    formik.setFieldValue(
                      "expirationDate",
                      newValue?.toISOString()
                    )
                  }
                />
              ) : (
                <div>
                  {formik?.values?.expirationDate
                    ? formik?.values?.expirationDate
                    : "--"}
                </div>
              )}
            </div>
            <div>
              <div>Maximum Clicks</div>
              {editMode ? (
                <SmolTextBox
                  name="maxClick"
                  width="100%"
                  value={formik?.values?.maxClick}
                  onChange={formik.handleChange}
                />
              ) : (
                <div>
                  {formik?.values?.maxClick ? formik?.values?.maxClick : "--"}
                </div>
              )}
            </div>
          </div>
          <div className="graph">
            <SmolLineChart xAxis={graphData?.xData} yAxis={graphData?.yData} />
          </div>
        </div>
        {editMode && (
          <div className="actions">
            <SmolButton label="Cancel" onClick={onCancelClick} />
            <SmolButton label="Save" onClick={formik.handleSubmit} />
          </div>
        )}
      </div>
    </div>
  );
};

export default Details;
