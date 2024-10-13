import { useEffect } from "react";

const AuthProvider = () => {
    function checkLogin(){
        if(localStorage.getItem('user') !== null){
            
        }
    }

    useEffect(()=> {
        checkLogin();
    }, []);
}