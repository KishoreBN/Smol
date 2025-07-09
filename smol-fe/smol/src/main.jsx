import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { ThemeProvider } from '@emotion/react'
import { CssBaseline } from '@mui/material'
import {theme} from "./Utils/Theme/Theme.jsx";

createRoot(document.getElementById('root')).render(
    <ThemeProvider theme={theme}>
      <CssBaseline/>
      <App />
    </ThemeProvider>
)