import { useEffect, useState } from "react";
import SmolSimpleTable from "../../Utils/Table/SmolSimpleTable";
import "./TopLink.scss";
import CopyAllIcon from "@mui/icons-material/CopyAll";
import { userUrls } from "./SmolifySlice";
import toast from "react-hot-toast";
import { Skeleton } from "@mui/material";
import SmolShimmer from "../../Utils/Shimmer/SmolShimmer";

const TopLink = (props) => {
  const { refresh, ...rest } = props;
  const [loading, setLoading] = useState(true);
  const [topLinks, setTopLinks] = useState([]);

  useEffect(() => {
    setLoading(true);
    userUrls()
      .then((response) => {
        setLoading(false);
        setTopLinks(
          response?.data?.map((item) => ({
            ...item,
            shortUrl: import.meta.env.VITE_BASE_URL + "/" + item?.shortUrl,
          }))
        );
      })
      .catch((error) => {
        setLoading(false);
        toast.error("Something went wrong.");
      });
  }, [refresh]);

  const columns = [
    { field: "title", headerName: "Title", flex: 0.3 },
    {
      field: "shortUrl",
      headerName: "Short URL",
      flex: 0.3,
      renderCell: (params) => (
        <div
          style={{ display: "flex", alignItems: "center", gap: "8px" }}
          className="row-with-copy"
        >
          <span>{params.value}</span>
          <div className="copy-icon">
            <CopyAllIcon
              onClick={() => navigator.clipboard.writeText(params.value)}
              fontSize="small"
            />
          </div>
        </div>
      ),
    },
    { field: "originalUrl", headerName: "Long URL", flex: 0.4 },
  ];

  return (
    <div className="top-link">
      <div>Top Links</div>
      {!loading ? (
        <div className="fade-in">
          <SmolSimpleTable rows={topLinks} columns={columns} />
        </div>
      ) : (
        <SmolShimmer
          style={{
            width: "100%",
            height: "400px",
            borderRadius: "8px",
          }}
          animation="wave"
        />
      )}
    </div>
  );
};

export default TopLink;
