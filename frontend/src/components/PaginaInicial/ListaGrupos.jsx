import { BsTrash3 } from 'react-icons/bs';

function ListaGrupos({ hookGrupos }) {
  return (
    <section className="pagina-inicial-grupos">
      <header className="pagina-inicial-criar">
        <input
          className="entrada"
          type="text"
          placeholder="+ Nome do novo grupo"
          value={hookGrupos.novoNome}
          onChange={(e) => hookGrupos.setNovoNome(e.target.value)}
        />
        <input
          className="entrada"
          type="text"
          placeholder="+ Senha do novo grupo"
          value={hookGrupos.novaSenha}
          onChange={(e) => hookGrupos.setNovaSenha(e.target.value)}
        />
        <button className="criar-button" onClick={hookGrupos.lidarComCriarGrupo}>
          + Criar Grupo
        </button>
      </header>

      <div className="pagina-inicial-lista" role="list">
        {hookGrupos.grupos.map((grupo) => (
          <article key={grupo.id} className="grupo-card" role="listitem">
            <span className="grupo-card-nome">{grupo.nome}</span>
            <input
              className="entrada"
              type="text"
              placeholder="Digite a senha para entrar"
              value={hookGrupos.senhasIn[grupo.id] || ''}
              onChange={(e) =>
                hookGrupos.setSenhasIn({ ...hookGrupos.senhasIn, [grupo.id]: e.target.value })
              }
            />
            <button
              className="entrar-button"
              onClick={() => hookGrupos.lidarEntrarGrupo(grupo.id)}
            >
              Entrar
            </button>
            <button
              className="deletar-button"
              onClick={() => hookGrupos.lidarDeletarGrupo(grupo.id)}
            >
              <BsTrash3 className="deletar-simbolo" />
            </button>
          </article>
        ))}
      </div>
    </section>
  );
}

export default ListaGrupos;
