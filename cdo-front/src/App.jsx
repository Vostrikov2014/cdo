import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './components/Home.jsx';
import Login from './components/Login.jsx';
import Register from './components/Register.jsx';

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/login" component={Login} />
                <Route path="/register" component={Register} />
                <Route path="/home" component={Home} />
                <Route path="/" exact component={Login} />
            </Switch>
        </Router>
    );
};

export default App;
