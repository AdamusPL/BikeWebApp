import { useContext, createContext, useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const RoleContext = createContext();

const RoleProvider = ({ children }) => {
    const [role, setRole] = useState("");
    const [cookies, setCookie, removeCookie] = useCookies(['token']);

    const navigate = useNavigate();

    async function checkRole() {
        const response = await fetch('http://localhost:8080/check-role', {
            credentials: 'include'
        });
        const data = await response.json();
        data.forEach(item => {
            if (item === "ROLE_ADMIN") {
                setRole(item);
            }
            else if(item === "ROLE_USER"){
                setRole(item);
            }
            else if(item === "ROLE_ANONYMOUS"){
                if(cookies.token){
                    removeCookie('token');
                }
                setRole(item);
            }
        })
    }

    useEffect(() => {
        checkRole();
    }, []);

    return <RoleContext.Provider value={{ role }}>{children}</RoleContext.Provider>;
};

export default RoleProvider;

export const useRole = () => {
    return useContext(RoleContext);
};
