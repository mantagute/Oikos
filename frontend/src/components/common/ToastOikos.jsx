import './ToastOikos.css';

export function ToastOikos({ toast, onFechar }) {
    if (!toast) return null;

    return (
        <div className={`toast-oikos toast-${toast.variante}`} role="alert">
            <span className="toast-mensagem">{toast.mensagem}</span>
            <button className="toast-fechar" onClick={onFechar} aria-label="Fechar">✕</button>
        </div>
    );
}