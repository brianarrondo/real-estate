import React from "react";

const GenericEmptyTable = ({show, children, emptyTableText}) => {
    if (show) {
        return children;
    } else {
        return <div className="align-center padding-bottom-15">{emptyTableText}</div>;
    }
};

export default GenericEmptyTable;