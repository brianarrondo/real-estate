import React, { useState, useEffect } from 'react';
import TenantService from "../../services/TenantService";
import Utils from "../../utils/Utils";

import { useForm } from 'react-hook-form';
import Alert from "../utils/Alert";

const TenantCreation = () => {
    const { register, handleSubmit, errors, reset } = useForm();
    const [tenantCreated, setTenantCreated] = useState(null);

    const createTenant = (formData) => {
        TenantService.createTenant(formData, () => { setTenantCreated(formData.fullName) });
        reset();
    };

    const showSuccessTenantCreationAlert = () => {
        if (tenantCreated != null) {
            return (
                <div key={Utils.generateKey(tenantCreated)}>
                    <Alert type="success">
                       El inquilino <strong>{tenantCreated}</strong> fue agregado con éxito <i className="bi bi-check-square"></i>
                    </Alert>
                </div>
            );
        }
    };

    return (
        <div className="container">
            {showSuccessTenantCreationAlert()}

            <form onSubmit={handleSubmit((formData) => createTenant(formData))} className="basic-padding-20 shadow justify-content-center rounded-bottom border border-light">
                <div>
                    <h3>Agregar nuevo Inquilino</h3>
                </div>

                <hr />

                <div className="form-group row justify-content-center">
                    <label htmlFor="name" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Nombre</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="fullName" placeholder="Nombre" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="dni" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">DNI</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="dni" placeholder="DNI" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="phone" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Teléfono</label>
                    <div className="col-sm-7 col-md-5"><input type="text" className="form-control" name="phone" placeholder="Telefono" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row justify-content-center">
                    <label htmlFor="desc" className="col-sm-2 col-md-2 col-lg-1 col-form-label form-label">Descripción</label>
                    <div className="col-sm-7 col-md-5"><textarea type="text" className="form-control" name="description" placeholder="Descripcion" ref={register} /></div>
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