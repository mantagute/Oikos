import './PaginaGrupo.css';

function PaginaGrupo(params) {
  let {grupo, aoSair} = params;

  return (
    <div id="corpo">
      <div id="header">
        <button id='sairButton' onClick={() => aoSair()}> 
          &lt;&lt; Voltar
        </button>
      </div>
      <div id="perfil">
        <h1 id="titulo">
          Nome do grupo
        </h1>
        <h2 id="caixaMeta">
          meta: 0/1000
        </h2>
        <h3 id="caixaCategoria">
          categoria: ouro
        </h3>
        <div id="caixaPontuar">

        </div>

      </div>

    </div>
  );

}

export default PaginaGrupo;