import { GoTrophy } from 'react-icons/go';
import './ExibicaoMetasBatidas.css';

export function ExibicaoMetasBatidas({ metasBatidas, classificacao }) {
  const nivel = classificacao || 'Iniciante';

  return (
    <div className="metas-batidas-card">
      <div className="metas-batidas-icon-wrapper">
        <GoTrophy className="metas-batidas-icon" />
      </div>
      <div className="metas-batidas-info">
        <span className="metas-batidas-numero">{metasBatidas}</span>
        <span className="metas-batidas-label">metas batidas</span>
      </div>
      <span className={`metas-batidas-classificacao ${nivel.toLowerCase()}`}>
        {nivel}
      </span>
    </div>
  );
}
