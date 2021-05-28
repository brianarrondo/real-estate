import React from 'react';
import EstateListTable from "./EstateListTable";

const EstateList = () => {
    return (
        <div className="container">
            <div className="basic-padding-10 shadow rounded-bottom border border-light">
                <div>
                    <h3>Lista de Propiedades</h3>
                    <hr />
                </div>

                <div className="basic-padding-10 table-responsive">
                    <EstateListTable />
                </div>
            </div>
        </div>
    );
};

export default EstateList;