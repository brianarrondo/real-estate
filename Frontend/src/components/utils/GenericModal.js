import React, {useState} from "react";
import Modal from "react-bootstrap/Modal";

export const ModalContext = React.createContext({
    setModalShow: null,
    setModalContent: null,
    setSize: null
});

const GenericModal = ({children}) => {

    const [modalShow, setModalShow] = useState(false);
    const [modalContent, setModalContent] = useState(null);
    const [size, setSize] = useState("lg");

    const context = {
        setModalShow: setModalShow,
        setModalContent: setModalContent,
        setSize: setSize,
    };

    const handleClose = () => {
        setModalShow(false);
    };

    return (
        <ModalContext.Provider value={context}>
            <Modal
                size={size || "lg"}
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={modalShow}
                onHide={handleClose}
            >
                {modalContent}
            </Modal>
            {children}
        </ModalContext.Provider>
    );
};

export default GenericModal;
