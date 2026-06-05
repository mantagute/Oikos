import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import grupoService from '../../services/grupoService';
import pessoaService from '../../services/pessoaService';
import eventoService from '../../services/eventoService';
import './PaginaGrupo.css';

function PaginaGrupo() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [grupo, setGrupo] = useState(null);
  const [pessoas, setPessoas] = useState([]);
  const [eventos, setEventos] = useState([]);
  const [pessoaSelecionada, setPessoa] = useState(null);
  const [eventoSelecionado, setEvento] = useState(null);

  useEffect(() => {
    const carregarDados = async () => {
      try {
        const [dadosGrupo, dadosPessoas, dadosEventos] = await Promise.all([
          grupoService.getGrupoPorId(id),
          pessoaService.getListaPessoas(id),
          eventoService.getListaEventos(id)
        ]); 
        setGrupo(dadosGrupo);
        setPessoas(dadosPessoas);
        setEventos(dadosEventos)
      } catch (error) {
        console.error('Erro ao carregar grupo:', error);
      }
    };

    carregarDados();
  }, [id]);

  if (!grupo) {
    return <div className="pagina-grupo">Carregando...</div>;
  }

  const lidarPontuar = async () => {
    if (!pessoaSelecionada || !eventoSelecionado) {
      return alert('Selecione uma pessoa e um evento para pontuar');
    }
    try {
      await grupoService.registrarAtividade(id, pessoaSelecionada, eventoSelecionado);
      const dadosGrupo = await grupoService.getGrupoPorId(id);
      setGrupo(dadosGrupo);
    } 
    catch (error) {
      console.error('Erro ao registrar atividade:', error);
      alert('Erro ao registrar atividade.');
    }
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
      <div className="pagina-grupo-pontuar">
        <select onChange={(e) => setPessoa(e.target.value)} value={pessoaSelecionada || ''}>
          <option value="">Selecione uma pessoa...</option>
          {pessoas.map((pessoa) => (
            <option key={pessoa.id} value={pessoa.id}>
              {pessoa.nome}
            </option>
          ))}
        </select>
  
        <select onChange={(e) => setEvento(e.target.value)} value={eventoSelecionado || ''}>
          <option value="">Selecione um evento...</option>
          {eventos.map((evento) => (
            <option key={evento.id} value={evento.id}>
              {evento.nome}
            </option>
          ))}
        </select>
  
        <button onClick={lidarPontuar}>Pontuar</button>
      </div>
    </div>
  );
}

export default PaginaGrupo;
