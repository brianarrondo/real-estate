import React from "react";
import {Table} from "react-bootstrap";

const TenantsAccordionList = ({tenants}) => {
    const tenantsRows = tenants.map((t, index) => {
        return (
            <tr key={index}>
                <td>{t.fullName}</td>
                <td>{t.dni}</td>
                <td>{t.phone}</td>
                <td>{t.description}</td>
            </tr>
        );
    });

    return (
        <div className="table-responsive">
            <Table className="table-hover">
                <thead>
                <tr className="basic-padding">
                    <th scope="col">Nombre</th>
                    <th scope="col">DNI</th>
                    <th scope="col">Teléfono</th>
                    <th scope="col">Descripción</th>
                </tr>
                </thead>
                <tbody>
                    {tenantsRows}
                </tbody>
            </Table>
        </div>
    );
}

export default TenantsAccordionList;
