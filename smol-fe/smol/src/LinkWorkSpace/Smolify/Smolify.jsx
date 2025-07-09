import { useState } from "react";
import ShortenLink from "./ShortenLink";
import "./Smolify.scss";
import TopLink from "./TopLink";

const Smolify = () => {
  const [refresh, setRefresh] = useState(true);
  return (
    <div className="smolify-wrapper">
      <div className="shorten-link-container">
        <ShortenLink setRefresh={setRefresh} />
      </div>
      <div className="top-links-container">
        <TopLink refresh={refresh} />
      </div>
    </div>
  );
};

export default Smolify;
