import { useState } from "react"

export default function Message(params) {

    const [Sender,setSender] = useState(false);
    return <div className={`h-fit my-[30px] w-full flex gap-[100px] p-[30px] ${Sender? "justify-end" : "justify-start"}`} >

            { Sender ?
                <div className=" w-fit h-fit flex">
                    <p className="h-fit w-fit bg-emerald-600 rounded-full px-[30px] py-[10px]">Message</p>
                    <div className=" h-auto w-fit">
                        <div className="relative top-[30px] h-[15px] w-[15px] rounded-[50px] bg-emerald-500"></div>
                    </div>
                </div>: 
                <div className=" w-fit h-fit flex">
                    <div className="h-auto w-fit">
                        <div className="relative top-[30px] h-[15px] w-[15px] rounded-[50px] bg-blue-300"></div>
                    </div>
                    <p className="h-fit w-fit bg-blue-500 rounded-full px-[30px] py-[10px]">Message</p>
                </div>


}

    </div>
};

function Messagebox(){
    return <p className="h-fit w-fit">Message</p>
}
function Circle(){
    return <div className="relative h-[10px] w-[10px] top-[5px] left-[5px]"></div>
}