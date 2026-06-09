import './InputOikos.css';

export function InputOikos({ label, type = 'text', value, onChange, placeholder, className = '', multiline = false, rows = 4 }) {
  return (
    <div className={`input-oikos-container ${className}`}>
      {label && <label className="input-oikos-label">{label}</label>}
      {multiline ? (
        <textarea
          className="input-oikos-campo input-oikos-textarea"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          rows={rows}
        />
      ) : (
        <input
          className="input-oikos-campo"
          type={type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
        />
      )}
    </div>
  );
}
