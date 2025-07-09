import { useEffect } from "react";
import "./Overview.scss";

const Overview = (props) => {
  const { data, setSelectedUrl, ...rest } = props;
  return (
    <div className="overview">
      <div className="url-list-header">Overview</div>
      <div className="url-list">
        {data?.map((item) => {
          return (
            <div
              className="item"
              id={item?.shortUrl}
              onClick={() => setSelectedUrl(item)}
            >
              <div className="left">
                <div>{item?.fullShortUrl}</div>
                <div>{item?.title}</div>
              </div>
              <div className="right">
                <div>{item?.clickCount}</div>
                <div>Clicks</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Overview;
