import React from 'react'
import type { ReactNode } from 'react'
import './BodySection.scss'

interface BodySectionProps {
  children: ReactNode
  bgWhite?: boolean
  noBorder?: boolean
  withShadow?: boolean
}

const BodySection: React.FC<BodySectionProps> = ({
  children,
  bgWhite = false,
  noBorder = false,
  withShadow = false,
}) => {
  const classNames = [
    'body-section',
    bgWhite && 'bg-white',
    noBorder ? 'no-border' : 'border-bottom',
    withShadow && !noBorder && 'with-shadow',
  ]
    .filter(Boolean)
    .join(' ')

  return (
    <div className={classNames}>
      <div className="section-container">
        {children}
      </div>
    </div>
  )
}

export default BodySection
