import React, { useEffect } from "react";

const Alert = ({ children, type, show, setShow }) => {

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        if (show) {
            let alertNode = document.querySelector("#success-alert");
            let alert = new window.bootstrap.Alert(alertNode);

            alertNode.addEventListener("closed.bs.alert", () => {
                if (setShow) setShow(null);
            });

            setTimeout(() => {
                alert.close();
            }, 2000);
        }
    }, []);

    if (show) {
        return (
            <div id="success-alert" className={"alert alert-"+ type +" alert-dismissible show fade fixed-top"} role="alert">
                <div>
                    {children}
                    <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </div>
        );
    } else {
        return null;
    }
};

export default Alert;