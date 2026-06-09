function DashboardGlobais({ grupos }) {
  // Cálculos de Analytics
  let totalGrupos = grupos.length;
  let totalPessoas = 0;
  let totalEventos = 0;
  let metasBatidas = 0;
  let pontuacaoColetiva = 0;

  for (const grupo of grupos) {
    totalPessoas += grupo.totalPessoas;
    totalEventos += grupo.totalEventos;
    metasBatidas += grupo.metasBatidas;
    pontuacaoColetiva += grupo.pontuacaoAtual;
  }

  return (
    <section className="dashboard-global">
      <article className="indicador-card">
        <h3>Grupos</h3>
        <p>{totalGrupos}</p>
      </article>
      <article className="indicador-card">
        <h3>Pessoas</h3>
        <p>{totalPessoas}</p>
      </article>
      <article className="indicador-card">
        <h3>Eventos Ativos</h3>
        <p>{totalEventos}</p>
      </article>
      <article className="indicador-card destaque-card">
        <h3>Metas Batidas</h3>
        <p>{metasBatidas}</p>
      </article>
      <article className="indicador-card">
        <h3>Pontuação Geral</h3>
        <p>{pontuacaoColetiva}</p>
      </article>
    </section>
  );
}

export default DashboardGlobais;
