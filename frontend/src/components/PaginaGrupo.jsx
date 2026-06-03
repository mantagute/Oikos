import { useState } from 'react';
import './PaginaGrupo.css';

function PaginaGrupo({ grupo, aoSair }) {
  const [pessoaIn, setPessoaIn] = useState('');
  const [eventoIn, setEventoIn] = useState('');

  return (
    <div id="corpo">
      <div id="header">
        <button id="sairButton" onClick={() => aoSair()}>
          &lt;&lt; Voltar
        </button>
      </div>
      <div id="perfil">
        <h1 id="titulo">{grupo.nome}</h1>
        <h2 id="caixaMeta">
          meta: {grupo.pontuacaoAtual}/{grupo.meta}
        </h2>
        <h3 id="caixaCategoria">categoria: {grupo.classificacao}</h3>
        <div id="caixaPontuar">
          <div id="criar">
            <input
              className="entrada"
              type="text"
              placeholder="Insira a pessoa"
              value={pessoaIn} 
              onChange={(e) => setPessoaIn(e.target.value)} 
            />
            <input
              className="entrada"
              type="text"
              placeholder="Insira o evento"
              value={eventoIn} 
              onChange={(e) => setEventoIn(e.target.value)} 
            />
            <button className="criarButton">Pontuar</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PaginaGrupo;
