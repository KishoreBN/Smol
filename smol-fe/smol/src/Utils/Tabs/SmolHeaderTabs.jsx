import { useEffect, useState } from "react";
import "./SmolHeaderTabs.scss";

const SmolHeaderTabs = (props) => {
  const { options, defaultSelection, ...rest } = props;
  const [selectedTab, setSelectedTab] = useState(defaultSelection?.value);
  const onTabClick = (item) => {
    setSelectedTab(item?.value);
    item?.onClick();
  }

  useEffect(()=>{
    onTabClick(defaultSelection);
  },[]);

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
