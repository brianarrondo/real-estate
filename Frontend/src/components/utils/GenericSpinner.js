import React from "react";
import {Spinner} from "react-bootstrap";

const GenericSpinner = ({size, variant, show, children}) => {
    return (
        show ?
            <Spinner
                as="span"
                animation="border"
                size={size || "sm"}
                role="status"
                aria-hidden="true"
                variant={variant || "light"}
                className="asd"
            />
            : children
    );
};

export default GenericSpinner;