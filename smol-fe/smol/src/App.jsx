import { RouterProvider } from "react-router-dom";
import "./App.scss";
import router from "./Config/Router/Router";
import { Toaster } from "react-hot-toast";

const App = () => {
  return (
    <div className="app-wrapper">
      <RouterProvider router={router}/>
      <Toaster />
    </div>
  );
};

export default App;
