import { useEffect, useState } from 'react';
import './App.css';
import grupoService from './services/grupoService';
import { BsTrash3 } from 'react-icons/bs';
import PaginaGrupo from './components/PaginaGrupo';

function App() {
  const [atualizador, setAtualizador] = useState(0);
  const [grupos, setGrupos] = useState([]);
  const [grupoAtivo, setGrupoAtivo] = useState(null); // Estado para o grupo selecionado
  const [novoNome, setNovoNome] = useState('');
  const [novaSenha, setNovaSenha] = useState('');
  const [senhasIn, setSenhasIn] = useState({}); // Alterado para objeto

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
    if (!novoNome.trim()) return alert('Digite um nome para o novo grupo');
    if (novoNome.trim() && !novaSenha.trim())
      return alert('Digite uma senha para o novo grupo');

    try {
      await grupoService.criarGrupo(novoNome, novaSenha);
      alert(`Grupo ${novoNome} criado`);

      setNovoNome('');
      setNovaSenha('');

      setAtualizador((prev) => prev + 1);
    } catch (error) {
      console.error('Erro ao criar grupo:', error);
    }
  };

  const lidarEntrar = async (grupoId) => {
    const senha = senhasIn[grupoId];
    if (!senha || !senha.trim()) return alert('Digite a senha do grupo');

    try {
      const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

      if (resposta === true) {
        // Se a senha estiver correta, define o grupo ativo
        const grupo = grupos.find((g) => g.id === grupoId);
        setGrupoAtivo(grupo);
      } else alert('Senha incorreta');

      setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
    } catch (error) {
      console.error('Erro ao autenticar senha:', error);
      alert('Erro ao verificar senha.');
    }
  };

  const lidarDeletar = async (grupoId, nomeGrupo) => {
    const senha = senhasIn[grupoId];
    if (!senha || !senha.trim())
      return alert('Digite a senha do grupo para excluí-lo');

    try {
      const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

      if (resposta === true) {
        grupoService.excluirGrupo(grupoId, senha);
        alert(`Grupo ${nomeGrupo} excluído`);
      } else alert('Senha incorreta');

      setAtualizador((prev) => prev + 1);
      setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
    } catch (error) {
      console.error('Erro ao autenticar senha:', error);
      alert('Erro ao verificar senha.');
    }
  };

  // Se houver um grupo ativo, renderiza a "página" do grupo
  if (grupoAtivo) {
    return (
      <PaginaGrupo grupo={grupoAtivo} aoSair={() => setGrupoAtivo(null)}/>
    );
  }

  // Caso contrário, renderiza a lista inicial
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
                value={senhasIn[grupo.id] || ''}
                onChange={(e) =>
                  setSenhasIn({ ...senhasIn, [grupo.id]: e.target.value })
                }
              />
              <button
                className="entrarButton"
                onClick={() => lidarEntrar(grupo.id)}
              >
                Entrar
              </button>
              <button
                className="deletarButton"
                onClick={() => lidarDeletar(grupo.id, grupo.nome)}
              >
                <BsTrash3 className="deletarSimbolo" />
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
