import { useEffect, useState } from 'react';
import './App.css';
import grupoService from './services/grupoService';

function App() {
  const [atualizador, setAtualizador] = useState(0);
  const [grupos, setGrupos] = useState([]);
  const [novoNome, setNovoNome] = useState('');
  const [novaSenha, setNovaSenha] = useState('');
  const [senhasEntrada, setSenhasEntrada] = useState({});

  useEffect(() => {
    const carregarGrupos = async () => {
      try {
        const dados = await grupoService.getListaGrupos();
        setGrupos(dados);
      } catch (error) {
        console.error('Erro ao buscar grupos:', error);
      }
    };

    carregarGrupos();
  }, [atualizador]);

  const lidarComCriarGrupo = async () => {
    if (!novoNome.trim()) return alert("Digite um nome para o grupo!");

    try {
      await grupoService.criarGrupo(novoNome, novaSenha);
      
      setNovoNome("");
      setNovaSenha("");
      
      setAtualizador(prev => prev + 1);
    } catch (error) {
      console.error('Erro ao criar grupo:', error);
    }
  };

  return (
    <div className="app-main">
      <header id="titulo">
        <div>
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
            value={novoNome} 
            onChange={(e) => setNovoNome(e.target.value)}
          />
          <input
            className="entrada"
            type="text"
            placeholder="+ Senha do novo grupo"
            value={novaSenha} 
            onChange={(e) => setNovaSenha(e.target.value)}
          />
          <button className="criarButton" onClick={lidarComCriarGrupo}>
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