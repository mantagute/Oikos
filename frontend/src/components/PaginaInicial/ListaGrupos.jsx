import { BsTrash3 } from 'react-icons/bs';
import { InputOikos, BotaoOikos } from '../common';

function ListaGrupos({ hookGrupos }) {
  return (
    <section className="pagina-inicial-grupos">
      <header className="pagina-inicial-criar">
        <InputOikos
          placeholder="Nome do novo grupo"
          value={hookGrupos.novoNome}
          onChange={(e) => hookGrupos.setNovoNome(e.target.value)}
        />
        <InputOikos
          placeholder="Senha do novo grupo"
          value={hookGrupos.novaSenha}
          onChange={(e) => hookGrupos.setNovaSenha(e.target.value)}
        />
        <BotaoOikos variante="primario" onClick={hookGrupos.lidarComCriarGrupo}>
          Criar Grupo
        </BotaoOikos>
      </header>

      <div className="pagina-inicial-lista" role="list">
        {hookGrupos.grupos.map((grupo) => (
          <article key={grupo.id} className="grupo-card" role="listitem">
            <span className="grupo-card-nome">{grupo.nome}</span>
            <div className="botoes-acao">
              <InputOikos
                className="entrada"
                placeholder="Senha para entrar"
                value={hookGrupos.senhasIn[grupo.id] || ''}
                onChange={(e) =>
                  hookGrupos.setSenhasIn({ ...hookGrupos.senhasIn, [grupo.id]: e.target.value })
                }
              />
              <BotaoOikos variante="secundario" onClick={() => hookGrupos.lidarEntrarGrupo(grupo.id)}>
                Entrar
              </BotaoOikos>
              <button
                className="deletar-button"
                onClick={() => hookGrupos.lidarDeletarGrupo(grupo.id)}
                title="Excluir grupo"
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

export default ListaGrupos;
