import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Login from './pages/login.jsx'
import Edit from './pages/edit.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'  

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
  },
   {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/edit',
    element: <Edit />,
  },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
