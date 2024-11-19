import { useContext, createContext, useEffect, useState } from "react";
import { useCookies } from "react-cookie";
const RoleContext = createContext();

const RoleProvider = ({ children }) => {
    const [isShopAssistant, setIsShopAssistant] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [cookies, setCookie, removeCookie] = useCookies(['token']);

    function checkRole() {
        fetch('http://localhost:8080/', { credentials: 'include' })
            .then(response => response.text())
            .then(data => {
                debugger;
                if(data === "NOT_VALID_TOKEN" || data === "TOKEN_IS_NULL"){
                    return;
                }

                else if(data === "EXPIRED_TOKEN"){
                    removeCookie('token');
                }

                if (data === "ROLE_ADMIN") {
                    setIsShopAssistant(true);
                }
                setIsLoggedIn(true);
            })
            .catch(error => {
                console.log(error);
            });
    }

    useEffect(() => {
        checkRole();
    }, []);

    return <RoleContext.Provider value={{ isShopAssistant, isLoggedIn }}>{children}</RoleContext.Provider>;
};

export default RoleProvider;

export const useRole = () => {
    return useContext(RoleContext);
};
