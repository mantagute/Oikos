import { useParams, useNavigate } from 'react-router-dom';
import GerenciadorPessoas from './GerenciadorPessoas';
import GerenciadorEventos from './GerenciadorEventos';
import GerenciadorNotificacoes from './GerenciadorNotificacoes';
import { useGrupo } from '../../hooks/useGrupo';
import { BotaoOikos, InputOikos, ToastOikos } from '../common';
import './PaginaGrupo.css';

function PaginaGrupo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const hook = useGrupo(id);

  if (hook.erro) {
    return (
      <main className="pagina-grupo">
        <header className="pagina-grupo-header">
          <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
            &lt;&lt; Voltar
          </button>
        </header>
        <section className="pagina-grupo-perfil">
          <p>{hook.erro}</p>
          <BotaoOikos variante="secundario" onClick={() => navigate('/')}>
            Voltar ao início
          </BotaoOikos>
        </section>
      </main>
    );
  }

  if (!hook.grupo) {
    return <main className="pagina-grupo">Carregando...</main>;
  }

  return (
    <main className="pagina-grupo">
      <ToastOikos toast={hook.toast} onFechar={hook.fecharToast}/>
      <header className="pagina-grupo-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
      </header>

      <section className="pagina-grupo-perfil">
        <h1 className="pagina-grupo-titulo">{hook.grupo.nome}</h1>
        <h3 className="pagina-grupo-categoria">CATEGORIA: {hook.grupo.classificacao}</h3>
        <h2 className="pagina-grupo-meta">
          Meta Atual: {hook.grupo.pontuacaoAtual} / {hook.grupo.meta} pts
        </h2>
        <div className="meta-container">
          <InputOikos
            type="number"
            placeholder="Redefina a meta"
            value={hook.novaMeta || ''}
            onChange={(e) => hook.setNovaMeta(e.target.value)}
          />
          <BotaoOikos variante="secundario" onClick={() => hook.lidarRedefinirMeta(hook.novaMeta)}>
            Aplicar
          </BotaoOikos>
        </div>
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

        <BotaoOikos variante="primario" onClick={hook.lidarPontuar}>
          Pontuar
        </BotaoOikos>
      </section>
      <GerenciadorPessoas grupoId={id} pessoas={hook.pessoas} onAtualizarPessoas={hook.onAtualizarPessoas} />
      <GerenciadorEventos grupoId={id} eventos={hook.eventos} onAtualizarEventos={hook.onAtualizarEventos} />
      <GerenciadorNotificacoes grupoId={id} notificacoes={hook.notificacoes} onAtualizarNotificacoes={hook.onAtualizarNotificacoes} />
    </main>
  );
}

export default PaginaGrupo;
