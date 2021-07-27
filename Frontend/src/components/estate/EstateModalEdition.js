import React, {useState, useContext, createRef, useRef} from "react";
import Modal from "react-bootstrap/Modal";
import {Button, Col, Form, Row, Spinner, Table} from "react-bootstrap";
import {ServicesContext} from "../../services/Services";
import Estate from "../../models/Estate";
import GenericSpinner from "../utils/GenericSpinner";
import Apartment from "../../models/Apartment";

const EstateModalEdition = ({ setModalShow, estate, successCallback, errorCallback }) => {
    let apartmentsToEdit = useRef(JSON.parse(JSON.stringify(estate.apartments)));
    let estateName = createRef();
    let estateAddress = createRef();
    let estateDescription = createRef();
    const { estateService } = useContext(ServicesContext);
    const [validated, setValidated] = useState(false);
    const [isLoading, setLoading] = useState(false);
    const [newApartments, setNewApartments] = useState([]);

    function onHide() {
        setModalShow(false);
    }

    function addNewApartment() {
        setValidated(false);
        setNewApartments(prevState => [...prevState, new Apartment(null, null, 1, "", "")]);
    }

    const getApartmentsTable = () => {
        let apartments = [...estate.apartments, ...newApartments];
        if (apartments && apartments.length) {
            return(
                <Table className="table-hover" id="apartment-table">
                    <thead>
                    <tr className="basic-padding">
                        <th scope="col">Id</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Ambientes</th>
                        <th scope="col">Descripción</th>
                        <th scope="col">Activo</th>
                    </tr>
                    </thead>

                    <tbody>
                    {apartmentsListComponents}
                    {newApartmentsRows}
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
                    [...apartmentsToEdit.current, ...newApartments]
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

    function removeApartment(index) {
        let newApartmentsToAdd = [...newApartments];
        newApartmentsToAdd.splice(index, 1)
        setNewApartments(newApartmentsToAdd);
    }

    function handleInputChange(e, i, name) {
        const newApartmentsToAdd = [...newApartments];
        newApartmentsToAdd[i][name] = e.target.value;
        setNewApartments(newApartmentsToAdd);
    }

    const newApartmentsRows = newApartments.map((apartment, index) => {
        return (
            <tr key={index}>
                <td className="bold">{apartment.apartmentId}</td>
                <td>
                    <Form.Control value={apartment.name} onChange={(e) => handleInputChange(e, index, "name")} required />
                    <Form.Control.Feedback type="invalid">*Obligatorio</Form.Control.Feedback>
                </td>
                <td>
                    <Form.Control value={apartment.rooms} onChange={(e) => handleInputChange(e, index, "rooms")} type="number" min="1" step="1" required />
                    <Form.Control.Feedback type="invalid">*Obligatorio (entero mayor que 0)</Form.Control.Feedback>
                </td>
                <td>
                    <Form.Control as="textarea" style={{height:"38px"}} value={apartment.description} onChange={(e) => handleInputChange(e, index, "description")} />
                </td>
                <td>
                    <Button className="delete-table-row-button" variant="danger" onClick={() => removeApartment(index)} style={{display: "flex"}}>
                        <i className="bi bi-x x-button" />
                    </Button>
                </td>
            </tr>
        );
    });

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
                <td>
                    <Form.Check
                        type="checkbox"
                        defaultChecked={a.active}
                        onChange={(e) => apartmentsToEdit.current[index].active = e.target.checked}
                    />
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
                        Departamentos
                    </Modal.Title>

                    <div className="detail-table-padding">
                        <Button variant="success" style={{fontSize: "12px"}} onClick={addNewApartment}>Agregar</Button>
                    </div>

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