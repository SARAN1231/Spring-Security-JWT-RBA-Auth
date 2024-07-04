import React, { useState } from "react";
import "./SignUp.css";
import { Link } from "react-router-dom";
import axios from "axios";
const SignIn = () => {
    const [userdetail,setuserdetail] = useState({
        Username:"",
        Password:""
    })
    const {Username,Password} = userdetail;

    const handleinputchange = (e) => {
        setuserdetail({...userdetail,[e.target.name]:e.target.value})
    }
    const handlesubmit =async (e) => {
        e.preventDefault();
        try{
          const response = await axios.post(
            "http://localhost:8080/api/auth/signin",
            userdetail,
            {
                headers: {
                    "Content-Type": "application/json",
                },
            }
          );
          // Store the JWT token in local storage
          localStorage.setItem("jwtToken", response.data.Token);
            console.log(response.data);
          // Display an alert with the authStatus message
          alert(response.data.authStatus);
        }catch {
            console.log("error")
        }
    }
  return (
    <div className="loginsignup">
      <div className="loginsignup-container">
        <h1>SignIn</h1>
        <form onSubmit={(e) => handlesubmit(e)}>
          <div className="loginsignup-fields">
            <input
              type="email"
              name="Username"
              value={Username}
              placeholder="email"
              onChange={(e) => handleinputchange(e)}
            />
            <input
              type="password"
              name="Password"
              value={Password}
              placeholder=" password"
              onChange={(e) => handleinputchange(e)}
            />
          </div>

          <button>Login</button>
        </form>
        <p className="loginsignup-login">
          Don't have an account <Link to="/signup"> SignUp Here</Link>
        </p>
        {/* <div className="loginsignup-agree">
          <input type="checkbox" />
          <p>By continuing ,I agree to the terms of use & privacy policy</p>
        </div> */}
      </div>
    </div>
  );
};

export default SignIn;
