import React, {useState} from "react";
import Modal from "react-bootstrap/Modal";

export const ModalContext = React.createContext({
    setModalShow: null,
    setModalContent: null
});

const GenericModal = ({children}) => {

    const [modalShow, setModalShow] = useState(false);
    const [modalContent, setModalContent] = useState(null);

    const context = {
        setModalShow: setModalShow,
        setModalContent: setModalContent
    };

    return (
        <ModalContext.Provider value={context}>
            <Modal
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={modalShow}
            >
                {modalContent}
            </Modal>
            {children}
        </ModalContext.Provider>
    );
};

export default GenericModal;
