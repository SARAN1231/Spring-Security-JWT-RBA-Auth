import React, { useEffect, useState } from "react";
import axios from "axios";

const SecretUser = () => {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem("jwtToken");
      console.log(token)
      if (!token) {
        setError("No token found");
        return;
      }

      try {
        const response = await axios.get("http://localhost:8080/api/secret/user", {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        });
        setData(response.data);
      } catch (err) {
        setError("Failed to fetch data");
        console.error("Error:", err);
      }
    };

    fetchData();
  }, []);

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!data) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>Secret User Data</h1>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
};

export default SecretUser;
