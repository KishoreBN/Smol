import Overview from "./Overview";
import "./Dashboard.scss";
import Details from "./Details";
import { useEffect, useState } from "react";
import { userOverview } from "./DashboardSlice";
import toast from "react-hot-toast";

const Dashboard = () =>{
    const [urlList, setUrlList] = useState([]);
    const [selectedUrl, setSelectedUrl] = useState();
    const [refresh, setRefresh] = useState(true);

    useEffect(() => {
        userOverview().then((response) => {
            setUrlList(response?.data?.map((item)=> {
                return {...item, fullShortUrl : import.meta.env.VITE_BASE_URL + "/" + item?.shortUrl}
            }));
        }).catch((error)=>{
            toast.error("Something went wrong.");
        });
    }, [refresh]);

    useEffect(()=>{
        if (urlList?.length > 0) setSelectedUrl(urlList[0]);
        else setSelectedUrl(null);
    },[urlList]);

    return (
        <div className="dashboard-wrapper">
            <div className="overview-container fade-in">
                <Overview data={urlList} setSelectedUrl={setSelectedUrl}/>
            </div>
            <div className="graph-chart fade-in">
                {selectedUrl && <Details data={selectedUrl} setRefresh={setRefresh}/>}
            </div>
        </div>
    )
};

export default Dashboard;