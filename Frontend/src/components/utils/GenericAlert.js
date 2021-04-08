import React, {useEffect, useState} from "react";
import {Alert} from "react-bootstrap";

export const AlertContext = React.createContext({
    setAlertType: null,
    setShowAlert: null,
    setAlertContent: null
});

const GenericAlert = ({children}) => {
    const [showAlert, setShowAlert] = useState(false);
    const [alertType, setAlertType] = useState(null);
    const [alertContent, setAlertContent] = useState(null);

    const context = {
        setAlertType: setAlertType,
        setShowAlert: setShowAlert,
        setAlertContent: setAlertContent,
    };

    const onClose = () => {
        setShowAlert(false);
    };

    /* Luego de renderizar por primera vez, se lanza un timeout para cerrar la alerta */
    useEffect(() => {
        if (showAlert) {
            const timeoutId = setTimeout(onClose, 2000);
            return () => {
                clearTimeout(timeoutId);
            };
        }
    }, [alertContent]);

    return (
        <AlertContext.Provider value={context}>
            <Alert variant={alertType} onClose show={showAlert} dismissible className="fixed-top opacity">
                <Alert.Heading>{alertContent}</Alert.Heading>
            </Alert>
            {children}
        </AlertContext.Provider>

    );
};

export default GenericAlert;