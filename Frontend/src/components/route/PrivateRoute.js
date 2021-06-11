import React, {useContext} from "react";
import {Redirect, Route} from "react-router-dom";
import {TokenLoggerContext} from "../login/TokenLogger";

const PrivateRoute = ({ component: Component, ...rest }) => {
    const { token } = useContext(TokenLoggerContext);
    return(
        <Route {...rest} render={(props) => (
            token !== null
                ? <Component {...props} />
                : <Redirect to='/login' />
        )}/>
    );
}

export default PrivateRoute;