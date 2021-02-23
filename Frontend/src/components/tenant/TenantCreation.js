import { useState } from 'react';
import TenantService from "../../services/TenantService";

import { AlertList } from "react-bs-notifier";
import { useForm } from 'react-hook-form';

const TenantCreation = () => {
    const [addedSuccessfully, setAddedSuccessfully] = useState(false);
    const { register, handleSubmit, errors } = useForm();

    const callback = () => {
        alert('An essay was submitted: ');
        return(
            <AlertList
                position={"top-right"}
                alerts={[{
                    id: 1,
                    type: "info",
                    message: "Hello, world"
                }]}
                timeout={5}
                dismissTitle="Begone!"
                //onDismiss={onDismissed}
            />
        );
        setAddedSuccessfully(true);
    };

    const createTenant = (formData) => {
        TenantService.createTenant(formData, callback);
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit((formData) => createTenant(formData))}>

                <div className="form-group row">
                    <label htmlFor="name" className="col-sm-1 col-form-label form-label">Nombre</label>
                    <div className="col-sm-5"><input type="text" className="form-control" name="fullName" placeholder="Nombre" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="dni" className="col-sm-1 col-form-label form-label">DNI</label>
                    <div className="col-sm-5"><input type="text" className="form-control" name="dni" placeholder="DNI" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="phone" className="col-sm-1 col-form-label form-label">Teléfono</label>
                    <div className="col-sm-5"><input type="text" className="form-control" name="phone" placeholder="Telefono" ref={register} /></div>
                </div>
                <br />
                <div className="form-group row">
                    <label htmlFor="desc" className="col-sm-1 col-form-label form-label">Descripción</label>
                    <div className="col-sm-5"><textarea type="text" className="form-control" name="description" placeholder="Descripcion" ref={register} /></div>
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