import React, {createContext, useContext} from "react";
import LoginService from "./LoginService";
import TenantService from "./TenantService";
import {TokenLoggerContext} from "../components/login/TokenLogger";
import EstateService from "./EstateService";
import ApartmentService from "./ApartmentService";
import LeaseService from "./LeaseService";
import PaymentService from "./PaymentService";

export const ServicesContext = createContext({
    loginService: null,
    tenantService: null,
    estateService: null,
    apartmentService: null,
    paymentService: null
});

const Services = ({children}) => {
    const { deleteToken } = useContext(TokenLoggerContext);


    const unauthorizedCallback = () => {
        deleteToken();
    };

    const loginService = new LoginService();
    const leaseService = new LeaseService(unauthorizedCallback);
    const tenantService = new TenantService(unauthorizedCallback);
    const estateService = new EstateService(unauthorizedCallback);
    const apartmentService = new ApartmentService(unauthorizedCallback);
    const paymentService = new PaymentService(unauthorizedCallback);

    const context = {
        loginService,
        leaseService,
        tenantService,
        estateService,
        apartmentService,
        paymentService
    };

    return (
        <ServicesContext.Provider value={context}>
            {children}
        </ServicesContext.Provider>
    );
};

export default Services;