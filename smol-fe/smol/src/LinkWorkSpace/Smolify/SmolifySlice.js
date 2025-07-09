import axiosInstance from "../../Config/AxiosConfig/AxiosInstance";

export const shortenUrl = async (longUrl, title, description, maxClick, expirationDate) => {
    const response = await axiosInstance.post("/api/url/compress", {
        longUrl : longUrl,
        title : title,
        description : description,
        maxClick: maxClick,
        expirationDate : expirationDate
    });
    return response;
}

export const userUrls = async () => {
    const response = await axiosInstance.get("/analytics/user/list");
    return response;
}