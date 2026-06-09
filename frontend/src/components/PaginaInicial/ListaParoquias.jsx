import { BsTrash3 } from 'react-icons/bs';

function ListaParoquias({ hookParoquias }) {
  return (
    <section className="pagina-inicial-paroquias">
      <header className="pagina-inicial-criar">
        <input
          className="entrada"
          type="text"
          placeholder="+ Nome da nova paróquia"
          value={hookParoquias.novoNome}
          onChange={(e) => hookParoquias.setNovoNome(e.target.value)}
        />
        <input
          className="entrada"
          type="text"
          placeholder="+ Senha da nova paróquia"
          value={hookParoquias.novaSenha}
          onChange={(e) => hookParoquias.setNovaSenha(e.target.value)}
        />
        <button className="criar-button" onClick={hookParoquias.lidarCriarParoquia}>
          + Criar Paróquia
        </button>
      </header>

      <div className="pagina-inicial-lista" role="list">
        {hookParoquias.paroquias.map((paroquia) => (
          <article key={paroquia.id} className="grupo-card" role="listitem">
            <span className="grupo-card-nome">{paroquia.nome}</span>
            <input
              className="entrada"
              type="text"
              placeholder="Digite a senha para entrar"
              value={hookParoquias.senhasIn[paroquia.id] || ''}
              onChange={(e) =>
                hookParoquias.setSenhasIn({ ...hookParoquias.senhasIn, [paroquia.id]: e.target.value })
              }
            />
            <button
              className="entrar-button"
              onClick={() => hookParoquias.lidarEntrarParoquia(paroquia.id)}
            >
              Entrar
            </button>
            <button
              className="deletar-button"
              onClick={() => hookParoquias.lidarDeletarParoquia(paroquia.id)}
            >
              <BsTrash3 className="deletar-simbolo" />
            </button>
          </article>
        ))}
      </div>
    </section>
  );
}

export default ListaParoquias;
