import { useState } from "react";
import "./SmolHeaderTabs.scss";

const SmolHeaderTabs = (props) => {
  const { options, defaultSelection, ...rest } = props;
  const [selectedTab, setSelectedTab] = useState(defaultSelection);
  const onTabClick = (item) => {
    setSelectedTab(item?.value);
    item?.onClick();
  }
  return (
    <div className="smol-header-tabs-wrapper">
      {options?.map((item) => {
        return (
          <div
            className={item.value === selectedTab ? "smol-tab selected" : "smol-tab"}
            onClick={() => onTabClick(item)}
          >
            {item?.label}
          </div>
        );
      })}
    </div>
  );
};

export default SmolHeaderTabs;
