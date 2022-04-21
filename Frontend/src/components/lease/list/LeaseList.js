import React from "react";
import {Container} from "react-bootstrap";
import LeaseAccordionList from "./LeaseAccordionList";

const LeaseList = () => {
    return (
        <Container>
            <div className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Lista de Contratos</h3>
                    <hr />
                </div>

                <LeaseAccordionList />
            </div>
        </Container>
    );
};

export default LeaseList;