import './chatinterface.css'
import Chatpeople from './chatpeople'
import Chatmessage from './Chatmessage'
import { useState , useEffect} from 'react';
export default function Chatinterface(params) {

    // const [resize,setResize] = useState(false);
    const [chatselected,setchatselected] = useState(false);

    // useEffect(()=>{
    //   function handleresize(width){
    //     if(width <= 1000)
    //       {
    //         setResize(()=>!resize);
    //         console.log(resize);
    //       };
    //   }
    
    //   window.addEventListener('resize',()=>handleresize(window.innerWidth));
    //   return ()=>{window.removeEventListener('resize',()=>handleresize(window.innerWidth));};
  
    // },[]);

    function handlechat(e){
        setchatselected((setchatselected)=> !setchatselected);
    }

    return <div className="h-full w-full flex flex-col  md:flex-row ">
        <div id="dashboard" className={` ${chatselected ? 'hidden ' : 'block'} md:flex w-full md:w-1/2 lg:w-1/4 h-fit md:h-full bg-white flex flex-col gap-[30px]`}>
            <div className="flex flex-col w-full h-fit justify-between p-[25px] gap-[25px] bg-slate-200">
                
                <div className="h-fit w-full flex   justify-between">
                    <h3 className="text-[40px]">Chats</h3>
                    <button className='h-auto w-fit p-[10px] px-[20px] justify-end rounded-xl bg-blue-300'>
                        <span><img src="" alt="Icon"  className=' h-fit w-fit md:hidden block'/></span>
                         <span className='h-fit md:block hidden'>@Create New Chat</span></button>
                </div>
                <input type="text" className='h-[40px] w-full rounded-full'/>

            </div>
            <div id="people" className='h-auto w-full px-[20px]' onClick={()=>handlechat(event)}>
                <Chatpeople/>
            </div>
        </div>


        <div id="chats" className={`w-full ${chatselected ? 'block ' : 'hidden ' } md:block md:w-1/2 lg:w-3/4 h-full bg-sky-50`}>
            <Chatmessage ischatopen = {chatselected} handler ={handlechat}/>
        </div>
    </div>
};
