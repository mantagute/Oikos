import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import grupoService from '../../services/grupoService';
import { BsTrash3 } from 'react-icons/bs';
import './PaginaInicial.css';

function PaginaInicial() {
  const navigate = useNavigate();
  const [atualizador, setAtualizador] = useState(0);
  const [grupos, setGrupos] = useState([]);
  const [novoNome, setNovoNome] = useState('');
  const [novaSenha, setNovaSenha] = useState('');
  const [senhasIn, setSenhasIn] = useState({});

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
        navigate(`/grupo/${grupoId}`);
      } else {
        alert('Senha incorreta');
      }

      setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
    } catch (error) {
      console.error('Erro ao autenticar senha:', error);
      alert('Erro ao verificar senha.');
    }
  };

  const lidarDeletar = async (grupoId) => {
    const senha = senhasIn[grupoId];
    if (!senha || !senha.trim())
      return alert('Digite a senha do grupo para excluí-lo');

    try {
      const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

      if (resposta === true) {
        await grupoService.excluirGrupo(grupoId, senha);
      } else {
        alert('Senha incorreta');
      }

      setAtualizador((prev) => prev + 1);
      setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
    } catch (error) {
      console.error('Erro ao autenticar senha:', error);
      alert('Erro ao verificar senha.');
    }
  };

  return (
    <div className="pagina-inicial">
      <header className="pagina-inicial-header">
        <div>
          <h1>Oikos</h1>
          <h2>Seja bem vindo</h2>
        </div>
      </header>

      <div className="pagina-inicial-grupos">
        <div className="pagina-inicial-criar">
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
          <button className="criar-button" onClick={lidarComCriarGrupo}>
            + Criar Grupo
          </button>
        </div>

        <div className="pagina-inicial-lista">
          {grupos.map((grupo) => (
            <div key={grupo.id} className="grupo-card">
              <span className="grupo-card-nome">{grupo.nome}</span>
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
                className="entrar-button"
                onClick={() => lidarEntrar(grupo.id)}
              >
                Entrar
              </button>
              <button
                className="deletar-button"
                onClick={() => lidarDeletar(grupo.id)}
              >
                <BsTrash3 className="deletar-simbolo" />
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default PaginaInicial;
