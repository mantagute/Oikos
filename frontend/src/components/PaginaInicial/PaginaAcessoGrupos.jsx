import { useNavigate } from 'react-router-dom';
import { useGrupos } from '../../hooks/useGrupos';
import ListaGrupos  from './ListaGrupos';
import './PaginaInicial.css';

function PaginaAcessoGrupos() {
  const navigate = useNavigate();
  const hookGrupos = useGrupos();

  return (
    <main className="pagina-inicial">
      <header className="pagina-inicial-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
        <div>
          <h1>Acesso de Membros</h1>
          <h2>Gerencie e acesse seus grupos</h2>
        </div>
      </header>

      <ListaGrupos hookGrupos={hookGrupos} />
    </main>
  );
}

export default PaginaAcessoGrupos;
