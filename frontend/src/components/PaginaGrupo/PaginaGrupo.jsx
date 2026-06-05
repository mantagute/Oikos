import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import grupoService from '../../services/grupoService';
import './PaginaGrupo.css';

function PaginaGrupo() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [grupo, setGrupo] = useState(null);

  useEffect(() => {
    const carregarDados = async () => {
      try {
        const dadosGrupo = await grupoService.getGrupoPorId(id);
        setGrupo(dadosGrupo);
      } catch (error) {
        console.error('Erro ao carregar grupo:', error);
      }
    };

    carregarDados();
  }, [id]);

  if (!grupo) {
    return <div className="pagina-grupo">Carregando...</div>;
  }

  return (
    <div className="pagina-grupo">
      <div className="pagina-grupo-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
      </div>
      <div className="pagina-grupo-perfil">
        <h1 className="pagina-grupo-titulo">{grupo.nome}</h1>
        <h2 className="pagina-grupo-meta">
          meta: {grupo.pontuacaoAtual}/{grupo.meta}
        </h2>
        <h3 className="pagina-grupo-categoria">categoria: {grupo.classificacao}</h3>
      </div>
    </div>
  );
}

export default PaginaGrupo;
