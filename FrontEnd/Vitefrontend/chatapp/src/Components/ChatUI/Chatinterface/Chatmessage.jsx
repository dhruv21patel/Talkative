import './chatinterface.css'
import Message from '../Message/Message'

export default function Chatmessage({ischatopen, handler}) {
    return <div className="h-full w-full flex flex-col ">

            <div id="person" className='h-fit w-full flex justify-between items-center px-[40px]  bg-white rounded-b-xl'>
                <div id="persondetails" className='flex gap-[20px] justify-center items-center py-[40px]'>
                    {ischatopen?<div className='md:hidden block'><img src="" alt="back" onClick={handler}/></div>:""}
                    <div id="photo" className='rounded-[50%] bg-black'></div>
                    <div id="information" className='flex flex-col items-start justify-center'>
                        <p className='text-[30px]'>Dhruv PATEL</p>
                        <p className='text-[20px]'>Date</p>
                    </div>
                </div>

                <div id="options">
                    <p>S</p>
                </div>
            </div>


            <div id="history" className='h-full w-full'>
                <Message/>
            </div>

    </div>
};
