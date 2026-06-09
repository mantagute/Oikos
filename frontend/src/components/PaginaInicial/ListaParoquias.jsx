import { BsTrash3 } from 'react-icons/bs';
import { InputOikos, BotaoOikos } from '../common';

function ListaParoquias({ hookParoquias }) {
  return (
    <section className="pagina-inicial-paroquias">
      <header className="pagina-inicial-criar">
        <InputOikos
          placeholder="Nome da nova paróquia"
          value={hookParoquias.novoNome}
          onChange={(e) => hookParoquias.setNovoNome(e.target.value)}
        />
        <InputOikos
          placeholder="Senha da nova paróquia"
          value={hookParoquias.novaSenha}
          onChange={(e) => hookParoquias.setNovaSenha(e.target.value)}
        />
        <BotaoOikos variante="primario" onClick={hookParoquias.lidarCriarParoquia}>
          Criar Paróquia
        </BotaoOikos>
      </header>

      <div className="pagina-inicial-lista" role="list">
        {hookParoquias.paroquias.map((paroquia) => (
          <article key={paroquia.id} className="grupo-card" role="listitem">
            <span className="grupo-card-nome">{paroquia.nome}</span>
            <div className="botoes-acao">
              <InputOikos
                className="entrada"
                placeholder="Senha para entrar"
                value={hookParoquias.senhasIn[paroquia.id] || ''}
                onChange={(e) =>
                  hookParoquias.setSenhasIn({ ...hookParoquias.senhasIn, [paroquia.id]: e.target.value })
                }
              />
              <BotaoOikos variante="secundario" onClick={() => hookParoquias.lidarEntrarParoquia(paroquia.id)}>
                Entrar
              </BotaoOikos>
              <button
                className="deletar-button"
                onClick={() => hookParoquias.lidarDeletarParoquia(paroquia.id)}
                title="Excluir paróquia"
              >
                <BsTrash3 className="deletar-simbolo" />
              </button>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}

export default ListaParoquias;
