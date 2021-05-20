import React, {useContext, useEffect, useState} from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import {AlertContext} from "../utils/GenericAlert";
import {ServicesContext} from "../../services/Services";
import Tenant from "../../models/Tenant";
import Select from "react-select";
import makeAnimated from 'react-select/animated';

const EstateCreation = () => {
    let estateName = React.createRef();
    let estateAddress = React.createRef();
    let estateDescription = React.createRef();
    let estateApartments = React.createRef();
    let form = React.createRef();

    const { estateService, apartmentService } = useContext(ServicesContext);
    const {setShowAlert, setAlertType, setAlertContent} = useContext(AlertContext);
    const [validated, setValidated] = useState(false);
    const [apartments, setApartments] = useState([]);
    const [options, setOptions] = useState([]);
    const animatedComponents = makeAnimated();

    function handleSubmit(event) {
        console.log(estateName.current.value);
        console.log(estateAddress.current.value);
        console.log(estateDescription.current.value);
        console.log(estateApartments.current.value);

        let validationOk = true;

        event.preventDefault();
        /*
        // realizamos la validacion del form
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            validationOk = false;
        }
        setValidated(true);


        if (validationOk) {
            let tenant = new Tenant(
                null,
                estateName.current.value,
                estateAddress.current.value,
                estateDescription.current.value,
                estateApartments.current.value
            );

            tenantService.createTenant(tenant,
                () => {
                    setAlertType("success");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-check-circle"></i> El inquilino <strong>{tenant && tenant.fullName}</strong> fue agregado con éxito</div>);
                    setValidated(false);
                    form.reset();
                },
                (error) => {
                    setAlertType("danger");
                    setShowAlert(true);
                    setAlertContent(<div><i className="bi bi-exclamation-circle"></i> Hubo un error al crear el inquilino: "{error.message}"</div>);
                }
            );
        }*/

    }

    useEffect(() => {
        apartmentService.getAllWithNoEstateAssigned(
            (response) => {
                setApartments(response.data);
            },
            (error) => {
                //TODO error
            }
        );
    }, []);

    function getOptions() {
        return apartments && apartments.map(a => { return {label: a.name + "[" + a.apartamentId + "]", value: a.apartamentId}})
    }

    function handleChange(event) {
        console.log(event);
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
                        <Form.Control type="text" ref={estateName} placeholder="Nombre Completo" required />
                        <Form.Control.Feedback type="invalid">Por favor ingresar el nombre del inquilino</Form.Control.Feedback>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Address
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateAddress} placeholder="Address" />
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Description
                    </Form.Label>
                    <Col sm={8}>
                        <Form.Control type="text" ref={estateDescription} placeholder="Description" />
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="justify-content-center">
                    <Form.Label column sm={3}>
                        Departamentos
                    </Form.Label>
                    <Col sm={8}>
                        <Select
                            components={animatedComponents}
                            closeMenuOnSelect={false}
                            isMulti
                            options={getOptions()}
                            onChange={handleChange}
                        />
                    </Col>
                </Form.Group>

                <div className="align-center basic-padding-10"><Button variant="dark" type="submit">Agregar</Button></div>
            </Form>
        </div>

    );
};

export default EstateCreation;