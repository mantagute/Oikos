import { useParams, useNavigate } from 'react-router-dom';
import GerenciadorPessoas from './GerenciadorPessoas';
import GerenciadorEventos from './GerenciadorEventos';
import GerenciadorNotificacoes from "./GerenciadorNotificacoes";
import { useGrupo } from '../../hooks/useGrupo';
import './PaginaGrupo.css';

function PaginaGrupo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const hook = useGrupo(id);

  if (!hook.grupo) {
    return <main className="pagina-grupo">Carregando...</main>;
  }

  return (
    <main>
      <header className="pagina-grupo">
        <div className="pagina-grupo-header">
          <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
            &lt;&lt; Voltar
          </button>
        </div>
        <section className="pagina-grupo-perfil">
          <h1 className="pagina-grupo-titulo">{hook.grupo.nome}</h1>
          <h2 className="pagina-grupo-meta">
            meta: {hook.grupo.pontuacaoAtual}/{hook.grupo.meta}
          </h2>
          <div>
            <input className='redefinir-meta-input' type='number' placeholder='Redefina a meta' value={hook.novaMeta || ''} onChange={(e) => hook.setNovaMeta(e.target.value)}></input>
            <button className="redefinir-meta-button" onClick={() => {hook.lidarRedefinirMeta(hook.novaMeta)}}>Aplicar</button>
          </div>
          <h3 className="pagina-grupo-categoria">categoria: {hook.grupo.classificacao}</h3>
        </section>
        <section className="pagina-grupo-pontuar">
          <select className="inputPontuar" onChange={(e) => hook.setPessoa(e.target.value)} value={hook.pessoaSelecionada || ''}>
            <option value="">Selecione uma pessoa</option>
            {hook.pessoas.map((pessoa) => (
              <option key={pessoa.id} value={pessoa.id}>
                {pessoa.nome}
              </option>
            ))}
          </select>
    
          <select className="inputPontuar" onChange={(e) => hook.setEvento(e.target.value)} value={hook.eventoSelecionado || ''}>
            <option value="">Selecione um evento</option>
            {hook.eventos.map((evento) => (
              <option key={evento.id} value={evento.id}>
                {evento.nome}
              </option>
            ))}
          </select>
    
          <button className='pontuar-button' onClick={hook.lidarPontuar}>Pontuar</button>
        </section>
      </header>
      <GerenciadorPessoas grupoId={id} pessoas={hook.pessoas} onAtualizarPessoas={hook.onAtualizarPessoas} />
      <GerenciadorEventos grupoId={id} eventos={hook.eventos} onAtualizarEventos={hook.onAtualizarEventos}/>
      <GerenciadorNotificacoes grupoId={id} notificacoes={hook.notificacoes} onAtualizarNotificacoes={hook.onAtualizarNotificacoes}/>
    </main>
  );
}

export default PaginaGrupo;
