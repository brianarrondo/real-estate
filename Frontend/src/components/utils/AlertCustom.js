import React, { useEffect } from "react";
import Utils from "../../utils/Utils";
import {Alert} from "react-bootstrap";

const AlertCustom = ({ children, type, show, onClose }) => {

    let key = Utils.generateKey("success-alert");

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        if (show) {
            let alertNode = document.querySelector("#" + key);
            let alert = new window.bootstrap.Alert(alertNode);

            alertNode.addEventListener("closed.bs.alert", () => {
                if (onClose) onClose();
            });

            setTimeout(() => {
                alert.close();
            }, 2000);
        }
    }, []);

    return (
        <Alert key={key} variant={type} onClose={onClose} show={show} dismissible transition>
            <Alert.Heading>{children}</Alert.Heading>
        </Alert>
    );

};

export default AlertCustom;