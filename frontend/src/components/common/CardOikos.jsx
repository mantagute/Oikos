import './CardOikos.css';

export function CardOikos({ children, className = '', destaque = false }) {
  return (
    <div className={`card-oikos ${destaque ? 'card-destaque' : ''} ${className}`}>
      {children}
    </div>
  );
}
