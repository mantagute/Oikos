import './SolicitarVinculo.css';

function SolicitarVinculo({ todosGrupos, gruposVinculados, onSolicitarVinculo }) {
  const gruposDisponiveis = todosGrupos.filter(
    g => !gruposVinculados.some(v => v.id === g.id)
  );

  return (
    <section className="solicitar-vinculo-section">
      <h2>Solicitar Vínculo</h2>
      <p className="solicitar-vinculo-descricao">
        Envie uma solicitação de vínculo para um grupo. O grupo precisará aceitar para compartilhar os dados.
      </p>
      {gruposDisponiveis.length === 0 ? (
        <p className="sem-dados">Todos os grupos já estão vinculados ou não há grupos disponíveis.</p>
      ) : (
        <div className="grupos-grid">
          {gruposDisponiveis.map(grupo => (
            <div key={grupo.id} className="grupo-vinculado-item grupo-solicitar-item">
              <span className="grupo-nome">{grupo.nome}</span>
              <button
                className="botao-solicitar"
                onClick={() => onSolicitarVinculo(grupo.id)}
              >
                Solicitar
              </button>
            </div>
          ))}
        </div>
      )}
    </section>
  );
}

export default SolicitarVinculo;
