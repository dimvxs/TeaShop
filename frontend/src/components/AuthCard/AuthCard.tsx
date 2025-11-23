import type { ReactNode } from 'react'
import './AuthCard.scss'

type AuthCardProps = {
  title: string
  children: ReactNode
}

function AuthCard({ title, children }: AuthCardProps) {
  return (
    <div className="auth-page">
      <div className="auth-card card">
        <h2>{title}</h2>
        {children}
      </div>
    </div>
  )
}

export default AuthCard