import React, { useState } from "react";
import axios from "axios";
import "./SignUp.css";
import { Link } from "react-router-dom";
const Signup = () => {
  const [userdetail, setuserdetail] = useState({
    name: "",
    email: "",
    password: "",
    role: "",
  });
  const { name, email, password, role } = userdetail;

  const handleinputchange = (e) => {
    setuserdetail({ ...userdetail, [e.target.name]: e.target.value });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();

    const userData = {
      name: name,
      Username: email,
      Password: password,
      roles: [role],
    };
    console.log(userData);
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        userData,

        { // used to send what type of format is sent to server
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      console.log(response.data);
      alert(response.data.authStatus);
    } catch {}
  };
  return (
    <div className="loginsignup">
      <div className="loginsignup-container">
        <h1>Signup</h1>
        <form onSubmit={(e) => handleSubmit(e)}>
          <div className="loginsignup-fields">
            <input
              type="text"
              name="name"
              value={name}
              placeholder=" Name"
              onChange={(e) => handleinputchange(e)}
            />
            <input
              type="email"
              name="email"
              value={email}
              placeholder="email"
              onChange={(e) => handleinputchange(e)}
            />
            <input
              type="password"
              name="password"
              value={password}
              placeholder=" password"
              onChange={(e) => handleinputchange(e)}
            />
            <input
              type="text"
              name="role"
              value={role}
              placeholder="Role (ROLE_USER,ROLE_ADMIN)"
              onChange={(e) => handleinputchange(e)}
            />
          </div>
          <button>SignUp</button>
        </form>
        <p className="loginsignup-login">
          Already have an account <Link to="/signin"> SignIn Here</Link>
        </p>
        {/* <div className="loginsignup-agree">
          <input type="checkbox" />
          <p>By continuing ,I agree to the terms of use & privacy policy</p>
        </div> */}
      </div>
    </div>
  );
};

export default Signup;
