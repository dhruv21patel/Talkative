import { useState } from "react"
import loginimage from '../../assets/20944201.jpg';
import Form from "./Form/Form"

export default function Login(){
    const [Signin,setSignin]  = useState(true);
    return <>
    <div className=" relative h-[80%] w-[80%] flex flex-col md:flex-row bg-white justify-center items-center rounded-lg overflow-hidden">
        <div className="w-full md:w-[40%] h-0 md:h-full  flex justify-center items-center"><img id="loginimage" src={loginimage} alt="Loginimage"  /></div>
        <div className="w-full md:w-[60%] h-full "><Form/></div>
    </div>
    </>

};