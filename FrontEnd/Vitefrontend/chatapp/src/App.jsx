import { useState } from 'react'
import Login from './Components/Login/Login.jsx'
import './App.css'
import Chatinterface from './Components/ChatUI/Chatinterface/Chatinterface.jsx'

function App() {

  return (
    <>
       <div className='bg-blue-400 text-white flex justify-center items-center'>
      <Login/>
       </div>
{/*        <Chatinterface/> */}
       </>
      
  )
}

export default App
