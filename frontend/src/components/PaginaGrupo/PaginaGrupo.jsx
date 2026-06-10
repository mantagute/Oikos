import { useParams, useNavigate } from 'react-router-dom';
import GerenciadorPessoas from './GerenciadorPessoas';
import GerenciadorEventos from './GerenciadorEventos';
import GerenciadorNotificacoes from './GerenciadorNotificacoes';
import CardPerfilGrupo from './CardPerfilGrupo';
import FormularioPontuar from './FormularioPontuar';
import { useGrupo } from '../../hooks/useGrupo';
import { useTitle } from '../../hooks/useTitle';
import { ToastOikos, HeaderOikos, FooterOikos, BotaoOikos } from '../common';
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

      <CardPerfilGrupo
        grupo={hook.grupo}
        novaMeta={hook.novaMeta}
        onChangeMeta={hook.setNovaMeta}
        onRedefinirMeta={hook.lidarRedefinirMeta}
      />

      <FormularioPontuar
        pessoas={hook.pessoas}
        eventos={hook.eventos}
        pessoaSelecionada={hook.pessoaSelecionada}
        eventoSelecionado={hook.eventoSelecionado}
        onChangePessoa={hook.setPessoa}
        onChangeEvento={hook.setEvento}
        onPontuar={hook.lidarPontuar}
      />

      <GerenciadorPessoas grupoId={id} pessoas={hook.pessoas} onAtualizarPessoas={hook.onAtualizarPessoas} onErro={hook.mostrarToast} />
      <GerenciadorEventos grupoId={id} eventos={hook.eventos} onAtualizarEventos={hook.onAtualizarEventos} onErro={hook.mostrarToast} />
      <GerenciadorNotificacoes grupoId={id} notificacoes={hook.notificacoes} onAtualizarNotificacoes={hook.onAtualizarNotificacoes} onMostrarToast={hook.mostrarToast} />

      <FooterOikos />
    </main>
  );
}

export default PaginaGrupo;
