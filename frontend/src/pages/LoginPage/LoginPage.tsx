import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import './LoginPage.scss'

function LoginPage() {
  const navigate = useNavigate()
  const [login, setLogin] = useState('')
  const [password, setPassword] = useState('')

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    alert(`Login submitted!\nLogin: ${login}`)
  }

  const goToRegister = () => {
    navigate('/register')
  }

  return (
    <div className="login-page">
      <div className="card">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="login" className="form-label">
              Login
            </label>
            <input
              type="text"
              className="form-control"
              id="login"
              value={login}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setLogin(e.target.value)}
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
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}
              placeholder="Enter password"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Sign In
          </button>
        </form>

        <div className="text-center mb-4">Or continue with</div>
        <div className="social-icons">
          {['google', 'github', 'facebook'].map((icon) => (
            <div
              key={icon}
              className="icon-wrapper"
            >
              <i className={`bi bi-${icon}`} />
            </div>
          ))}
        </div>

        <p className="register-text">
          Don't have an account?{' '}
          <span className="register-link" onClick={goToRegister}>
            Register
          </span>
        </p>
      </div>
    </div>
  )
}

export default LoginPage