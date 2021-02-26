import React, { useEffect } from "react";
import Utils from "../../utils/Utils";

const Alert = ({ children, type, show, onClose }) => {

    let id = Utils.generateKey("success-alert");

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        if (show) {
            let alertNode = document.querySelector("#" + id);
            let alert = new window.bootstrap.Alert(alertNode);

            alertNode.addEventListener("closed.bs.alert", () => {
                if (onClose) onClose();
            });

            setTimeout(() => {
                alert.close();
            }, 2000);
        }
    }, []);

    if (show) {
        return (

            <div id={id} className={"alert alert-"+ type +" alert-dismissible show fade fixed-top"} role="alert">
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