import { useNavigate } from 'react-router-dom';
import { useGrupos } from '../../hooks/useGrupos';
import ListaGrupos  from './ListaGrupos';
import { ToastOikos } from '../common';
import './PaginaInicial.css';

function PaginaAcessoGrupos() {
  const navigate = useNavigate();
  const hookGrupos = useGrupos();

  return (
    <main className="pagina-inicial">
      <ToastOikos toast={hookGrupos.toast} onFechar={hookGrupos.fecharToast}/>
      <header className="pagina-inicial-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
        <div>
          <h1>Acesso de Membros</h1>
          <h2>Gerencie e acesse seus grupos</h2>
        </div>
      </header>

      {hookGrupos.erro ? 
      (
        <section className="pagina-inicial-grupos">
          <p style={{ color: 'var(--oikos-text-secondary)', textAlign: 'center' }}> {hookGrupos.erro}</p>
        </section>
      ) : (
      <ListaGrupos hookGrupos={hookGrupos} />)}
    </main>
  );
}

export default PaginaAcessoGrupos;
