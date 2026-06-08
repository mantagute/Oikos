import { useState } from 'react';
import { BsTrash3 } from 'react-icons/bs';
import './PaginaInicial.css';

import { useGrupos } from '../../hooks/useGrupos';
import { useParoquias } from '../../hooks/useParoquias';

function PaginaInicial() {
  const [modo, setModo] = useState('membro'); // 'membro' ou 'paroquia'

  const hookGrupos = useGrupos();
  const hookParoquias = useParoquias();

  return (
    <div className="pagina-inicial">
      <header className="pagina-inicial-header">
        <div>
          <h1>Oikos</h1>
          <h2>Seja bem vindo</h2>
        </div>
      </header>

      <div className="selecao-modo">
        <button 
          className={modo === 'membro' ? 'ativo' : ''} 
          onClick={() => setModo('membro')}
        >
          Modo Membro
        </button>
        <button 
          className={modo === 'paroquia' ? 'ativo' : ''} 
          onClick={() => setModo('paroquia')}
        >
          Modo Paróquia
        </button>
      </div>

      {modo === 'membro' ? (
        <div className="pagina-inicial-grupos">
          <div className="pagina-inicial-criar">
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
          </div>

          <div className="pagina-inicial-lista">
            {hookGrupos.grupos.map((grupo) => (
              <div key={grupo.id} className="grupo-card">
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
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="pagina-inicial-paroquias">
          <div className="pagina-inicial-criar">
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
          </div>

          <div className="pagina-inicial-lista">
            {hookParoquias.paroquias.map((paroquia) => (
              <div key={paroquia.id} className="grupo-card">
                <span className="grupo-card-nome">{paroquia.nomem || paroquia.nome}</span>
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
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default PaginaInicial;