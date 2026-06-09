import './SelectOikos.css';

export function SelectOikos({ value, onChange, children, placeholder = 'Selecione...' }) {
  return (
    <div className="select-oikos-wrapper">
      <select className="select-oikos" value={value} onChange={onChange}>
        <option value="" disabled>
          {placeholder}
        </option>
        {children}
      </select>
      <span className="select-oikos-seta" aria-hidden="true">&#9662;</span>
    </div>
  );
}
