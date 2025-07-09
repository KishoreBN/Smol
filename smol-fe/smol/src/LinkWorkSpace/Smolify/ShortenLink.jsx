import { useRef, useState } from "react";
import SmolButton from "../../Utils/Button/SmolButton";
import SmolTextBox from "../../Utils/TextBox/SmolTextBox";
import "./ShortenLink.scss";
import { shortenUrl } from "./SmolifySlice";
import CopyAllIcon from "@mui/icons-material/CopyAll";
import SmolDatePicker from "../../Utils/DateTimePicker/SmolDatePicker";
import dayjs from "dayjs";
import toast from "react-hot-toast";
import { useFormik } from "formik";

const ShortenLink = (props) => {
  const { setRefresh, ...rest } = props;
  const [shortUrl, setShortUrl] = useState(null);
  const formik = useFormik({
    enableReinitialize: true,
    initialValues: {
      longUrl: null,
      title: null,
      description: null,
      maxClick: null,
      expirationDate: null,
    },
    onSubmit: (values) => onShortenClick(values),
  });

  console.log(formik.values);

  const onShortenClick = async (values) => {
    const expirationDate = dayjs(values?.expirationDate).format(
      "YYYY-MM-DDTHH:mm:ss"
    );
    const response = await toast.promise(
      shortenUrl(
        values?.longUrl,
        values?.title,
        values?.description,
        values?.maxClick,
        values?.expirationDate
      ),
      {
        loading: "Shortening your URL...",
        success: "URL shortened successfully.",
        error: "Failed to shorten URL.",
      }
    );
    setShortUrl(import.meta.env.VITE_BASE_URL + "/" + response?.data?.shortUrl);
    setRefresh((prev) => !prev);
  };

  return (
    <div className="shorten-link">
      <div>Shorten Link</div>
      <SmolTextBox
        name="longUrl"
        label="Long URL"
        width="400px"
        value={formik?.values?.longUrl}
        onChange={formik.handleChange}
      />
      <SmolTextBox
        name="title"
        label="Title"
        width="400px"
        value={formik?.values?.title}
        onChange={formik.handleChange}
      />
      <SmolTextBox
        name="description"
        label="Description"
        width="400px"
        value={formik?.values?.description}
        onChange={formik.handleChange}
      />
      <SmolTextBox
        name="maxClick"
        label="Maximum Clicks"
        width="400px"
        value={formik?.values?.maxClick}
        onChange={formik.handleChange}
      />
      <SmolDatePicker
        name="expirationDate"
        label="Expiration Date"
        width="400px"
        disablePast={true}
        value={formik?.values?.expirationDate}
        onChange={(newValue) =>
          formik.setFieldValue("expirationDate", newValue)
        }
      />
      <SmolButton
        label="Shorten"
        sx={{ width: "400px" }}
        onClick={formik.handleSubmit}
      />
      {shortUrl && (
        <div className="short-url-section">
          <div>Short URL</div>
          <div>
            <a href={shortUrl}>{shortUrl}</a>
            <CopyAllIcon
              onClick={() => navigator.clipboard.writeText(shortUrl)}
              fontSize="small"
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default ShortenLink;
