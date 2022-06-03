import React, {useContext, useEffect, useState} from "react";
import {Accordion, Card, Col, Row, Button} from "react-bootstrap";
import {ServicesContext} from "../../../services/Services";
import ApartmentAccordionList from "./ApartmentAccordionList";
import TenantsAccordionList from "./TenantsAccordionList";
import GenericTableSpinner from "../../utils/GenericTableSpinner";
import GenericEmptyTable from "../../utils/GenericEmptyTable";
import RentalFeesAccordionList from "./RentalFeesAccordionList";
import Utils from "../../../utils/Utils";

const LeaseAccordionList = () => {
    const [leaseList, setLeaseList] = useState([]);
    const [activeIndex, setActiveIndex] = useState(-1);
    const [isLoading, setLoading] = useState(false);
    const { leaseService } = useContext(ServicesContext);

    useEffect(() => {
        setLoading(true);
        leaseService.getAllLease(
            (response) => {
                setLeaseList(response.data);
                setLoading(false);
            },
            (error) => {
                console.log(error);
                setLoading(false);
            }
        );
    }, []);

    function toggleActiveIndex(index) {
        if (index === activeIndex) {
            setActiveIndex(-1);
        } else {
            setActiveIndex(index);
        }
    }

    const leaseListAccordion = leaseList.map((lease, index) => {
        return (
            <Card key={index}>
                <Card.Header>
                    <Row>
                        <Col xs={'auto'} md={2}>{lease.name}</Col>
                        <Col xs={'auto'} md={2}>{lease.apartment.name}</Col>
                        <Col xs={'auto'} md={2}>{lease.estateName}</Col>
                        <Col xs={'auto'} md={2}>{lease.tenants[0].fullName}</Col>
                        <Col xs={'auto'} md={2}>{lease.isEnded ? "SI" : "NO"}</Col>
                        <Col xs={'auto'} md={{ span: 2 }}>
                            <Accordion.Toggle as={Button} eventKey={index.toString()} variant="dark" className="float-right" onClick={() => toggleActiveIndex(index)}>
                                <i className={"bi bi-chevron-" + (activeIndex === index ? "up" : "down")}></i>
                            </Accordion.Toggle>
                        </Col>
                    </Row>

                </Card.Header>

                <Accordion.Collapse eventKey={index.toString()} key={index}>
                    <Card.Body>
                        <div className="padding-top-15">
                            <h5>Datos</h5><hr />
                        </div>

                        <Row>
                            <Col><span className="bold">ID: </span>{lease.id}</Col>
                        </Row>

                        <Row>
                            <Col><span className="bold">Fecha de Inicio: </span>{Utils.getFormattedDate(lease.startDate)}</Col>
                        </Row>

                        <Row>
                            <Col><span className="bold">Fecha de Fin: </span>{Utils.getFormattedDate(lease.endDate)}</Col>
                        </Row>

                        <Row>
                            <Col><span className="bold">Descripcion: </span>{lease.description}</Col>
                        </Row>

                        <div className="padding-top-30">
                            <h5>Departamento</h5>
                            <ApartmentAccordionList apartment={lease.apartment} />
                        </div>

                        <div className="padding-top-15">
                            <h5>Inquilinos</h5>
                            <TenantsAccordionList tenants={lease.tenants} />
                        </div>

                        <div className="padding-top-30">
                            <h5>Incrementos</h5>
                            <RentalFeesAccordionList rentalFees={lease.rentalFees} />
                        </div>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    });

    return (
        <GenericEmptyTable show={leaseListAccordion.length > 0} emptyTableText="No hay contratos para mostrar.">
            <GenericTableSpinner show={isLoading}>
                <Row className="lease-table-titles">
                    <Col xs={'auto'} md={2}>Nombre</Col>
                    <Col xs={'auto'} md={2}>Depto.</Col>
                    <Col xs={'auto'} md={2}>Propiedad</Col>
                    <Col xs={'auto'} md={2}>Inquilino</Col>
                    <Col xs={'auto'} md={2}>Finalizado</Col>
                    <Col xs={'auto'} md={2}></Col>
                </Row>
                <Accordion flush="true">
                    {leaseListAccordion}
                </Accordion>
            </GenericTableSpinner>

        </GenericEmptyTable>
    );
};

export default LeaseAccordionList;