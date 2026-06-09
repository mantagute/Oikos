import { useParams, useNavigate } from 'react-router-dom';
import { useParoquia } from '../../hooks/useParoquia';
import { useTitle } from '../../hooks/useTitle';
import DashboardGlobais from './DashboardGlobais';
import RankingGrupos from './RankingGrupos';
import PainelComunicacao from './PainelComunicacao';
import { ToastOikos, HeaderOikos, FooterOikos, BotaoOikos } from '../common';
import './PaginaParoquia.css';

function PaginaParoquia() {
  const { id } = useParams();
  const navigate = useNavigate();
  const hook = useParoquia(id);
  useTitle(hook.paroquia?.nome);

  if (hook.erro) {
    return (
      <main className="pagina-paroquia">
        <HeaderOikos mostrarVoltar mostrarLogo />
        <section className="pagina-paroquia-perfil">
          <p className="pagina-paroquia-subtitulo">{hook.erro}</p>
          <BotaoOikos variante="secundario" onClick={() => navigate('/')}>
            Voltar ao início
          </BotaoOikos>
        </section>
        <FooterOikos />
      </main>
    );
  }

  if (!hook.paroquia) {
    return (
      <main className="pagina-paroquia">
        <HeaderOikos mostrarVoltar mostrarLogo />
        <section className="pagina-grupo-carregando">
          <div className="skeleton" />
          <div className="skeleton" />
        </section>
      </main>
    );
  }

  return (
    <main className="pagina-paroquia">
      <ToastOikos toast={hook.toast} onFechar={hook.fecharToast} />
      <HeaderOikos mostrarVoltar mostrarLogo />

      <section className="pagina-paroquia-perfil">
        <h1 className="pagina-paroquia-titulo">{hook.paroquia.nome}</h1>
        <h2 className="pagina-paroquia-subtitulo">Dashboard da Comunidade</h2>
      </section>

      <DashboardGlobais grupos={hook.gruposVinculados} />

      <RankingGrupos grupos={hook.gruposVinculados} />

      <PainelComunicacao hook={hook} />

      <FooterOikos />
    </main>
  );
}

export default PaginaParoquia;
