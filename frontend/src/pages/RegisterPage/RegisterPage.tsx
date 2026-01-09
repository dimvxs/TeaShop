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

    // Состояния для UI
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setError(null)

        if (password !== confirmPassword) {
            setError('Пароли не совпадают')
            return
        }

        setLoading(true)

        try {
            const response = await fetch('http://localhost:8080/api/customers/register', {  // Твой эндпоинт
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',

                },
                body: JSON.stringify({
                    login: login,
                    email: email,
                    mobile: phone || null,
                    password: password,
                }),
            })

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}))
                throw new Error(errorData.message || errorData.error || 'Ошибка регистрации')
            }

            const customer = await response.json()
            console.log('Создан пользователь:', customer)

            alert('Регистрация успешна!')
            navigate('/login')

        } catch (err: any) {
            setError(err.message || 'Не удалось зарегистрироваться')
        } finally {
            setLoading(false)
        }
    }

    const goToLogin = () => {
        navigate('/login')
    }

    return (
        <AuthCard title="Register">
            <form onSubmit={handleSubmit} className="register-page-form">
                {error && <div className="alert alert-danger mb-3">{error}</div>}

                <div className="mb-3">
                    <label htmlFor="login" className="form-label">
                        Login
                    </label>
                    <input
                        type="text"
                        className="form-control"
                        id="login"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                        placeholder="Enter login"
                        required
                        disabled={loading}
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
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter email"
                        required
                        disabled={loading}
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
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder="Enter phone"
                        disabled={loading}
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
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Enter password"
                        required
                        disabled={loading}
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
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        placeholder="Confirm password"
                        required
                        disabled={loading}
                    />
                </div>

                <button type="submit" className="btn btn-primary" disabled={loading}>
                    {loading ? 'Регистрация...' : 'Sign Up'}
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