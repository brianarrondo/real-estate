import React, { useEffect } from "react";
import {Alert} from "react-bootstrap";

const AlertCustom = ({ children, type, show, onClose }) => {
    let timeoutId;

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        if (show) {
            timeoutId = setTimeout(onClose, 2000);
            return () => {
                clearTimeout(timeoutId);
            };
        }
    });

    return (
        <Alert variant={type} onClose={onClose} show={show} dismissible className="fixed-top opacity">
            <Alert.Heading>{children}</Alert.Heading>
        </Alert>
    );

};

export default AlertCustom;