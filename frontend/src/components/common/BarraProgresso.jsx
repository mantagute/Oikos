import { GoMortarBoard } from 'react-icons/go';
import './BarraProgresso.css';

export function BarraProgresso({ atual, meta }) {
  const porcentagem = meta > 0 ? Math.min(Math.round((atual / meta) * 100), 100) : 0;
  const completa = porcentagem >= 100;

  return (
    <div className={`barra-progresso-wrapper ${completa ? 'completa' : ''}`}>
      <div className="barra-progresso-header">
        <span className="barra-progresso-label">Progresso da Meta</span>
        <span className="barra-progresso-valor">
          {atual} / {meta} pts
        </span>
      </div>
      <div className="barra-progresso">
        <div
          className={`barra-progresso-preenchimento ${completa ? 'completa' : ''}`}
          style={{ width: `${porcentagem}%` }}
        />
      </div>
      <span className="barra-progresso-porcentagem">{porcentagem}%</span>
      {completa && (
        <div className="barra-progresso-celebracao">
          <GoMortarBoard className="barra-progresso-coroa" />
          <span className="barra-progresso-parabens">
            Meta alcançada! Que conquista.
          </span>
        </div>
      )}
    </div>
  );
}
