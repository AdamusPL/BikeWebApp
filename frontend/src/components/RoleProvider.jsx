import { useContext, createContext, useEffect, useState } from "react";
const RoleContext = createContext();

const RoleProvider = ({ children }) => {
    const [isShopAssistant, setIsShopAssistant] = useState(false);

    function checkRole() {
        fetch('http://localhost:8080/', { credentials: 'include' })
            .then(response => {
                if(response.ok){
                    return response.text();
                }
            })
            .then(data => {
                if (data === "ROLE_ADMIN") {
                    setIsShopAssistant(true);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    useEffect(() => {
        checkRole();
    }, []);

    return <RoleContext.Provider value={{ isShopAssistant }}>{children}</RoleContext.Provider>;
};

export default RoleProvider;

export const useRole = () => {
    return useContext(RoleContext);
};
