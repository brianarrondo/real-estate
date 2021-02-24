import React, { useEffect } from "react";

const Alert = ({ children, type }) => {

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        let alertNode = document.querySelector("#success-alert");
        let alert = new window.bootstrap.Alert(alertNode);
        setTimeout(() => {
            alert.close();
        }, 2000);
    }, []);

    return (
        <div id="success-alert" className={"alert alert-"+ type +" alert-dismissible show fade fixed-top"} role="alert">
            <div>
                {children}
                <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
    );
};

export default Alert;