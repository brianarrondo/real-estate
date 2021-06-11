import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row, Table} from "react-bootstrap";

const EstateModalDetail = ({ setModalShow, estate }) => {
    function onHide() {
        setModalShow(false);
    }

    const getApartmentsTable = () => {
        if (estate.apartments && estate.apartments.length) {
            return(
                <Table className="table-hover">
                    <thead>
                    <tr className="basic-padding">
                        <th scope="col">Id</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Ambientes</th>
                        <th scope="col">Descripción</th>
                    </tr>
                    </thead>

                    <tbody>
                    {apartmentsListComponents}
                    </tbody>
                </Table>
            );
        } else {
            return (<div className="align-center">No hay departamentos para esta propiedad.</div>);
        }
    }

    const apartmentsListComponents = (estate.apartments || []).map(a => {
        return (
            <tr key={a.apartmentId}>
                <td className="bold">{a.apartmentId}</td>
                <td>{a.name}</td>
                <td>{a.rooms}</td>
                <td>{a.description}</td>
            </tr>
        )}
    );

    return (
        <>
            <Modal.Header closeButton>
                <Modal.Title>
                    Detalle de Propiedad
                </Modal.Title>
            </Modal.Header>

            <Form>
                <Modal.Body>
                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Id
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control readOnly defaultValue={estate.estateId} className="details" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Nombre
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control readOnly defaultValue={estate.name} />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Dirección
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control readOnly defaultValue={estate.address} />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Descripción
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control readOnly defaultValue={estate.description} />
                        </Col>
                    </Form.Group>

                    <Modal.Title className="detail-table-padding">
                        Detalle de Departamentos
                    </Modal.Title>

                    <div className="table-responsive">
                        {getApartmentsTable()}
                    </div>

                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={onHide}>Cerrar</Button>
                </Modal.Footer>
            </Form>
        </>
    )
}

export default EstateModalDetail;