import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Account(){

    const navigate = useNavigate();

    useEffect(() => {
        checkLogin();
    }, []);

    function checkLogin(){
        if(localStorage.getItem('user') === null){
            navigate('/sign-in');
        }
    }

    return(<>
        
    </>)
}