import { useParams, useNavigate } from 'react-router-dom';
import GerenciadorPessoas from './GerenciadorPessoas';
import GerenciadorEventos from './GerenciadorEventos';
import GerenciadorNotificacoes from './GerenciadorNotificacoes';
import { useGrupo } from '../../hooks/useGrupo';
import { useTitle } from '../../hooks/useTitle';
import { BotaoOikos, InputOikos, ToastOikos, HeaderOikos, FooterOikos, BarraProgresso, SelectOikos } from '../common';
import './PaginaGrupo.css';

function PaginaGrupo() {
  const { id } = useParams();
  const navigate = useNavigate();
  const hook = useGrupo(id);
  useTitle(hook.grupo?.nome);

  if (hook.erro) {
    return (
      <main className="pagina-grupo">
        <HeaderOikos mostrarVoltar mostrarLogo />
        <section className="pagina-grupo-perfil">
          <p>{hook.erro}</p>
          <BotaoOikos variante="secundario" onClick={() => navigate('/')}>
            Voltar ao início
          </BotaoOikos>
        </section>
        <FooterOikos />
      </main>
    );
  }

  if (!hook.grupo) {
    return (
      <main className="pagina-grupo">
        <HeaderOikos mostrarVoltar mostrarLogo />
        <section className="pagina-grupo-carregando">
          <div className="skeleton" />
          <div className="skeleton" />
          <div className="skeleton" />
        </section>
      </main>
    );
  }

  return (
    <main className="pagina-grupo">
      <ToastOikos toast={hook.toast} onFechar={hook.fecharToast} />
      <HeaderOikos mostrarVoltar mostrarLogo />

      <section className="pagina-grupo-perfil">
        <h1 className="pagina-grupo-titulo">{hook.grupo.nome}</h1>
        <h3 className="pagina-grupo-categoria">CATEGORIA: {hook.grupo.classificacao}</h3>
        <BarraProgresso atual={hook.grupo.pontuacaoAtual} meta={hook.grupo.meta} />
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
        <header className="pontuar-header">
          <h2>Registrar Atividade</h2>
          <p className="pontuar-subtitulo">Atribua pontos a um membro pela participação</p>
        </header>
        <SelectOikos
          placeholder="Selecione uma pessoa"
          value={hook.pessoaSelecionada || ''}
          onChange={(e) => hook.setPessoa(e.target.value)}
        >
          {hook.pessoas.map((pessoa) => (
            <option key={pessoa.id} value={pessoa.id}>
              {pessoa.nome}
            </option>
          ))}
        </SelectOikos>

        <SelectOikos
          placeholder="Selecione um evento"
          value={hook.eventoSelecionado || ''}
          onChange={(e) => hook.setEvento(e.target.value)}
        >
          {hook.eventos.map((evento) => (
            <option key={evento.id} value={evento.id}>
              {evento.nome} ({evento.pontos} pts)
            </option>
          ))}
        </SelectOikos>

        <BotaoOikos variante="primario" onClick={hook.lidarPontuar}>
          Pontuar
        </BotaoOikos>
      </section>

      <GerenciadorPessoas grupoId={id} pessoas={hook.pessoas} onAtualizarPessoas={hook.onAtualizarPessoas} onErro={hook.mostrarToast} />
      <GerenciadorEventos grupoId={id} eventos={hook.eventos} onAtualizarEventos={hook.onAtualizarEventos} onErro={hook.mostrarToast} />
      <GerenciadorNotificacoes grupoId={id} notificacoes={hook.notificacoes} onAtualizarNotificacoes={hook.onAtualizarNotificacoes} />

      <FooterOikos />
    </main>
  );
}

export default PaginaGrupo;
