import React from 'react';
import {createRoot} from 'react-dom/client'
import AppWrapper from './App.jsx'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'

createRoot(document.getElementById('root')).render(<AppWrapper/>);