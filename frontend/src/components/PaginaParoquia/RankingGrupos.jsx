function RankingGrupos({ grupos, onDesvincular }) {
  const ranking = [...grupos].sort((a, b) => {
    if (b.metasBatidas !== a.metasBatidas) return b.metasBatidas - a.metasBatidas;
    return b.pontuacaoAtual - a.pontuacaoAtual;
  });

  return (
    <section className="dashboard-ranking">
      <h2>Ranking de Engajamento</h2>
      {ranking.length === 0 ? (
        <p className="sem-dados">Nenhum grupo vinculado para ranquear.</p>
      ) : (
        <div className="ranking-lista">
          {ranking.map((grupo, index) => (
            <article key={grupo.id} className="ranking-item">
              <span className="ranking-posicao">{index + 1}º</span>
              <div className="ranking-info">
                <strong>{grupo.nome}</strong>
                <small>Categoria: {grupo.classificacao}</small>
              </div>
              <div className="ranking-stats">
                <span>{grupo.pontuacaoAtual} / {grupo.meta} pts</span>
                <span className="badge-metas">{grupo.metasBatidas} metas</span>
              </div>
              {onDesvincular && (
                <button className="botao-desvincular" onClick={() => onDesvincular(grupo.id)}>
                  Desvincular
                </button>
              )}
            </article>
          ))}
        </div>
      )}
    </section>
  );
}

export default RankingGrupos;
