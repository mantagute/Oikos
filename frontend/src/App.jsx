import { useEffect, useState } from 'react';
import './App.css';
import grupoService from './services/grupoService';

function App() {
  const [grupos, setGrupos] = useState([]);
  const [novoNome, setNovoNome] = useState('');
  const [novaSenha, setNovaSenha] = useState('');

  // Estado para controlar as senhas digitadas na lista de grupos
  const [senhasEntrada, setSenhasEntrada] = useState({});

  useEffect(() => {
    const carregarGrupos = async () => {
      try {
        // Usamos o grupoService importado e await para esperar a resposta da API
        const dados = await grupoService.getListaGrupos();
        setGrupos(dados);
      } catch (error) {
        console.error('Erro ao buscar grupos:', error);
      }
    };

    carregarGrupos();
  }, []);

  return (
    <div className="app-main">
      <header id="titulo">
        <div id="titulo">
          <h1>Oikos</h1>
          <h2>Seja bem vindo</h2>
        </div>
      </header>

      <div id="grupos">
        <div id="criar">
          <input
            className="entrada"
            type="text"
            placeholder="+ Nome do novo grupo"
          />
          <input
            className="entrada"
            type="text"
            placeholder="+ Senha do novo grupo"
          />
          <button className="criarButton" onClick={() => {}}>
            + Criar Grupo
          </button>
        </div>
        <div id="lista">
          {grupos.map((grupo) => (
            <div key={grupo.id} className="grupo">
              <span className="nome">{grupo.nome}</span>
              <input
                className="entrada"
                type="text"
                placeholder="Digite a senha para entrar"
              />
              <button className="entrarButton" onClick={() => {}}>
                Entrar
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
