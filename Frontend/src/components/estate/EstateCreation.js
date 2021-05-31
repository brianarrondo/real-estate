import React, {useContext, useEffect, useState} from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import {AlertContext} from "../utils/GenericAlert";
import {ServicesContext} from "../../services/Services";
import Select from "react-select";
import Estate from "../../models/Estate";
import Apartment from "../../models/Apartment";

const EstateCreation = () => {
    let estateName = React.createRef();
    let estateAddress = React.createRef();
    let estateDescription = React.createRef();
    let form = React.createRef();

    const { estateService, apartmentService } = useContext(ServicesContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [validated, setValidated] = useState(false);
    const [apartments, setApartments] = useState([]);
    const [estateApartments, setEstateApartments] = useState([]);

    function handleSubmit(event) {
        let validationOk = true;

        event.preventDefault();

        // realizamos la validacion del form
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);

        if (validationOk) {
            let apartmentsToSet = [];
            if (estateApartments && estateApartments.length) {
                const apartmentsIds = estateApartments.map(a => { return a.value });
                apartmentsToSet = apartments
                    .filter(a => { return apartmentsIds.includes(a.apartmentId) })
                    .map(a => new Apartment(a.apartmentId, a.estate, a.rooms, a.name, a.description));
            }


            let estate = new Estate(
                null,
                estateName.current.value,
                estateAddress.current.value,
                estateDescription.current.value,
                apartmentsToSet
            );

            estateService.createEstate(estate,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> La propiedad <strong>{estate && estate.name}</strong> fue agregada con éxito</div>);
                    setValidated(false);
                    form.reset();
                    setEstateApartments([]);
                    getEstatesApartments();
                },
                (error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al crear la propiedad: "{error.message}"</div>);
                }
            );
        }
    }

    function getEstatesApartments() {
        apartmentService.getAllWithNoEstateAssigned(
            (response) => {
                setApartments(response.data);
            },
            (error) => {
                setAlertType("danger");
                setShowAlert(true);
                setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al obtener los departamentos: "{error.message}"</div>);
            }
        );
    }

    useEffect(() => {
        getEstatesApartments();
    }, []);

    function getOptions() {
        return apartments.map(a => { return {label: a.name + " [" + a.apartmentId + "]", value: a.apartmentId}});
    }

    return (
        <div className="container">
            <Form ref={form} onSubmit={handleSubmit} noValidate validated={validated} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Agregar propiedad</h3>
                    <hr />
                </div>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Nombre
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateName} placeholder="Nombre" required />
                        <Form.Control.Feedback type="invalid">Por favor ingresar el nombre de la propiedad</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Dirección
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateAddress} placeholder="Dirección" required />
                        <Form.Control.Feedback type="invalid">Por favor ingresar la dirección de la propiedad</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Descripción
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateDescription} placeholder="Descripción" />
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Departamentos
                    </Form.Label>
                    <Col sm={8}>
                        <Select
                            closeMenuOnSelect={false}
                            isMulti
                            options={getOptions()}
                            placeholder={"Seleccione los departamentos"}
                            onChange={(value) => setEstateApartments(value)}
                            value={estateApartments}
                        />
                    </Col>
                </Form.Group>

                <div className="align-center basic-padding-10"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>
        </div>

    );
};

export default EstateCreation;