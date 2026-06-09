import { useNavigate } from 'react-router-dom';
import { useParoquias } from '../../hooks/useParoquias';
import ListaParoquias from './ListaParoquias';
import './PaginaInicial.css';

function PaginaAcessoParoquias() {
  const navigate = useNavigate();
  const hookParoquias = useParoquias();

  return (
    <main className="pagina-inicial">
      <header className="pagina-inicial-header">
        <button className="pagina-grupo-voltar" onClick={() => navigate('/')}>
          &lt;&lt; Voltar
        </button>
        <div>
          <h1>Acesso Paroquial</h1>
          <h2>Gerenciamento de paróquias</h2>
        </div>
      </header>

      <ListaParoquias hookParoquias={hookParoquias} />
    </main>
  );
}

export default PaginaAcessoParoquias;
