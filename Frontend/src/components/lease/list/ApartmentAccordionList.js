import React from "react";
import {Table} from "react-bootstrap";

const ApartmentAccordionList = ({apartment}) => {
    return (
        <div className="table-responsive">
            <Table className="table-hover">
                <thead>
                <tr className="basic-padding">
                    <th scope="col">Nombre</th>
                    <th scope="col">Dirección</th>
                    <th scope="col">Ambientes</th>
                    <th scope="col">Descripción</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>{apartment.name}</td>
                    <td>{apartment.name}</td>
                    <td>{apartment.rooms}</td>
                    <td>{apartment.description}</td>
                </tr>
                </tbody>
            </Table>
        </div>
    );
}

export default ApartmentAccordionList;
