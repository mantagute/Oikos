import { InputOikos, BotaoOikos, BarraProgresso, ExibicaoMetasBatidas } from '../common';
import './CardPerfilGrupo.css';

function CardPerfilGrupo({ grupo, novaMeta, onChangeMeta, onRedefinirMeta }) {
  return (
    <section className="pagina-grupo-perfil">
      <h1 className="pagina-grupo-titulo">{grupo.nome}</h1>
      <h3 className="pagina-grupo-categoria">CATEGORIA: {grupo.classificacao}</h3>
      <ExibicaoMetasBatidas metasBatidas={grupo.metasBatidas} classificacao={grupo.classificacao} />
      <BarraProgresso atual={grupo.pontuacaoAtual} meta={grupo.meta} />
      <div className="meta-container">
        <InputOikos
          type="number"
          placeholder="Redefina a meta"
          value={novaMeta || ''}
          onChange={(e) => onChangeMeta(e.target.value)}
        />
        <BotaoOikos variante="secundario" onClick={() => onRedefinirMeta(novaMeta)}>
          Aplicar
        </BotaoOikos>
      </div>
    </section>
  );
}

export default CardPerfilGrupo;
