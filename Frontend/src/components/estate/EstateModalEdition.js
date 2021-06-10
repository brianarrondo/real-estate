import React, {useEffect} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row, Table} from "react-bootstrap";
import Services, {ServicesContext} from "../../services/Services";
import Tenant from "../../models/Tenant";
import Estate from "../../models/Estate";

const EstateModalEdition = ({ setModalShow, estate, successCallback, errorCallback }) => {
    let apartmentsToEdit = [];
    let estateName = React.createRef();
    let estateAddress = React.createRef();
    let estateDescription = React.createRef();
    const { estateService } = React.useContext(ServicesContext);

    function onHide() {
        setModalShow(false);
    }

    const getApartmentsTable = () => {
        if (estate.apartments && estate.apartments.length) {
            return(
                <Table className="table-hover" id="apartment-table">
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
    };

    function handleSubmit(e) {
        e.preventDefault();
        estateService.editEstate(
            new Estate(
                estate.estateId,
                estateName.current.value,
                estateAddress.current.value,
                estateDescription.current.value,
                apartmentsToEdit
            ),
            (response) => {
                onHide();
                if (successCallback) successCallback();
            },
            (error) => {
                if (errorCallback) errorCallback(error);
            }
        );
    };

    useEffect(() => {
        apartmentsToEdit = JSON.parse(JSON.stringify(estate.apartments));
    }, []);

    const apartmentsListComponents = (estate.apartments || []).map((a, index) => {
        return (
            <tr key={a.apartmentId}>
                <td className="bold">{a.apartmentId}</td>
                <td>
                    <Form.Control defaultValue={a.name} onChange={(e) => apartmentsToEdit[index].name = e.target.value} />
                </td>
                <td>
                    <Form.Control defaultValue={a.rooms} onChange={(e) => apartmentsToEdit[index].rooms = e.target.value} />
                </td>
                <td>
                    <Form.Control as="textarea" style={{height:"38px"}} defaultValue={a.description} onChange={(e) => apartmentsToEdit[index].description = e.target.value} />
                </td>
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

            <Form onSubmit={handleSubmit}>
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
                            <Form.Control ref={estateName} defaultValue={estate.name} />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Dirección
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control ref={estateAddress} defaultValue={estate.address} />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Descripción
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control ref={estateDescription} defaultValue={estate.description} />
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
                    <Button variant="success" type="submit">Editar</Button>
                </Modal.Footer>
            </Form>
        </>
    );
};

export default EstateModalEdition;