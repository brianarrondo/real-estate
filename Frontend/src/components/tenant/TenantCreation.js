import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import { useForm } from 'react-hook-form';
import Alert from "../utils/Alert";

const TenantCreation = () => {
    const [tenantCreated, setTenantCreated] = useState(null);
    const [showTenantCreationAlert, setShowTenantCreationAlert] = useState(false);
    const [errorOnTenantCreationRequest, setErrorOnTenantCreationRequest] = useState(false);
    const { register, handleSubmit, errors, reset } = useForm();

    const createTenant = (formData) => {
        TenantService.createTenant(formData,
                () => {
                    setTenantCreated(formData);
                    setShowTenantCreationAlert(true);
                    reset();
                },
                (error) => {
                    setErrorOnTenantCreationRequest(error);
                }
            );
    };

    /* Alerta para la creacion exitosa del objeto */
    const displaySuccessTenantCreationAlert = () => {
        let tenantName = tenantCreated ? tenantCreated.fullName : "";
        return (
            <div key={Utils.generateKey(tenantName)}>
                <Alert type="success" show={showTenantCreationAlert} onClose={() => setShowTenantCreationAlert(false)}>
                    <i className="bi bi-check-circle"></i> El inquilino <strong>{tenantName}</strong> fue agregado con éxito
                </Alert>
            </div>
        );
    };

    /* Alerta para errores al realizar el request de creacion del objeto */
    const displayErrorTenantCreationAlert = () => {
        let errorMsg = errorOnTenantCreationRequest ? errorOnTenantCreationRequest.message : "";
        return (
            <div key={Utils.generateKey("error")}>
                <Alert type="danger" show={errorOnTenantCreationRequest} onClose={() => setErrorOnTenantCreationRequest(null)}>
                    <i className="bi bi-exclamation-circle"></i> Hubo un error en la creación del Inquilino: "{errorMsg}"
                </Alert>
            </div>
        );
    };

    return (
        <div className="container">
            {displaySuccessTenantCreationAlert()}
            {displayErrorTenantCreationAlert()}

            <form
                onSubmit={handleSubmit((formData) => createTenant(formData))}
                className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light"
                noValidate
            >
                <div>
                    <h3>Agregar nuevo Inquilino</h3>
                </div>

                <hr />

                <div className="form-group row justify-content-center">
                    <label htmlFor="name" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Nombre</label>
                    <div className="col-sm-7 col-md-5">
                        <input
                            type="text"
                            className={"form-control" + (errors.fullName ? " is-invalid" : "")}
                            name="fullName"
                            placeholder="Nombre"
                            ref={register({required: true})}
                        />
                        <div className="invalid-feedback">Debe completar el nombre</div>
                    </div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="dni" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">DNI</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="dni" placeholder="DNI" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="phone" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Teléfono</label>
                    <div className="col-sm-7 col-md-5">
                        <input type="text" className="form-control" name="phone" placeholder="Telefono" ref={register} />
                    </div>

                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="desc" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Descripción</label>
                    <div className="col-sm-7 col-md-5">
                        <input
                            type="text"
                            className={"form-control" + (errors.description ? " is-invalid" : " ")}
                            name="description"
                            placeholder="Descripcion"
                            ref={register({maxLength: 10})}
                        />
                        <div className="invalid-feedback">Se superó el máximo de carácteres permitidos (50)</div>
                    </div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <div className="col-sm-5 col-md-5 align-center"><button type="submit" className="btn btn-dark">Agregar</button></div>
                </div>
            </form>
        </div>
    );
};

export default TenantCreation;