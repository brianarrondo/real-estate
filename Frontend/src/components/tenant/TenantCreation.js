import React from 'react';
import TenantService from "../../services/TenantService";

import { useForm } from 'react-hook-form';

const TenantCreation = () => {
    const { register, handleSubmit, errors } = useForm();

    const callback = () => {
        return (<div className="alert alert-primary" role="alert">
            This is a primary alert with <a href="#" className="alert-link">an example link</a>. Give it a click if you
            like.
        </div>);
    };

    const createTenant = (formData) => {
        TenantService.createTenant(formData, callback);
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit((formData) => createTenant(formData))}>

                <div className="form-group row">
                    <label htmlFor="name" className="col-sm-2 col-form-label">Nombre</label>
                    <div className="col-sm-10"><input type="text" className="form-control" name="fullName" placeholder="Nombre" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="dni" className="col-sm-2 col-form-label">DNI</label>
                    <div className="col-sm-10"><input type="text" className="form-control" name="dni" placeholder="DNI" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="phone" className="col-sm-2 col-form-label">Teléfono</label>
                    <div className="col-sm-10"><input type="text" className="form-control" name="phone" placeholder="Telefono" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="desc" className="col-sm-2 col-form-label">Descripción</label>
                    <div className="col-sm-10"><input type="text" className="form-control" name="description" placeholder="Descripcion" ref={register} /></div>
                </div>
                <br />
                <div className="row">
                    <div className="col-sm-10"><button type="submit" className="btn btn-primary">Crear</button></div>
                </div>

            </form>
        </div>
    );
};

export default TenantCreation;