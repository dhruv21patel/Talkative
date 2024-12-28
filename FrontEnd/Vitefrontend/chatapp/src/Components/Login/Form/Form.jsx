
import React, { useState } from 'react';
import Fields from './Fields';
import "./form.css"

export default function Form(params) {
  const [isSignUp, setIsSignUp] = useState(false);
  const [formData, setFormData] = useState({
        email: '',
        password: '',
        confirmPassword: '',
    });

    // Handle input changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission

        console.log(formData.email,formData.password);
        if(formData.email && formData.password)
        {
            let type = null;
            if(formData.confirmPassword && formData.password == formData.confirmPassword)
            {
                type = "Signup"
            }else if(!isSignUp)
            {
                type="Login"
            }

            if(type == null)
            {
                console.log("add fields");
                return
            }else
            {
                await fetch(`http://localhost:8080/${type}`, {
                    method: "POST",
                    mode:"no-cors",
                    headers: {
                        "authentication":"no-auth",
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        email: formData.email,
                        password: formData.password,
                    }),
                })
                .then((response) => {
                    if (response.ok) {
                        console.log("User signup successful");
                        return response.json(); // Return the response.json() to handle it in the next `.then()`
                    } else {
                        console.error("Signup failed:", response.status);
                    }
                })
                .then((data) => {
                    if (data) {
                        console.log("Response data:", data);
                    }
                })
                .catch((err) => {
                    console.error("Error occurred:", err);
                });
            }
        }
        console.log("skipping the fetch part");
    }


  return (

    <div className="w-full h-full bg-green-50 flex flex-col justify-center items-center p-[40px]">
        <h2 className="text-center text-[50px] text-blue-950 font-bold h-fit">
          {isSignUp ? 'Sign Up' : 'Sign In'}
        </h2>

        <form type="Post" onSubmit={handleSubmit}  className="text-black space-y-6 h-fit w-full p-[40px] flex flex-col justify-center items-center">

            <Fields type='email' name='email' val={formData} handler={handleChange}> Email</Fields>
            <Fields type='password' name='password' val={formData} handler={handleChange}>Password</Fields>
            
            {isSignUp &&
                <><Fields type='text' name='firstname' val={formData} handler={handleChange}>Firstname</Fields>
                <Fields type='text' name='lastname' val={formData} handler={handleChange}>Lastname</Fields>
                <Fields type='password' name='confirmPassword' val={formData} handler={handleChange}>Confirm Password</Fields></>}


            <button
                type="submit"
                className="w-full h-fit px-4 py-2 text-white bg-indigo-600 rounded-md shadow hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-500"
            >
                {isSignUp ? 'Sign Up' : 'Sign In'}
            </button>
        </form>


       <p className="text-center text-sm text-gray-600 h-fit w-fit">
          {isSignUp ? 'Already have an account?' : "Don't have an account?"}{' '}
          <button
            type="button"
            className="font-medium text-indigo-600 hover:text-indigo-500 h-fit w-fit"
            onClick={() => setIsSignUp((isSignUp)=>!isSignUp)}
          >
            {isSignUp ? 'Sign In' : 'Sign Up'}
          </button>
        </p>  
      </div>
  );
};

