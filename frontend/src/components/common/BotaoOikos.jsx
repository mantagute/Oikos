import './BotaoOikos.css';

export function BotaoOikos({ children, variante = 'primario', onClick, type = 'button', className = '' }) {
  // variantes: 'primario' (dourado), 'secundario' (marrom), 'perigo' (vermelho suave), 'texto' (sem fundo)
  return (
    <button 
      type={type} 
      className={`botao-oikos botao-${variante} ${className}`} 
      onClick={onClick}
    >
      {children}
    </button>
  );
}
