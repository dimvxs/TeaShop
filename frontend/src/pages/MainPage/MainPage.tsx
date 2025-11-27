import { useNavigate } from 'react-router-dom'

function MainPage() {
  const navigate = useNavigate()

  const goToLogin = () => {
    navigate('/login')
  }

  return (
    <div>
      <h1>Main Page</h1>
      <button onClick={goToLogin}>Go to Login</button>
    </div>
  )
}

export default MainPage