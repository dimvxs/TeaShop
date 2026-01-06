import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthCard from "../../components/AuthCard/AuthCard";
import "./LoginPage.scss";
import { useAuth } from "../../context/AuthContext.tsx"; // ← добавь это

function LoginPage() {
    const navigate = useNavigate();
    const { setUser } = useAuth(); // ← если хочешь вручную обновить (опционально)

    const [loginInput, setLoginInput] = useState("");
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
                    login: loginInput,
                    password: password,
                }),
                credentials: "include", // важно для установки cookie
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || "Ошибка входа");
            }
            const userData = await response.json();
            setUser(userData);
            navigate("/"); // ← после редиректа AuthContext перечитает cookie и обновит user

        } catch (err: any) {
            alert(err.message || "Не удалось войти");
        }
    };

    const goToRegister = () => navigate("/register");

    return (
        <AuthCard title="Login">
            <form onSubmit={handleSubmit} className="login-page-form">
                <div className="mb-4">
                    <label htmlFor="login" className="form-label">Login</label>
                    <input
                        type="text"
                        className="form-control"
                        id="login"
                        value={loginInput}
                        onChange={(e) => setLoginInput(e.target.value)}
                        placeholder="Enter login"
                        required
                    />
                </div>

                <div className="mb-4">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
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