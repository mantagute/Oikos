import { useTitle } from '../../hooks/useTitle';
import { useGrupos } from '../../hooks/useGrupos';
import ListaGrupos from './ListaGrupos';
import { ToastOikos, HeaderOikos, FooterOikos } from '../common';
import './PaginaInicial.css';

function PaginaAcessoGrupos() {
  useTitle('Acesso de Membros');
  const hookGrupos = useGrupos();

  return (
    <main className="pagina-inicial">
      <ToastOikos toast={hookGrupos.toast} onFechar={hookGrupos.fecharToast} />
      {hookGrupos.ConfirmDialog}
      <HeaderOikos
        mostrarVoltar
        mostrarLogo
        titulo="Acesso de Membros"
        subtitulo="Gerencie e acesse seus grupos"
      />

      {hookGrupos.erro ? (
        <section className="pagina-inicial-grupos">
          <p style={{ color: 'var(--oikos-text-secondary)', textAlign: 'center' }}>
            {hookGrupos.erro}
          </p>
        </section>
      ) : (
        <ListaGrupos hookGrupos={hookGrupos} />
      )}

      <FooterOikos />
    </main>
  );
}

export default PaginaAcessoGrupos;
