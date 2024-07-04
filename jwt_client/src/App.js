
import './App.css';
import SecretUser from './Components/SecretUser';
import SignIn from './Components/SignIn';
import Signup from './Components/SignUp';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<h1>Home</h1>} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/signin" element={<SignIn />} />
           <Route path="/secret-user" element={<SecretUser />} />
        </Routes>
      </Router>
     
    </div>
  );
}

export default App;
