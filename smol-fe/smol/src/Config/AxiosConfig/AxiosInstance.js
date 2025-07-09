import axios from "axios";

const axiosInstance = axios.create({
    baseURL : import.meta.env.VITE_BASE_URL,
    timeout: import.meta.env.VITE_AXIOS_TIMEOUT,
    headers : {
        'Content-Type' : 'application/json',
    }
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = "Bearer " + token;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => Promise.reject(error)
);

export default axiosInstance;