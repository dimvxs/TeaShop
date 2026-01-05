import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthCard from "../../components/AuthCard/AuthCard";
import "./LoginPage.scss";

function LoginPage() {
  const navigate = useNavigate();
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/customers/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    login: login,
                    password: password,
                }),
                credentials: "include", // нужно для работы с сессией / cookie
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || "Ошибка входа");
            }

            const user = await response.json();
            console.log("Вход выполнен:", user);

            // Здесь можно сохранить токен или user info в localStorage / context
            localStorage.setItem("currentUser", JSON.stringify(user));

            alert("Вход успешен!");
            navigate("/"); // перенаправление на главную

        } catch (err: any) {
            alert(err.message || "Не удалось войти");
        }
    };


    const goToRegister = () => {
    navigate("/register");
  };

  return (
    <AuthCard title="Login">
      <form onSubmit={handleSubmit} className="login-page-form">
        <div className="mb-4">
          <label htmlFor="login" className="form-label">
            Login
          </label>
          <input
            type="text"
            className="form-control"
            id="login"
            value={login}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setLogin(e.target.value)
            }
            placeholder="Enter login"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            type="password"
            className="form-control"
            id="password"
            value={password}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setPassword(e.target.value)
            }
            placeholder="Enter password"
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Sign In
        </button>

        <div className="text-center mb-4">Or continue with</div>
        <div className="social-icons">
          {["google", "github", "facebook"].map((icon) => (
            <div key={icon} className="icon-wrapper">
              <i className={`bi bi-${icon}`} />
            </div>
          ))}
        </div>

        <p className="register-text">
          Don't have an account?{" "}
          <span className="register-link" onClick={goToRegister}>
            Register
          </span>
        </p>
      </form>
    </AuthCard>
  );
}

export default LoginPage;
