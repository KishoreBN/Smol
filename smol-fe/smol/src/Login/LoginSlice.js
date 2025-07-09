import axiosInstance from "../Config/AxiosConfig/AxiosInstance";

export const authLogin = async (email, password) => {
    const response = await axiosInstance.post("/api/auth/login", {
        email : email,
        password : password
    });
    return response;
}

export const registerUser = async (username, email, password) => {
    const response = await axiosInstance.post("/api/auth/register", {
        username : username,
        email : email,
        password : password
    });
    return response;
}