import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import AuthCard from '../../components/AuthCard/AuthCard'
import './RegisterPage.scss'

function RegisterPage() {
  const navigate = useNavigate()

  const [login, setLogin] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (password !== confirmPassword) {
      alert('Passwords do not match')
      return
    }
    alert('Successfully submitted')
  }

  const goToLogin = () => {
    navigate('/login')
  }

  return (
    <AuthCard title="Register">
      <form onSubmit={handleSubmit} className="register-page-form">
        <div className="mb-3">
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

        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Email
          </label>
          <input
            type="email"
            className="form-control"
            id="email"
            value={email}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)}
            placeholder="Enter email"
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="phone" className="form-label">
            Phone
          </label>
          <input
            type="tel"
            className="form-control"
            id="phone"
            value={phone}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPhone(e.target.value)}
            placeholder="Enter phone"
          />
        </div>

        <div className="mb-3">
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

        <div className="mb-4">
          <label htmlFor="confirmPassword" className="form-label">
            Confirm password
          </label>
          <input
            type="password"
            className="form-control"
            id="confirmPassword"
            value={confirmPassword}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setConfirmPassword(e.target.value)
            }
            placeholder="Confirm password"
            required
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Sign Up
        </button>

        <p className="register-switch-text mt-3">
          Already have an account?{' '}
          <span className="register-switch-link" onClick={goToLogin}>
            Login
          </span>
        </p>
      </form>
    </AuthCard>
  )
}

export default RegisterPage
