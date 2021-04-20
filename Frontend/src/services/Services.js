import React, {createContext, useContext} from "react";
import LoginService from "./LoginService";
import TenantService from "./TenantService";
import {TokenLoggerContext} from "../components/login/TokenLogger";

export const ServicesContext = createContext({
    loginService: null,
    tenantService: null
});

const Services = ({children}) => {
    const { deleteToken } = useContext(TokenLoggerContext);


    const unauthorizedCallback = () => {
        deleteToken();
    };

    const loginService = new LoginService();
    const tenantService = new TenantService(unauthorizedCallback);

    const context = {
        loginService,
        tenantService
    };

    return (
        <ServicesContext.Provider value={context}>
            {children}
        </ServicesContext.Provider>
    );
};

export default Services;