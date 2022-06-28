import React from "react";
import {Table} from "react-bootstrap";
import Utils from "../../../utils/Utils";

const RentalFeesAccordionList = ({rentalFees}) => {
    const rentalFeesRows = rentalFees.map((r, index) => {
        return (
            <tr key={index}>
                <td>{r.fee}%</td>
                <td>{Utils.getFormattedDate(r.startDate)}</td>
                <td>{Utils.getFormattedDate(r.endDate)}</td>
            </tr>
        );
    });

    return (
        <div className="table-responsive">
            <Table className="table-hover">
                <thead>
                <tr className="basic-padding">
                    <th scope="col">Incremento</th>
                    <th scope="col">Fecha Inicial</th>
                    <th scope="col">Fecha Final</th>
                </tr>
                </thead>
                <tbody>
                {rentalFeesRows}
                </tbody>
            </Table>
        </div>
    );
};

export default RentalFeesAccordionList;