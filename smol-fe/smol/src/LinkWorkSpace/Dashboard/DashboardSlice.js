import axiosInstance from "../../Config/AxiosConfig/AxiosInstance";

export const userOverview = async () => {
    const response = await axiosInstance.get("/analytics/user/overview");
    return response;
}

export const urlAnalytics = async (shortUrl, start, end) => {
    const response = await axiosInstance.get("/analytics/urlActivity/" + shortUrl, {
        params : {
            start, end
        }
    });
    return response;
};

export const updateUrl = async (body) => {
    const response = await axiosInstance.post("/api/url/update", body);
    return response;
}

export const deleteUrl = async (id) => {
    const response = await axiosInstance.delete("/api/url/delete", {
        params : {
            id : id
        }
    });
    return response;
}