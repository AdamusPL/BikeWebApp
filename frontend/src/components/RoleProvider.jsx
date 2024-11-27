import { useContext, createContext, useEffect, useState } from "react";
import { useCookies } from "react-cookie";

const RoleContext = createContext();

const RoleProvider = ({ children }) => {
    const [isShopAssistant, setIsShopAssistant] = useState(false);
    const [isClient, setIsClient] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [cookies, setCookie, removeCookie] = useCookies(['token']);

    async function checkRole() {
        const response = await fetch('http://localhost:8080/check-role', {
            credentials: 'include'
        });
        const data = await response.json();
        data.forEach(item => {
            if (item === "ROLE_ADMIN") {
                setIsShopAssistant(true);
                setIsLoggedIn(true);
            }
            else if(item === "ROLE_USER"){
                setIsClient(true);
                setIsLoggedIn(true);
            }
            else if(item === "ROLE_ANONYMOUS"){
                if(cookies.token){
                    removeCookie('token');
                }
            }
        })
    }

    useEffect(() => {
        checkRole();
    }, []);

    return <RoleContext.Provider value={{ isShopAssistant, isClient, isLoggedIn }}>{children}</RoleContext.Provider>;
};

export default RoleProvider;

export const useRole = () => {
    return useContext(RoleContext);
};
