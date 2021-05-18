import React, {createContext, useContext} from "react";
import LoginService from "./LoginService";
import TenantService from "./TenantService";
import {TokenLoggerContext} from "../components/login/TokenLogger";
import EstateService from "./EstateService";

export const ServicesContext = createContext({
    loginService: null,
    tenantService: null,
    estateService: null
});

const Services = ({children}) => {
    const { deleteToken } = useContext(TokenLoggerContext);


    const unauthorizedCallback = () => {
        deleteToken();
    };

    const loginService = new LoginService();
    const tenantService = new TenantService(unauthorizedCallback);
    const estateService = new EstateService(unauthorizedCallback);

    const context = {
        loginService,
        tenantService,
        estateService
    };

    return (
        <ServicesContext.Provider value={context}>
            {children}
        </ServicesContext.Provider>
    );
};

export default Services;