import { useTitle } from '../../hooks/useTitle';
import { useParoquias } from '../../hooks/useParoquias';
import ListaParoquias from './ListaParoquias';
import { ToastOikos, HeaderOikos, FooterOikos } from '../common';
import './PaginaInicial.css';

function PaginaAcessoParoquias() {
  useTitle('Acesso Paroquial');
  const hookParoquias = useParoquias();

  return (
    <main className="pagina-inicial">
      <ToastOikos toast={hookParoquias.toast} onFechar={hookParoquias.fecharToast} />
      {hookParoquias.ConfirmDialog}
      <HeaderOikos
        mostrarVoltar
        mostrarLogo
        titulo="Acesso Paroquial"
        subtitulo="Gerenciamento de paróquias"
      />

      {hookParoquias.erro ? (
        <section className="pagina-inicial-paroquias">
          <p style={{ color: 'var(--oikos-text-secondary)', textAlign: 'center' }}>
            {hookParoquias.erro}
          </p>
        </section>
      ) : (
        <ListaParoquias hookParoquias={hookParoquias} />
      )}

      <FooterOikos />
    </main>
  );
}

export default PaginaAcessoParoquias;
