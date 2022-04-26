import {Spinner} from "react-bootstrap";
import React from "react";


const GenericTableSpinner = ({show, children}) => {
    return (
        show?
        <div className="spinner"><Spinner animation="border" variant="dark"/></div>
        :
        children
    );
};

export default GenericTableSpinner;