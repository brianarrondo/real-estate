import React, {useState, useContext, createRef, useRef} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row, Spinner, Table} from "react-bootstrap";
import {ServicesContext} from "../../services/Services";
import Estate from "../../models/Estate";
import GenericSpinner from "../utils/GenericSpinner";

const EstateModalEdition = ({ setModalShow, estate, successCallback, errorCallback }) => {
    let apartmentsToEdit = useRef(JSON.parse(JSON.stringify(estate.apartments)));
    let estateName = createRef();
    let estateAddress = createRef();
    let estateDescription = createRef();
    const { estateService } = useContext(ServicesContext);
    const [validated, setValidated] = useState(false);
    const [isLoading, setLoading] = useState(false);

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
        let validationOk = true;
        e.preventDefault();

        // realizamos la validacion del form
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);

        if (validationOk) {
            setLoading(true);
            estateService.editEstate(
                new Estate(
                    estate.estateId,
                    estateName.current.value,
                    estateAddress.current.value,
                    estateDescription.current.value,
                    apartmentsToEdit.current
                ),
                (response) => {
                    setLoading(false);
                    onHide();
                    if (successCallback) successCallback();
                },
                (error) => {
                    setLoading(false);
                    if (errorCallback) errorCallback(error);
                }
            );
        }
    }

    const apartmentsListComponents = apartmentsToEdit.current.map((a, index) => {
        return (
            <tr key={a.apartmentId}>
                <td className="bold">{a.apartmentId}</td>
                <td>
                    <Form.Control defaultValue={a.name} onChange={(e) => apartmentsToEdit.current[index].name = e.target.value} required />
                    <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
                </td>
                <td>
                    <Form.Control defaultValue={a.rooms} onChange={(e) => apartmentsToEdit.current[index].rooms = e.target.value} type="number" min="1" step="1" required />
                    <Form.Control.Feedback type="invalid">*Obligatorio (entero mayor que 0)</Form.Control.Feedback>
                </td>
                <td>
                    <Form.Control as="textarea" style={{height:"38px"}} defaultValue={a.description} onChange={(e) => apartmentsToEdit.current[index].description = e.target.value} />
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

            <Form onSubmit={handleSubmit} noValidate validated={validated}>
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
                            <Form.Control ref={estateName} defaultValue={estate.name} required />
                            <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row}>
                        <Form.Label column sm={2}>
                            Dirección
                        </Form.Label>
                        <Col sm={9}>
                            <Form.Control ref={estateAddress} defaultValue={estate.address} required />
                            <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
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
                    <Button variant="success" type="submit" className="form-submit-button" disabled={isLoading}>
                        <GenericSpinner show={isLoading}>Editar</GenericSpinner>
                    </Button>
                </Modal.Footer>
            </Form>
        </>
    );
};

export default EstateModalEdition;