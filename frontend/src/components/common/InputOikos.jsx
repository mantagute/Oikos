import './InputOikos.css';

export function InputOikos({ label, type = 'text', value, onChange, placeholder, className = '' }) {
  return (
    <div className={`input-oikos-container ${className}`}>
      {label && <label className="input-oikos-label">{label}</label>}
      <input 
        className="input-oikos-campo"
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
      />
    </div>
  );
}
