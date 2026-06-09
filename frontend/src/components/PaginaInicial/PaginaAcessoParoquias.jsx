import { useNavigate } from 'react-router-dom';
import { useParoquias } from '../../hooks/useParoquias';
import ListaParoquias from './ListaParoquias';
import { ToastOikos } from '../common';
import './PaginaInicial.css';

function PaginaAcessoParoquias() {
  const navigate = useNavigate();
  const hookParoquias = useParoquias();

  return (
    <main className="pagina-inicial">
      <ToastOikos toast={hookParoquias.toast} onFechar={hookParoquias.fecharToast}/>
      <header className="pagina-inicial-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
        <div>
          <h1>Acesso Paroquial</h1>
          <h2>Gerenciamento de paróquias</h2>
        </div>
      </header>

      {hookParoquias.erro ? 
      (
        <section className="pagina-inicial-paroquias">
          <p style={{ color: 'var(--oikos-text-secondary)', textAlign: 'center' }}> {hookParoquias.erro} </p>
        </section>) : (
      <ListaParoquias hookParoquias={hookParoquias} />)}
    </main>
  );
}

export default PaginaAcessoParoquias;
