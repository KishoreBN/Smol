import { createBrowserRouter } from "react-router-dom";
import Welcome from "../../Welcome/Welcome";
import LoginOrSignUp from "../../Login/LoginOrSignUp";
import LinkWorkSpace from "../../LinkWorkSpace/LinkWorkSpace";
import Dashboard from "../../LinkWorkSpace/Dashboard/Dashboard";
import Smolify from "../../LinkWorkSpace/Smolify/Smolify";
import SignUp from "../../Login/SignUp";
import Login from "../../Login/Login";

const router = createBrowserRouter([
    {
        path: "/",
        Component: Welcome
    },
    {
        path: "/login",
        Component : Login
    },
    {
        path: "/signup",
        Component: SignUp
    },
    {
        path: "/workspace",
        Component: LinkWorkSpace,
        children: [
            {
                path: "/workspace/createLink",
                Component : Smolify
            },
            {
                path: "/workspace/dashboard",
                Component : Dashboard
            }
        ]
    },
])

export default router;